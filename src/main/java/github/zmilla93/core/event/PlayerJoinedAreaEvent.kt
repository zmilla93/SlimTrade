package github.zmilla93.core.event

import github.zmilla93.core.chatparser.ChatEvent

data class PlayerJoinedAreaEvent(val playerName: String, override val loaded: Boolean) : ChatEvent