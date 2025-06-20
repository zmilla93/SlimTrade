package github.zmilla93.gui.windows

import github.zmilla93.core.poe.Game
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.gui.stash.StashHelperContainer

class StashHelperPreviewWindow : StashHelperContainer() {

    private var game: Game? = Game.PATH_OF_EXILE_1

    init {
        addHelperDebug(TradeOffer.getAlignmentPreviewTradeMessage()).isVisible = true
        addHelperDebug(TradeOffer.getAlignmentPreviewTradeMessage()).isVisible = true
    }

    fun updateBounds(game: Game) {
        this.game = game
        updateBounds()
    }

    override fun updateBounds() {
        if (game == null) return
        val stashBounds = POEWindow.stashBounds(game!!)
        pack()
        val helperY = POEWindow.gameBounds.y + POEWindow.stashHelperOffset(game!!) - getHeight();
        setLocation(stashBounds.x, helperY)
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        if (visible) updateBounds()
    }

}