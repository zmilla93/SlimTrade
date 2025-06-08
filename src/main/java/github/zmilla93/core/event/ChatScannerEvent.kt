package github.zmilla93.core.event

import github.zmilla93.core.data.PlayerMessage
import github.zmilla93.gui.chatscanner.ChatScannerEntry

data class ChatScannerEvent(val entry: ChatScannerEntry, val message: PlayerMessage, val loaded: Boolean)