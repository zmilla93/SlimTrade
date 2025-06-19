package github.zmilla93.core.chatparser

import github.zmilla93.core.poe.Game
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ChatMessage(val game: Game, val time: LocalDateTime, val message: String) {

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    }

    constructor(game: Game, date: String, time: String, message: String) : this(
        game, LocalDateTime.parse("$date $time", formatter), message
    )

    override fun toString(): String {
        return "ChatMessage(game=$game, time=$time, message='$message')"
    }

}