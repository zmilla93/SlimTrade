package github.zmilla93.core.event

import github.zmilla93.core.poe.Game
import github.zmilla93.core.trading.TradeOffer

data class TradeEvent(val tradeOffer: TradeOffer, val isLoaded: Boolean, val game: Game)