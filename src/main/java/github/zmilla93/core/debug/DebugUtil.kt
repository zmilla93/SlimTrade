package github.zmilla93.core.debug

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.core.poe.GameSettings
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DebugUtil {

    private const val testIncomingTrade =
        "@From ExampleExile123: Hi, I would like to buy your Kaom's Heart Conqueror Plate listed for 233 divine in Standard (stash tab \"sale\"; position: left 11, top 10)"
    private const val testOutgoingTrade =
        "@From ExampleExile123: Hi, I would like to buy your Kaom's Heart Conqueror Plate listed for 233 divine in Standard (stash tab \"sale\"; position: left 11, top 10)"
    private const val DEBUG_TEXT = " 000000000 00000000 [Debug] "

    fun incomingTrade(game: Game) {
        appendClient(game, testIncomingTrade)
    }

    fun outgoingTrade(game: Game) {
        appendClient(game, testOutgoingTrade)
    }

    fun appendClient(game: Game, message: String) {
        val settings: GameSettings
        if (game.isPoe1) settings = SaveManager.settingsSaveFile.data.settingsPoe1
        else settings = SaveManager.settingsSaveFile.data.settingsPoe2
        if (settings.clientPath == null) return
        val line = getTimestamp() + DEBUG_TEXT + message
        try {
            settings.clientPath!!.toFile().appendText(line + System.lineSeparator())
        } catch (e: Exception) {
            println("Error writing to client.txt (${game.explicitName}): ${e.message}")
        }
    }

    private fun getTimestamp(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        return current.format(formatter)
    }

}