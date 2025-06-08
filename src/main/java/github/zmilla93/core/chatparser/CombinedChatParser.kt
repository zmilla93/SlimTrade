package github.zmilla93.core.chatparser

import github.zmilla93.App
import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.core.event.ParserEventType
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.GameSettings
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.gui.chatscanner.ChatScannerEntry
import github.zmilla93.gui.managers.FrameManager

/**
 * Runs a chat parser for each game.
 */
// FIXME @important : Make sure chat parsers are fully closed and reopened
class CombinedChatParser : TradeListener, ChatScannerListener, ParserRestartListener, ParserLoadedListener {

    var chatParserPoe1: ChatParser? = null
    var chatParserPoe2: ChatParser? = null

    // FIXME : Zone should be handled via listeners, quick fix for now
    var currentZone: String = "The Twilight Strand"

    // Listeners - POE Game Events
    val tradeListeners = ArrayList<TradeListener>()
    val chatScannerListeners = ArrayList<ChatScannerListener>()
    val playerJoinedAreaListeners = ArrayList<PlayerJoinedAreaListener>()
    val dndListeners = ArrayList<DndListener>()
    var parsersRestarted = 0
        @Synchronized set
    var parserLoadedCount = 0
        @Synchronized set
    var expectedParserCount = 0

    /**
     *  Restarts chat parsers for both games only when required.
     *  IMPORTANT: Parsers are multithreaded, so over of execution is vital here.
     */
    fun restartChatParsers(forceRestart: Boolean = false) {
        val shouldOpenPoe1Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe1)
        val shouldOpenPoe2Parser = shouldParserOpen(SaveManager.settingsSaveFile.data.settingsPoe2)
        // Check which parsers should open
        val poe1ParserStateChange = didParserStateChange(
            chatParserPoe1,
            shouldOpenPoe1Parser,
            SaveManager.settingsSaveFile.data.settingsPoe1.installFolder!!
        ) || forceRestart
        val poe2ParserStateChange = didParserStateChange(
            chatParserPoe2,
            shouldOpenPoe2Parser,
            SaveManager.settingsSaveFile.data.settingsPoe2.installFolder!!
        ) || forceRestart
        // Close existing parsers before creating new ones
        if (poe1ParserStateChange) chatParserPoe1?.close()
        if (poe2ParserStateChange) chatParserPoe2?.close()
        val openPoe1 = poe1ParserStateChange && shouldOpenPoe1Parser
        val openPoe2 = poe2ParserStateChange && shouldOpenPoe2Parser
        // If any parsers are going to be reset, post a restart event
        if (openPoe1) expectedParserCount++
        if (openPoe2) expectedParserCount++
        if (expectedParserCount > 0) App.parserEvent.post(ParserEventType.RESTART)
        // Create new parsers
        if (openPoe1) chatParserPoe1 = createChatParser(SaveManager.settingsSaveFile.data.settingsPoe1)
        if (openPoe2) chatParserPoe2 = createChatParser(SaveManager.settingsSaveFile.data.settingsPoe2)
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
        if (!settings.doesClientLogExist()) return false
        return true
    }

    fun createChatParser(settings: GameSettings): ChatParser? {
        if (settings.notInstalled) return null
        if (!settings.doesClientLogExist()) {
            System.err.println("Client.txt not found!")
            return null
        }
        val parser = ChatParser(settings.game)
        addParserListeners(settings, parser)
        parser.onInitListeners += this
        parser.onLoadListeners += this
//        parser.tradeListeners.addAll(tradeListeners)
//        parser.chatScannerListeners.addAll(chatScannerListeners)
//        parser.joinedAreaListeners.addAll(joinedAreaListeners)
//        parser.dndListeners.addAll(dndListeners)
        parser.open(settings.clientPath)
        return parser
    }

    /** NOTE: Since chat parsers can be closed and reopened, listeners are
     * added here instead of in the UI elements like usual. */
    // FIXME : Moving these to the UI elements requires making the parser reopen-able.
    @Deprecated("Move to new event system")
    fun addParserListeners(settings: GameSettings, parser: ChatParser) {
        // Menu Bar
        // FIXME:
        parser.addOnLoadedCallback(FrameManager.menuBarIcon)
        parser.addOnLoadedCallback(FrameManager.menuBarDialog)
        parser.addDndListener(FrameManager.menuBarIcon)
        parser.addDndListener(FrameManager.menuBarDialog)
    }

    override fun handleTrade(tradeOffer: TradeOffer, loaded: Boolean) {
        tradeListeners.forEach { it.handleTrade(tradeOffer, loaded) }
    }

    override fun onScannerMessage(entry: ChatScannerEntry, message: PlayerMessage, loaded: Boolean) {
        chatScannerListeners.forEach { it.onScannerMessage(entry, message, loaded) }
    }

    override fun onParserRestart() {
        println("PARSERS RESTARTED!!!")
        parsersRestarted++
    }

    /** Posts a LOADED event after all expected chat parsers have loaded. */
    override fun onParserLoaded(dnd: Boolean) {
        parserLoadedCount++
        if (parserLoadedCount == expectedParserCount) {
            App.parserEvent.post(ParserEventType.LOADED)
            println("PARSERS LOADED!!!")
        }
    }

//    override fun onJoinedArea(playerName: String) {
//        playerJoinedAreaListeners.forEach { it.onJoinedArea(playerName) }
//    }

}
