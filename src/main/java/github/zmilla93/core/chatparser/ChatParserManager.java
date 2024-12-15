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
        closeChatParsers();
        initChatParser(SaveManager.settingsSaveFile.data.settingsPoe1);
        initChatParser(SaveManager.settingsSaveFile.data.settingsPoe2);
    }

//    private static boolean shouldParserOpen(Game game, ChatParser parser) {
//        if (!game.isClientPathValid()) return false;
//        if (!parser.isOpen()) return true;
//        return parser.getPath().startsWith(SaveManager.settingsSaveFile.data.installFolderPoe1);
//    }

    private static void closeChatParsers() {
        if (chatParserPoe1 != null) {
            chatParserPoe1.close();

        }
        if (chatParserPoe2 != null) {
            chatParserPoe2.close();
        }
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


    public static void addParserListeners(ChatParser parser) {
        /// NOTE : Since chat parsers can be closed and reopened, listeners are
        ///        added here instead of in the UI elements like usual.
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
