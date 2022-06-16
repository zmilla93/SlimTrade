package com.slimtrade.core.chatparser;

import com.slimtrade.core.data.IgnoreItem;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.modules.filetailing.FileTailer;
import com.slimtrade.modules.filetailing.FileTailerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class ChatParser implements FileTailerListener {

    private FileTailer tailer;
    public static final int tailerDelayMS = 250;

    // Listeners
    private ArrayList<IParserLoadedListener> onLoadListeners = new ArrayList<>();
    private ArrayList<ITradeListener> preloadTradeListeners = new ArrayList<>();
    private ArrayList<ITradeListener> tradeListeners = new ArrayList<>();
    private ArrayList<IJoinedAreaListener> joinedAreaListeners = new ArrayList<>();

    // State
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
        loaded = false;
        path = null;
        open = false;
        tailer.stop();
    }

    public String getPath() {
        return path;
    }

    //    @Override
    public void parseLine(String line) {
        if (!open) return;
        if (line.contains("@")) {
            TradeOffer tradeOffer = TradeOffer.getTradeFromMessage(line);
            if (tradeOffer != null) {
                handleTradeOffer(tradeOffer);
            }
        } else if (loaded) {
            // Chat Scanner
            // TODO : this
            // Player Joined Area
            for (LangRegex lang : LangRegex.values()) {
                Matcher matcher = lang.joinedAreaPattern.matcher(line);
                if (matcher.matches()) {
                    String playerName = matcher.group("playerName");
                    for (IJoinedAreaListener listener : joinedAreaListeners) {
                        listener.onJoinedArea(playerName);
                    }
                    break;
                }
            }
        }
    }

    private void handleTradeOffer(TradeOffer offer) {
        // Check if trade should be ignored
        String itemNameLower = offer.itemName.toLowerCase();
        if (offer.offerType == TradeOffer.TradeOfferType.INCOMING) {
            IgnoreItem item = SaveManager.ignoreSaveFile.data.exactIgnoreMap.get(itemNameLower);
            if (item != null && !item.isExpired()) {
                handleIgnoreItem();
                return;
            }
            for (IgnoreItem ignoreItem : SaveManager.ignoreSaveFile.data.containsIgnoreList) {
                if (itemNameLower.contains(ignoreItem.itemNameLower) && !ignoreItem.isExpired()) {
                    handleIgnoreItem();
                    return;
                }
            }
        }
        // Handle trade
        for (ITradeListener listener : tradeListeners) {
            listener.handleTrade(offer);
        }
    }

    private void handleIgnoreItem() {
        // This is a little hacky. Will only play sound if chat parser starts at end. Might want to switch to a listener
        if (end)
            AudioManager.playSoundPercent(SaveManager.settingsSaveFile.data.itemIgnoredSound.sound, SaveManager.settingsSaveFile.data.itemIgnoredSound.volume);
    }

    private TradeOffer.TradeOfferType getMessageType(String s) {
        // TODO : Move to LangRegex
        switch (s.toLowerCase()) {
            case "from":
            case "來自":     // Chinese
            case "de":      // French, Portuguese & Spanish
            case "von":     // German
            case "от кого": // Russian
            case "จาก":     // Thai
                return TradeOffer.TradeOfferType.INCOMING;
            case "to":
            case "向":      // Chinese
            case "à":       // French
            case "an":      // German
            case "para":    // Portuguese & Spanish
            case "кому":    // Russian
            case "ถึง":      // Thai
                return TradeOffer.TradeOfferType.OUTGOING;
            default:
                return TradeOffer.TradeOfferType.UNKNOWN;
        }
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

    // Listeners
    public void addOnLoadedCallback(IParserLoadedListener listener) {
        onLoadListeners.add(listener);
    }

    public void addPreloadTradeListener(ITradeListener listener) {
        preloadTradeListeners.add(listener);
    }

    public void addTradeListener(ITradeListener listener) {
        tradeListeners.add(listener);
    }

    public void addJoinedAreaListener(IJoinedAreaListener listener) {
        joinedAreaListeners.add(listener);
    }

    // File Tailing

    @Override
    public void init(FileTailer tailer) {

    }

    @Override
    public void fileNotFound() {

    }

    @Override
    public void fileRotated() {

    }

    @Override
    public void onLoad() {
        for (IParserLoadedListener listener : onLoadListeners) {
            listener.onParserLoaded();
        }
        loaded = true;
    }

    @Override
    public void handle(String line) {
        parseLine(line);
    }
}
