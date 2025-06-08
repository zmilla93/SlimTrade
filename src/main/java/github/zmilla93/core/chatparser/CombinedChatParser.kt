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
        // Return early if no parser state changes occur
        var parserChangedCount = 0
        if (poe1ParserStateChange) parserChangedCount++
        if (poe2ParserStateChange) parserChangedCount++
        if (parserChangedCount == 0) return
        // Close existing parsers before creating new ones
        if (poe1ParserStateChange) if (chatParserPoe1 != null) chatParserPoe1?.close()
        if (poe2ParserStateChange) if (chatParserPoe2 != null) chatParserPoe2?.close()
        parserLoadedCount = 0
        expectedParserCount = 0
        // If any parsers are going to be reset, post a restart event
        if (shouldOpenPoe1Parser) expectedParserCount++
        if (shouldOpenPoe2Parser) expectedParserCount++
        App.parserEvent.post(ParserEventType.RESTART)
        // Create new parsers
        if (shouldOpenPoe1Parser) chatParserPoe1 = createChatParser(SaveManager.settingsSaveFile.data.settingsPoe1)
        if (shouldOpenPoe2Parser) chatParserPoe2 = createChatParser(SaveManager.settingsSaveFile.data.settingsPoe2)
    }

    /** Returns true if the chat parser is switching between on and off, or if already running but the client.txt path changed. */
    private fun didParserStateChange(parser: ChatParser?, shouldOpen: Boolean, newPath: String): Boolean {
        val isRunning = parser != null && parser.isOpen
        val runningChanged = isRunning != shouldOpen
        var pathChanged = false
        if (isRunning && shouldOpen) pathChanged = !parser.path!!.startsWith(newPath)
        return runningChanged || pathChanged
    }

    /** Returns true if a valid client.txt file exists. */
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
        parser.onLoadListeners += FrameManager.menuBarIcon
        parser.onLoadListeners += FrameManager.menuBarDialog
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
        println("Loaded Parser($parserLoadedCount)")
        if (parserLoadedCount == expectedParserCount) {
            App.parserEvent.post(ParserEventType.LOADED)
            println("PARSERS LOADED!!!")
        }
    }

//    override fun onJoinedArea(playerName: String) {
//        playerJoinedAreaListeners.forEach { it.onJoinedArea(playerName) }
//    }

}
