package github.zmilla93.core.chatparser

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.core.event.ChatScannerEvent
import github.zmilla93.core.event.ParserEvent
import github.zmilla93.core.event.PlayerJoinedAreaEvent
import github.zmilla93.core.event.TradeEvent
import github.zmilla93.core.events.ZoneChangedEvent
import github.zmilla93.core.managers.AudioManager
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.core.trading.LangRegex
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.trading.TradeOfferType
import github.zmilla93.core.trading.WhisperData
import github.zmilla93.modules.filetailing.FileTailer
import github.zmilla93.modules.filetailing.FileTailer.Companion.createTailer
import github.zmilla93.modules.filetailing.FileTailerListener
import github.zmilla93.modules.updater.ZLogger
import java.nio.file.Path
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Parses Path of Exile's chat using a [FileTailer] and listening for [FileTailerListener] events.
 */
// FIXME : This file could use some cleanup.
// FIXME : Consolidate regex in References and LangRegex with this.
class ChatParser(private val game: Game) : FileTailerListener {

    companion object {
        // File Tailing
        const val tailerDelayMS: Int = 250

        // FIXME : Could extract debug text for analysis.
        /** A full client.txt line. Extracts date, time, and message. Skips debug text.  */
        const val CLIENT_MESSAGE_REGEX: String =
            "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*] (?<message>.+)"

        /** A whisper message. Extracts type (to/from), player name, and message. */
        const val CLIENT_WHISPER_REGEX: String =
            "@(?<messageType>От кого|\\S+) (?<guildName><.+>)? ?(?<playerName>[^:]+):(\\s+)(?<message>.+)"

        // Compiled regex
        private val clientMessage = Pattern.compile(CLIENT_MESSAGE_REGEX)
        private val clientWhisper = Pattern.compile(CLIENT_WHISPER_REGEX)

        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")

    }

    private var tailer: FileTailer? = null

    /** Parser is multithreaded, so CopyOnWriteArrays are used to avoid concurrency modification exceptions. */
    // FIXME : Switch to event bus
    val onInitListeners = CopyOnWriteArrayList<ParserRestartListener>()
    val onLoadListeners = CopyOnWriteArrayList<ParserLoadedListener>()
    val dndListeners = CopyOnWriteArrayList<DndListener>()
    var path: Path? = null
        private set

    // State
    var isOpen: Boolean = false
        private set
    private var lineCount = 0
    private var whisperCount = 0
    private var tradeCount = 0
    var currentZone: String = "The Twilight Strand"
        private set
    private var dnd = false
    private var startTime: Long = 0
    private val loaded get() = tailer!!.isLoaded


    @JvmOverloads
    fun open(path: Path?, isPathRelative: Boolean = false) {
        if (this.isOpen) return
        this.path = path
        lineCount = 0
        whisperCount = 0
        tradeCount = 0
        if (path == null) {
            ZLogger.err("Chat parser was given a null path!")
            return
        }
        if (!path.toFile().exists()) {
            ZLogger.err("Chat parser was given a file that doesn't exist: " + path)
        }
        ZLogger.log("Starting chat parser for " + game.explicitName + ".")
        tailer = createTailer(path, isPathRelative, this, tailerDelayMS, false)
        startTime = System.currentTimeMillis()
        this.isOpen = true
    }

    fun close() {
        if (!this.isOpen) {
            System.err.println("Tried to close a parser that wasn't open!")
            return
        }
        tailer!!.stop()
        tailer = null
        path = null
        this.isOpen = false
        ZLogger.log("Closed chat parser for " + game.explicitName + ".")
    }

    fun parseLine(line: String) {
        if (!this.isOpen) return
        val fullClientMessage: Matcher = clientMessage.matcher(line)
        if (!fullClientMessage.matches()) return
        val fullMessage = fullClientMessage.group("message")
        val date = fullClientMessage.group("date")
        val time = fullClientMessage.group("time")
        if (fullMessage == null || fullMessage.isEmpty() || date == null || time == null) return
        val firstChar = fullMessage[0]
//        println("LOG $date $time - $fullMessage")
//        val chatMessage = ChatMessage(date, time, fullMessage)
        val timestamp = LocalDateTime.parse("$date $time", formatter)
        lineCount++
        val chatMessage = ChatMessage(game, date, time, fullMessage)
        App.events.post(chatMessage)
        // Meta stuff
        // FIXME : Switch to passing fullMessage to everything
        if (firstChar == ':') {
            if (handleZoneChange(timestamp, line)) return
            if (handleDndToggle(line)) return
            if (handlePlayerJoinedArea(line)) return
        }
        // Whispers
        if (firstChar == '@') {
            whisperCount++
            val whisperMatcher: Matcher = clientWhisper.matcher(fullMessage)
            if (whisperMatcher.matches()) {
                val message = whisperMatcher.group("message")
                val guildName = if (game.isPoe1) whisperMatcher.group("guildName") else null
                val playerName = whisperMatcher.group("playerName")
                val messageType = whisperMatcher.group("messageType")
                val metaData = WhisperData()
                metaData.date = date
                metaData.time = time
                metaData.message = message
                metaData.guildName = guildName
                metaData.playerName = playerName
                metaData.offerType = LangRegex.getMessageType(messageType)
                if (metaData.offerType == TradeOfferType.UNKNOWN) return
                if (message != null && handleTradeOffer(metaData, message)) {
                    tradeCount++
                    return
                }
            }
        }
        // Scanner
        if (handleChatScanner(line)) return
    }

    private fun handleChatScanner(line: String): Boolean {
        if (!SaveManager.chatScannerSaveFile.data.searching) return false
        val chatMatcher = References.chatPatten.matcher(line)
        val metaMatcher = References.clientMetaPattern.matcher(line)
        var message: String
        var messageType: String?
        var player: String?
        if (chatMatcher.matches()) {
            message = chatMatcher.group("message")
            messageType = chatMatcher.group("messageType")
            player = chatMatcher.group("playerName")
        } else if (metaMatcher.matches()) {
            message = metaMatcher.group("message")
            messageType = "meta"
            player = "Meta Text"
        } else {
            return false
        }
        if (messageType == null) return false
        message = message.lowercase(Locale.getDefault())
        messageType = messageType.lowercase(Locale.getDefault())
        // Iterate though all active searches and look for matching phrases
        for (entry in SaveManager.chatScannerSaveFile.data.activeSearches) {
            if (entry.getSearchTerms() == null) continue
            for (term in entry.getSearchTerms()) {
                if (message.contains(term)) {
                    var allow = false
                    var ignore = false
                    // Check if this message should be ignored
                    if (entry.getIgnoreTerms() != null) {
                        for (ignoreTerm in entry.getIgnoreTerms()) {
                            if (message.contains(ignoreTerm)) {
                                ignore = true
                                break
                            }
                        }
                    }
                    if (ignore) continue
                    // Verify the message is allowed
                    if (entry.allowGlobalAndTradeChat && (messageType == "#" || messageType == "$")) allow = true
                    if (entry.allowWhispers) {
                        for (lang in LangRegex.entries) {
                            if (lang.messageFrom == null) continue
                            if (messageType.contains(lang.messageFrom)) {
                                allow = true
                                break
                            }
                        }
                    }
                    var isMetaText = false
                    if (entry.allowMetaText && messageType == "meta") {
                        allow = true
                        player = entry.title
                        isMetaText = true
                    }
                    if (!allow) continue
                    val playerMessage = PlayerMessage(player, message, isMetaText)
                    App.events.post(ChatScannerEvent(entry, playerMessage, loaded))
                    return true
                }
            }
        }
        return false
    }

    private fun handleTradeOffer(data: WhisperData?, line: String?): Boolean {
        // Check for a trade
        val offer = TradeOffer.getTradeFromMessage(data, line, game)
        if (offer == null) return false
        // Check if the trade should be ignored
        val itemNameLower = offer.itemName.lowercase(Locale.getDefault())
        if (offer.offerType == TradeOfferType.INCOMING_TRADE && SaveManager.ignoreSaveFile.data != null) {
            val item = SaveManager.ignoreSaveFile.data.exactMatchIgnoreMap.get(itemNameLower)
            if (item != null && !item.isExpired()) {
                handleIgnoreItem()
                return true
            }
            for (ignoreItemData in SaveManager.ignoreSaveFile.data.containsTextIgnoreList) {
                if (itemNameLower.contains(ignoreItemData.itemNameLower()) && !ignoreItemData.isExpired()) {
                    handleIgnoreItem()
                    return true
                }
            }
        }
        // Handle trade
        App.events.post(TradeEvent(offer, loaded, game))
        return true
    }

    private fun handleDndToggle(line: String): Boolean {
        val metaMatcher = References.clientMetaPattern.matcher(line)
        if (!metaMatcher.matches()) return false
        val message = metaMatcher.group("message")
        if (message == null) return false
        for (lang in LangRegex.entries) {
            if (lang.dndOn == null || lang.dndOff == null) continue
            if (message.contains(lang.dndOn)) {
                dnd = true
                for (listener in dndListeners) listener.onDndToggle(dnd, loaded)
                return true
            }
            if (message.contains(lang.dndOff)) {
                dnd = false
                for (listener in dndListeners) listener.onDndToggle(dnd, loaded)
                return true
            }
        }
        return false
    }

    private fun handleIgnoreItem() {
        if (loaded) AudioManager.playSoundComponent(SaveManager.settingsSaveFile.data.itemIgnoredSound)
    }

    private fun handlePlayerJoinedArea(line: String): Boolean {
        for (lang in LangRegex.entries) {
            if (lang.joinedArea == null) continue
            val matcher = lang.joinedAreaPattern.matcher(line)
            if (matcher.matches()) {
                val playerName = matcher.group("playerName")
//                for (listener in playerJoinedAreaListeners) {
//                    listener.onJoinedArea(playerName)
//                }
                App.events.post(PlayerJoinedAreaEvent(playerName, loaded))
                return true
            }
        }
        return false
    }

    private fun handleZoneChange(time: LocalDateTime, line: String): Boolean {
        for (lang in LangRegex.entries) {
            if (lang.enteredArea == null) continue
            val matcher = lang.enteredAreaPattern.matcher(line)
            if (matcher.matches()) {
                currentZone = matcher.group("zone")
                App.parserEvents.post(ZoneChangedEvent(time, loaded, currentZone))
                return true
            }
        }
        return false
    }

    fun addDndListener(listener: DndListener?) {
        dndListeners.add(listener)
    }


    // File Tailing
    override fun init(tailer: FileTailer) {
        for (listener in onInitListeners) listener.onParserRestart()
        App.events.post(ParserEvent(ParserEvent.Type.RESTART, game.parser))
    }

    override fun fileNotFound() {
    }

    override fun fileRotated() {
    }

    override fun onLoad() {
        val endTime = (System.currentTimeMillis() - startTime) / 1000f
        ZLogger.log("Chat parser loaded for " + game.explicitName + " in " + endTime + " seconds. Found " + lineCount + " lines, " + whisperCount + " whispers, and " + tradeCount + " trades.")
        for (listener in onLoadListeners) listener.onParserLoaded(dnd)
    }

    override fun handle(line: String) {
        parseLine(line)
//        try {
//        } catch (e: Exception) {
//            CrashReportWindow.showCrashReport(e)
//            // Throw the error so that the parser stops
//            throw e
//        }
    }

}
