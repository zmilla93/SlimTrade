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
        boolean openPoe1Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe1, chatParserPoe1);
        boolean openPoe2Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe2, chatParserPoe2);
        if (openPoe1Parser) {
            if (chatParserPoe1 != null) chatParserPoe1.close();
            initChatParser(SaveManager.settingsSaveFile.data.settingsPoe1);
        }
        if (openPoe2Parser) {
            if (chatParserPoe2 != null) chatParserPoe2.close();
            initChatParser(SaveManager.settingsSaveFile.data.settingsPoe2);
        }
    }

    private static boolean shouldParserOpen(GameSettings settings, ChatParser parser) {
        if (!settings.isClientPathValid()) return false;
        if (parser == null) return true;
        if (!parser.isOpen()) return true;
        return !parser.getPath().startsWith(settings.installFolder);
    }

    private static void closeParser(ChatParser parser) {
        if (parser != null) parser.close();
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
        addParserListeners(parser);
        parser.open(settings.getClientPath());
    }

    /// NOTE : Since chat parsers can be closed and reopened, listeners are
    ///        added here instead of in the UI elements like usual.
    public static void addParserListeners(ChatParser parser) {
        // History
        parser.addOnInitCallback(FrameManager.historyWindow);
        parser.addOnLoadedCallback(FrameManager.historyWindow);
        parser.addTradeListener(FrameManager.historyWindow);
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
