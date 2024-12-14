package github.zmilla93.core.chatparser;

import github.zmilla93.core.References;
import github.zmilla93.core.data.IgnoreItemData;
import github.zmilla93.core.data.PlayerMessage;
import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.trading.LangRegex;
import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.trading.WhisperData;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.modules.filetailing.FileTailer;
import github.zmilla93.modules.filetailing.FileTailerListener;
import github.zmilla93.modules.updater.ZLogger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatParser implements FileTailerListener {

    public static final int tailerDelayMS = 250;
    private FileTailer tailer;

    // Listeners - Parser State
    private final ArrayList<IParserInitListener> onInitListeners = new ArrayList<>();
    private final ArrayList<IParserLoadedListener> onLoadListeners = new ArrayList<>();
    // Listeners - Game Events
    private final ArrayList<ITradeListener> tradeListeners = new ArrayList<>();
    private final ArrayList<IChatScannerListener> chatScannerListeners = new ArrayList<>();
    private final ArrayList<IJoinedAreaListener> joinedAreaListeners = new ArrayList<>();
    private final ArrayList<IDndListener> dndListeners = new ArrayList<>();

    // Settings
    private final Game game;
    private Path path;
    private final boolean isPoe1;

    // State
    private boolean open;
    private int lineCount;
    private int whisperCount;
    private int tradeCount;
    private String currentZone = "The Twilight Strand";
    private boolean dnd = false;
    private long startTime;

    // Regex
    public static final String CLIENT_MESSAGE_REGEX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*] (?<message>.+)";
    public static final String CLIENT_WHISPER_REGEX = "@(?<messageType>От кого|\\S+) (?<guildName><.+>)? ?(?<playerName>[^:]+):(\\s+)(?<message>.+)";
    // FIXME : POE2 is currently missing the to/from part of whispers, so it uses a different pattern.
    public static final String CLIENT_WHISPER_REGEX_POE2 = "(?<messageType>@)(?<playerName>[^:]+):(\\s+)(?<message>.+)";
    private static final Pattern clientMessage = Pattern.compile(CLIENT_MESSAGE_REGEX);
    private static final Pattern clientWhisper = Pattern.compile(CLIENT_WHISPER_REGEX);
    private static final Pattern clientWhisperPoe2 = Pattern.compile(CLIENT_WHISPER_REGEX_POE2);

    public ChatParser(Game game) {
        this.game = game;
        this.isPoe1 = game == Game.PATH_OF_EXILE_1;
    }

    public void open(Path path) {
        open(path, false);
    }

    public void open(Path path, boolean isPathRelative) {
        this.path = path;
        lineCount = 0;
        whisperCount = 0;
        tradeCount = 0;
        if (open) close();
        if (path == null) {
            ZLogger.err("Chat parser was given a null path!");
            return;
        }
        if (!path.toFile().exists()) {
            ZLogger.err("Chat parser was given a file that doesn't exist: " + path);
        }
        tailer = FileTailer.createTailer(path, isPathRelative, this, tailerDelayMS, false);
        startTime = System.currentTimeMillis();
        open = true;
    }

    public void close() {
        tailer.stop();
        tailer = null;
        path = null;
        open = false;
    }

    public Path getPath() {
        return path;
    }

    public void parseLine(String line) {
        if (!open) return;
//        System.out.println("PARSING " + game + " LINE.");
        Matcher fullClientMessage = clientMessage.matcher(line);
        if (!fullClientMessage.matches()) return;
        String fullMessage = fullClientMessage.group("message");
        String date = fullClientMessage.group("date");
        String time = fullClientMessage.group("time");
        if (fullMessage == null || fullMessage.isEmpty()) return;
        char firstChar = fullMessage.charAt(0);
        lineCount++;
        // Meta stuff
        // FIXME : Switch to passing fullMessage to everything
        if (firstChar == ':') {
            if (handleZoneChange(line)) return;
            if (handleDndToggle(line)) return;
            if (handlePlayerJoinedArea(line)) return;
        }
        // Whispers
        if (firstChar == '@') {
            whisperCount++;
            Pattern whisperPattern = game == Game.PATH_OF_EXILE_1 ? clientWhisper : clientWhisperPoe2;
            Matcher whisperMatcher = whisperPattern.matcher(fullMessage);
            if (whisperMatcher.matches()) {
                String message = whisperMatcher.group("message");
                String guildName = isPoe1 ? whisperMatcher.group("guildName") : null;
                String playerName = whisperMatcher.group("playerName");
                String messageType = whisperMatcher.group("messageType");
                WhisperData metaData = new WhisperData();
                metaData.date = date;
                metaData.time = time;
                metaData.message = message;
                metaData.guildName = guildName;
                metaData.playerName = playerName;
                metaData.offerType = getOfferType(messageType);
                if (message != null && handleTradeOffer(metaData, message)) {
                    tradeCount++;
                    return;
                }
            }
        }
        // Scanner
        if (handleChatScanner(line)) return;
    }

    private TradeOfferType getOfferType(String messageType) {
        if (isPoe1) return LangRegex.getMessageType(messageType);
        else {
            // FIXME : Add hotkey check for outgoing trades
            return TradeOfferType.INCOMING_TRADE;
        }
    }

    private boolean handleChatScanner(String line) {
        if (!SaveManager.chatScannerSaveFile.data.searching) return false;
        Matcher chatMatcher = References.chatPatten.matcher(line);
        Matcher metaMatcher = References.clientMetaPattern.matcher(line);
        String message;
        String messageType;
        String player;
        if (chatMatcher.matches()) {
            message = chatMatcher.group("message");
            messageType = chatMatcher.group("messageType");
            player = chatMatcher.group("playerName");
        } else if (metaMatcher.matches()) {
            message = metaMatcher.group("message");
            messageType = "meta";
            player = "Meta Text";
        } else {
            return false;
        }
        if (messageType == null) return false;
        message = message.toLowerCase();
        messageType = messageType.toLowerCase();
        // Iterate though all active searches and look for matching phrases
        for (ChatScannerEntry entry : SaveManager.chatScannerSaveFile.data.activeSearches) {
            if (entry.getSearchTerms() == null) continue;
            for (String term : entry.getSearchTerms()) {
                if (message.contains(term)) {
                    boolean allow = false;
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
                    // Verify the message is allowed
                    if (entry.allowGlobalAndTradeChat && (messageType.equals("#") || messageType.equals("$")))
                        allow = true;
                    if (entry.allowWhispers) {
                        for (LangRegex lang : LangRegex.values()) {
                            if (lang.messageFrom == null) continue;
                            if (messageType.contains(lang.messageFrom)) {
                                allow = true;
                                break;
                            }
                        }
                    }
                    boolean isMetaText = false;
                    if (entry.allowMetaText && messageType.equals("meta")) {
                        allow = true;
                        player = entry.title;
                        isMetaText = true;
                    }
                    if (!allow) continue;
                    PlayerMessage playerMessage = new PlayerMessage(player, message, isMetaText);
                    for (IChatScannerListener listener : chatScannerListeners)
                        listener.onScannerMessage(entry, playerMessage, tailer.isLoaded());
                    return true;
                }
            }
        }
        return false;
    }

    private boolean handleTradeOffer(WhisperData data, String line) {
        // Check for a trade
        TradeOffer offer = TradeOffer.getTradeFromMessage(data, line, game);
        if (offer == null) return false;
        // Check if the trade should be ignored
        String itemNameLower = offer.itemName.toLowerCase();
        if (offer.offerType == TradeOfferType.INCOMING_TRADE && SaveManager.ignoreSaveFile.data != null) {
            IgnoreItemData item = SaveManager.ignoreSaveFile.data.exactMatchIgnoreMap.get(itemNameLower);
            if (item != null && !item.isExpired()) {
                handleIgnoreItem();
                return true;
            }
            for (IgnoreItemData ignoreItemData : SaveManager.ignoreSaveFile.data.containsTextIgnoreList) {
                if (itemNameLower.contains(ignoreItemData.itemNameLower()) && !ignoreItemData.isExpired()) {
                    handleIgnoreItem();
                    return true;
                }
            }
        }
        // Handle trade
        for (ITradeListener listener : tradeListeners)
            listener.handleTrade(offer, tailer.isLoaded());
        return true;
    }

    private boolean handleDndToggle(String line) {
        Matcher metaMatcher = References.clientMetaPattern.matcher(line);
        if (!metaMatcher.matches()) return false;
        String message = metaMatcher.group("message");
        if (message == null) return false;
        for (LangRegex lang : LangRegex.values()) {
            if (lang.dndOn == null || lang.dndOff == null) continue;
            if (message.contains(lang.dndOn)) {
                dnd = true;
                for (IDndListener listener : dndListeners) listener.onDndToggle(dnd, tailer.isLoaded());
                return true;
            }
            if (message.contains(lang.dndOff)) {
                dnd = false;
                for (IDndListener listener : dndListeners) listener.onDndToggle(dnd, tailer.isLoaded());
                return true;
            }
        }
        return false;
    }

    private void handleIgnoreItem() {
        if (tailer.isLoaded()) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.itemIgnoredSound);
    }


    private boolean handlePlayerJoinedArea(String line) {
        for (LangRegex lang : LangRegex.values()) {
            if (lang.joinedArea == null) continue;
            Matcher matcher = lang.joinedAreaPattern.matcher(line);
            if (matcher.matches()) {
                String playerName = matcher.group("playerName");
                for (IJoinedAreaListener listener : joinedAreaListeners) {
                    listener.onJoinedArea(playerName);
                }
                return true;
            }
        }
        return false;
    }

    private boolean handleZoneChange(String line) {
        for (LangRegex lang : LangRegex.values()) {
            if (lang.enteredArea == null) continue;
            Matcher matcher = lang.enteredAreaPattern.matcher(line);
            if (matcher.matches()) {
                currentZone = matcher.group("zone");
                return true;
            }
        }
        return false;
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

    public void addTradeListener(ITradeListener listener) {
        tradeListeners.add(listener);
    }

    public void addChatScannerListener(IChatScannerListener listener) {
        chatScannerListeners.add(listener);
    }

    public void addJoinedAreaListener(IJoinedAreaListener listener) {
        joinedAreaListeners.add(listener);
    }

    public void addDndListener(IDndListener listener) {
        dndListeners.add(listener);
    }

    public void removeAllListeners() {
        onInitListeners.clear();
        onLoadListeners.clear();
        tradeListeners.clear();
        chatScannerListeners.clear();
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
        float endTime = (System.currentTimeMillis() - startTime) / 1000f;
        ZLogger.log("Chat parser loaded for " + game + " in " + endTime + " seconds. Found " + lineCount + " lines, " + whisperCount + " whispers, and " + tradeCount + " trades.");
        for (IParserLoadedListener listener : onLoadListeners) listener.onParserLoaded(dnd);
    }

    @Override
    public void handle(String line) {
        parseLine(line);
    }

}
