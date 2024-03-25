package com.slimtrade.core.chatparser;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.data.PlayerMessage;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.modules.filetailing.FileTailer;
import com.slimtrade.modules.filetailing.FileTailerListener;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ChatParser implements FileTailerListener {

    private FileTailer tailer;
    public static final int tailerDelayMS = 250;

    // Listeners
    private final ArrayList<IParserInitListener> onInitListeners = new ArrayList<>();
    private final ArrayList<IParserLoadedListener> onLoadListeners = new ArrayList<>();
    private final ArrayList<IPreloadTradeListener> preloadTradeListeners = new ArrayList<>();
    private final ArrayList<ITradeListener> tradeListeners = new ArrayList<>();
    private final ArrayList<IJoinedAreaListener> joinedAreaListeners = new ArrayList<>();

    // State
    private String currentZone = "The Twilight Strand";
    private boolean changeCharacter;
    private String changeCharacterString;
    private int changeCharacterChecks;
    private boolean loaded; // Set to true after file has been read to EOF once
    private boolean open;
    private String path;
    private boolean end;

    public void open(String path, boolean end) {
        this.end = end;
        if (open) close();
        if (path == null) return;
        File clientFile = new File(path);
        if (clientFile.exists() && clientFile.isFile()) {
            this.path = path;
            tailer = FileTailer.createTailer(clientFile, this, tailerDelayMS, end);
            open = true;
        }
    }

    public void close() {
        tailer.stop();
        loaded = false;
        path = null;
        open = false;
    }

    public String getPath() {
        return path;
    }

    public void parseLine(String line) {
        if (!open) return;
        if (line.contains("@")) {
            TradeOffer tradeOffer = TradeOffer.getTradeFromMessage(line);
            if (tradeOffer != null) {
                handleTradeOffer(tradeOffer);
                return;
            }
        }
        if (loaded) {
            // Chat Scanner
            handleChatScanner(line);
            handleChangeCharacter(line);
            // Player Joined Area
            for (LangRegex lang : LangRegex.values()) {
                if (lang.joinedArea == null) continue;
                Matcher matcher = lang.joinedAreaPattern.matcher(line);
                if (matcher.matches()) {
                    String playerName = matcher.group("playerName");
                    for (IJoinedAreaListener listener : joinedAreaListeners) {
                        listener.onJoinedArea(playerName);
                    }
                    return;
                }
            }
        }
        // Zone Tracking
        for (LangRegex lang : LangRegex.values()) {
            Matcher matcher = lang.enteredAreaPattern.matcher(line);
            if (lang.enteredArea == null) continue;
            if (matcher.matches()) {
                currentZone = matcher.group("zone");
                return;
            }
        }
    }

    private void handleChatScanner(String line) {
        Matcher chatMatcher = References.chatPatten.matcher(line);
        if (!chatMatcher.matches()) return;
        if (App.chatInConsole)
            System.out.println(chatMatcher.group("playerName") + ": " + chatMatcher.group("message"));
        String message = chatMatcher.group("message");
        if (SaveManager.chatScannerSaveFile.data.searching) {
            // Iterate though all active searches and look for matching phrases
            for (ChatScannerEntry entry : SaveManager.chatScannerSaveFile.data.activeSearches) {
                if (entry.getSearchTerms() == null) continue;
                for (String term : entry.getSearchTerms()) {
                    if (message.contains(term)) {
                        boolean ignore = false;
                        // Check if this message should be ignored
                        if (entry.getIgnoreTerms() != null) {
                            for (String ignoreTerm : entry.getIgnoreTerms()) {
                                if (message.contains(ignoreTerm)) {
                                    ignore = true;
                                    break;
                                }
                            }
                        }
                        if (ignore) continue;
                        String player = chatMatcher.group("playerName");
                        SwingUtilities.invokeLater(() -> FrameManager.messageManager.addScannerMessage(entry, new PlayerMessage(player, message)));
                        return;
                    }
                }
            }
        }

    }

    private void handleChangeCharacter(String line) {
        if (!changeCharacter) return;
        Matcher chatMatcher = References.whisperPattern.matcher(line);
        if (!chatMatcher.matches()) return;
        String playerName = chatMatcher.group("playerName");
        String message = chatMatcher.group("message");
        if (playerName.equals(SaveManager.settingsSaveFile.data.characterName)) {
            changeCharacterString = message;
        } else if (message.equals(changeCharacterString)) {
            TradeUtil.changeCharacterName(playerName);
            changeCharacter = false;
        } else {
            changeCharacterChecks++;
        }
        if (changeCharacterChecks > 10) changeCharacter = false;
    }

    private void handleTradeOffer(TradeOffer offer) {
        // Check if trade should be ignored
        String itemNameLower = offer.itemName.toLowerCase();
        if (offer.offerType == TradeOfferType.INCOMING_TRADE) {
            IgnoreItemData item = SaveManager.ignoreSaveFile.data.exactMatchIgnoreMap.get(itemNameLower);
            if (item != null && !item.isExpired()) {
                handleIgnoreItem();
                return;
            }
            for (IgnoreItemData ignoreItemData : SaveManager.ignoreSaveFile.data.containsTextIgnoreList) {
                if (itemNameLower.contains(ignoreItemData.itemNameLower()) && !ignoreItemData.isExpired()) {
                    handleIgnoreItem();
                    return;
                }
            }
        }
        // Handle trade
        if (tailer.isLoaded()) {
            for (ITradeListener listener : tradeListeners) {
                listener.handleTrade(offer);
            }
        } else {
            for (IPreloadTradeListener listener : preloadTradeListeners) {
                listener.handlePreloadTrade(offer);
            }
        }
    }

    private void handleIgnoreItem() {
        // This is a little hacky. Will only play sound if chat parser starts at end. Might want to switch to a listener
        if (end)
            AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.itemIgnoredSound.sound, SaveManager.settingsSaveFile.data.itemIgnoredSound.volume);
    }

    private TradeOfferType getMessageType(String s) {
        // TODO : Move to LangRegex
        switch (s.toLowerCase()) {
            case "from":
            case "來自":     // Chinese
            case "de":      // French, Portuguese & Spanish
            case "von":     // German
            case "от кого": // Russian
            case "จาก":     // Thai
                return TradeOfferType.INCOMING_TRADE;
            case "to":
            case "向":      // Chinese
            case "à":       // French
            case "an":      // German
            case "para":    // Portuguese & Spanish
            case "кому":    // Russian
            case "ถึง":      // Thai
                return TradeOfferType.OUTGOING_TRADE;
            default:
                return TradeOfferType.UNKNOWN;
        }
    }

    public void startChangeCharacterName() {
        changeCharacter = true;
        changeCharacterChecks = 0;
    }

    private String cleanResult(Matcher matcher, String text) {
        try {
            return matcher.group(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private int cleanInt(String text) {
        if (text == null) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    private double cleanDouble(String text) {
        if (text == null) {
            return 0;
        }
        text = text.replaceAll(",", ".");
        return Double.parseDouble(text);
    }

    public String getCurrentZone() {
        return currentZone;
    }

    // Listeners
    public void addOnInitCallback(IParserInitListener listener) {
        onInitListeners.add(listener);
    }

    public void addOnLoadedCallback(IParserLoadedListener listener) {
        onLoadListeners.add(listener);
    }

    public void addPreloadTradeListener(IPreloadTradeListener listener) {
        preloadTradeListeners.add(listener);
    }

    public void addTradeListener(ITradeListener listener) {
        tradeListeners.add(listener);
    }

    public void addJoinedAreaListener(IJoinedAreaListener listener) {
        joinedAreaListeners.add(listener);
    }

    public void removeAllListeners() {
        onInitListeners.clear();
        onLoadListeners.clear();
        preloadTradeListeners.clear();
        tradeListeners.clear();
        joinedAreaListeners.clear();
    }

    // File Tailing

    @Override
    public void init(FileTailer tailer) {
        for (IParserInitListener listener : onInitListeners) listener.onParserInit();
    }

    @Override
    public void fileNotFound() {

    }

    @Override
    public void fileRotated() {

    }

    @Override
    public void onLoad() {
        for (IParserLoadedListener listener : onLoadListeners) listener.onParserLoaded();
        loaded = true;
    }

    @Override
    public void handle(String line) {
        parseLine(line);
    }

}
