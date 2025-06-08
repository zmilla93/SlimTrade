package github.zmilla93.core.chatparser

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.GameSettings
import github.zmilla93.gui.managers.FrameManager

/**
 * Manages opening and restarting two [ChatParser]s, one for each game.
 */
object ChatParserManager {
    var chatParserPoe1: ChatParser? = null
    var chatParserPoe2: ChatParser? = null

    // FIXME : Zone should be handled via listeners, quick fix for now
    @JvmField
    var currentZone: String = "The Twilight Strand"

    /** Initialize */
    @JvmStatic
    fun initChatParsers() {
        val shouldOpenPoe1Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe1)
        val shouldOpenPoe2Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe2)
        val poe1ParserStateChange = ChatParserManager.didParserStateChange(
            chatParserPoe1,
            shouldOpenPoe1Parser,
            SaveManager.settingsSaveFile.data.settingsPoe1.installFolder!!
        )
        val poe2ParserStateChange = ChatParserManager.didParserStateChange(
            chatParserPoe2,
            shouldOpenPoe2Parser,
            SaveManager.settingsSaveFile.data.settingsPoe2.installFolder!!
        )
        if (poe1ParserStateChange) {
            if (chatParserPoe1 != null) chatParserPoe1!!.close()
            if (shouldOpenPoe1Parser) initChatParser(SaveManager.settingsSaveFile.data.settingsPoe1)
        }
        if (poe2ParserStateChange) {
            if (chatParserPoe2 != null) chatParserPoe2!!.close()
            if (shouldOpenPoe2Parser) initChatParser(SaveManager.settingsSaveFile.data.settingsPoe2)
        }
    }

    private fun didParserStateChange(parser: ChatParser?, shouldOpen: Boolean, newPath: String): Boolean {
        val isRunning = parser != null && parser.isOpen
        val runningChanged = isRunning != shouldOpen
        var pathChanged = false
        if (isRunning && shouldOpen) pathChanged = !parser.path!!.startsWith(newPath)
        return runningChanged || pathChanged
    }

    private fun shouldParserOpen(settings: GameSettings): Boolean {
        if (settings.notInstalled) return false
        return settings.doesClientLogExist()
    }

    fun initChatParser(settings: GameSettings) {
        if (settings.notInstalled) return
        if (!settings.doesClientLogExist()) return
        val parser: ChatParser?
        if (settings.isPoe1) {
            chatParserPoe1 = ChatParser(settings.game)
            parser = chatParserPoe1
        } else {
            chatParserPoe2 = ChatParser(settings.game)
            parser = chatParserPoe2
        }
        ChatParserManager.addParserListeners(settings, parser!!)
        parser.open(settings.clientPath)
    }

    /** NOTE : Since chat parsers can be closed and reopened, listeners are
     * added here instead of in the UI elements like usual. */
    // FIXME : Moving these to the UI elements requires making the parser reopen-able.
    fun addParserListeners(settings: GameSettings, parser: ChatParser) {
        // History
        if (settings.isPoe1) {
            parser.addOnInitCallback(FrameManager.historyWindow.incomingTradesPoe1)
            parser.addOnLoadedCallback(FrameManager.historyWindow.incomingTradesPoe1)
            parser.addTradeListener(FrameManager.historyWindow.incomingTradesPoe1)
            parser.addOnInitCallback(FrameManager.historyWindow.outgoingTradesPoe1)
            parser.addOnLoadedCallback(FrameManager.historyWindow.outgoingTradesPoe1)
            parser.addTradeListener(FrameManager.historyWindow.outgoingTradesPoe1)
        } else {
            parser.addOnInitCallback(FrameManager.historyWindow.incomingTradesPoe2)
            parser.addOnLoadedCallback(FrameManager.historyWindow.incomingTradesPoe2)
            parser.addTradeListener(FrameManager.historyWindow.incomingTradesPoe2)
            parser.addOnInitCallback(FrameManager.historyWindow.outgoingTradesPoe2)
            parser.addOnLoadedCallback(FrameManager.historyWindow.outgoingTradesPoe2)
            parser.addTradeListener(FrameManager.historyWindow.outgoingTradesPoe2)
        }
        //        parser.addOnInitCallback(FrameManager.historyWindow);
//        parser.addOnLoadedCallback(FrameManager.historyWindow);
//        parser.addTradeListener(FrameManager.historyWindow);
        // Message Manager
        parser.addTradeListener(FrameManager.messageManager)
        parser.addChatScannerListener(FrameManager.messageManager)
        parser.addJoinedAreaListener(FrameManager.messageManager)
        // Menu Bar
        parser.addOnLoadedCallback(FrameManager.menuBarIcon)
        parser.addOnLoadedCallback(FrameManager.menuBarDialog)
        parser.addDndListener(FrameManager.menuBarIcon)
        parser.addDndListener(FrameManager.menuBarDialog)
    }
}
