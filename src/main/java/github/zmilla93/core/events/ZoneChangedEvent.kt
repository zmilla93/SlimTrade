package github.zmilla93.core.events

import java.time.LocalDateTime

data class ZoneChangedEvent(val time: LocalDateTime, val loaded: Boolean, val currentZone: String)