package github.zmilla93.core.chatparser;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.GameSettings;
import github.zmilla93.gui.managers.FrameManager;

/**
 * Manages opening and restarting two {@link ChatParser}s, one for each game.
 */
public class ChatParserManager {

    public static ChatParser chatParserPoe1;
    public static ChatParser chatParserPoe2;

    /// Initialize
    public static void initChatParsers() {
        boolean shouldOpenPoe1Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe1);
        boolean shouldOpenPoe2Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe2);
        boolean poe1ParserStateChange = didParserStateChange(chatParserPoe1, shouldOpenPoe1Parser, SaveManager.settingsSaveFile.data.settingsPoe1.installFolder);
        boolean poe2ParserStateChange = didParserStateChange(chatParserPoe2, shouldOpenPoe2Parser, SaveManager.settingsSaveFile.data.settingsPoe2.installFolder);
        if (poe1ParserStateChange) {
            if (chatParserPoe1 != null) chatParserPoe1.close();
            if (shouldOpenPoe1Parser) initChatParser(SaveManager.settingsSaveFile.data.settingsPoe1);
        }
        if (poe2ParserStateChange) {
            if (chatParserPoe2 != null) chatParserPoe2.close();
            if (shouldOpenPoe2Parser) initChatParser(SaveManager.settingsSaveFile.data.settingsPoe2);
        }
    }

    private static boolean didParserStateChange(ChatParser parser, boolean shouldOpen, String newPath) {
        boolean isRunning = parser != null && parser.isOpen();
        boolean runningChanged = isRunning != shouldOpen;
        boolean pathChanged = false;
        if (isRunning && shouldOpen) pathChanged = !parser.getPath().startsWith(newPath);
        return runningChanged || pathChanged;
    }

    private static boolean shouldParserOpen(GameSettings settings) {
        if (settings.notInstalled) return false;
        return settings.isClientPathValid();
    }

    public static void initChatParser(GameSettings settings) {
        if (settings.notInstalled) return;
        if (!settings.isClientPathValid()) return;
        ChatParser parser;
        if (settings.isPoe1()) {
            chatParserPoe1 = new ChatParser(settings.game());
            parser = chatParserPoe1;
        } else {
            chatParserPoe2 = new ChatParser(settings.game());
            parser = chatParserPoe2;
        }
        addParserListeners(settings, parser);
        parser.open(settings.getClientPath());
    }

    /// NOTE : Since chat parsers can be closed and reopened, listeners are
    ///        added here instead of in the UI elements like usual.
// FIXME : Moving these to the UI elements requires making the parser reopen-able.
    public static void addParserListeners(GameSettings settings, ChatParser parser) {
        // History
        if (settings.isPoe1()) {
            parser.addOnInitCallback(FrameManager.historyWindow.incomingTradesPoe1);
            parser.addOnLoadedCallback(FrameManager.historyWindow.incomingTradesPoe1);
            parser.addTradeListener(FrameManager.historyWindow.incomingTradesPoe1);
            parser.addOnInitCallback(FrameManager.historyWindow.outgoingTradesPoe1);
            parser.addOnLoadedCallback(FrameManager.historyWindow.outgoingTradesPoe1);
            parser.addTradeListener(FrameManager.historyWindow.outgoingTradesPoe1);
        } else {
            parser.addOnInitCallback(FrameManager.historyWindow.incomingTradesPoe2);
            parser.addOnLoadedCallback(FrameManager.historyWindow.incomingTradesPoe2);
            parser.addTradeListener(FrameManager.historyWindow.incomingTradesPoe2);
            parser.addOnInitCallback(FrameManager.historyWindow.outgoingTradesPoe2);
            parser.addOnLoadedCallback(FrameManager.historyWindow.outgoingTradesPoe2);
            parser.addTradeListener(FrameManager.historyWindow.outgoingTradesPoe2);
        }
//        parser.addOnInitCallback(FrameManager.historyWindow);
//        parser.addOnLoadedCallback(FrameManager.historyWindow);
//        parser.addTradeListener(FrameManager.historyWindow);
        // Message Manager
        parser.addTradeListener(FrameManager.messageManager);
        parser.addChatScannerListener(FrameManager.messageManager);
        parser.addJoinedAreaListener(FrameManager.messageManager);
        // Menu Bar
        parser.addOnLoadedCallback(FrameManager.menuBarIcon);
        parser.addOnLoadedCallback(FrameManager.menuBarDialog);
        parser.addDndListener(FrameManager.menuBarIcon);
        parser.addDndListener(FrameManager.menuBarDialog);
    }

}
