package github.zmilla93.core.chatparser;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.modules.updater.ZLogger;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Manages opening and restarting two {@link ChatParser}s, one for each game.
 */
public class ChatParserManager {

    public static ChatParser chatParserPoe1;
    public static ChatParser chatParserPoe2;

    /// Initialize
    public static void initChatParsers() {
        System.out.println("NEWINITPARSER!!!");
        if (chatParserPoe1 != null) {
            boolean open = chatParserPoe1.isOpen();
            System.out.println("open:" + open);
            if (open) {
                Path path = Paths.get(SaveManager.settingsSaveFile.data.installFolderPoe1);
                if (chatParserPoe1.getPath().startsWith(path))
                    System.out.println("match!!!");
            }
        }

        closeChatParsers();
        initChatParser(Game.PATH_OF_EXILE_1, SaveManager.settingsSaveFile.data.installFolderPoe1, SaveManager.settingsSaveFile.data.notInstalledPoe1);
        initChatParser(Game.PATH_OF_EXILE_2, SaveManager.settingsSaveFile.data.installFolderPoe2, SaveManager.settingsSaveFile.data.notInstalledPoe2);
    }

    private static boolean shouldParserOpen(Game game, ChatParser parser) {
        if (!game.isClientPathValid()) return false;
        if (!parser.isOpen()) return true;
        return parser.getPath().startsWith(SaveManager.settingsSaveFile.data.installFolderPoe1);
    }

    private static void closeChatParsers() {
        if (chatParserPoe1 != null) {
            chatParserPoe1.close();

        }
        if (chatParserPoe2 != null) {
            chatParserPoe2.close();
        }
    }

    public static void initChatParser(Game game, String installFolder, boolean notInstalled) {
        if (notInstalled) return;
        if (installFolder == null) return;
        Path poeFolder = Paths.get(installFolder);
        if (!poeFolder.toFile().exists()) return;
        setupListeners(game, poeFolder.resolve(Paths.get(SaveManager.POE_LOG_FOLDER_NAME, SaveManager.POE_CLIENT_TXT_NAME)));

        // Open
        // FIXME:
//        parser.open(clientPath);
    }


    public static void setupListeners(Game game, Path clientPath) {
        ChatParser parser = game.isPoe1() ? chatParserPoe1 : chatParserPoe2;
//        if (parser != null) {
//            parser.close();
//            parser.removeAllListeners();
//        }
        if (!clientPath.toFile().exists()) {
            ZLogger.err("Client.txt file not found: " + clientPath);
            return;
        }
        System.out.println("Setup listeners!");
        if (game.isPoe1()) {
            chatParserPoe1 = new ChatParser(game);
            parser = chatParserPoe1;
        } else {
            chatParserPoe2 = new ChatParser(game);
            parser = chatParserPoe2;
        }
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
        // FIXME: Move/remove
        parser.open(clientPath);
    }

}
