package github.zmilla93.core.chatparser

import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.gui.chatscanner.ChatScannerEntry

interface ChatScannerListener {
    fun onScannerMessage(entry: ChatScannerEntry, message: PlayerMessage, loaded: Boolean)
}
