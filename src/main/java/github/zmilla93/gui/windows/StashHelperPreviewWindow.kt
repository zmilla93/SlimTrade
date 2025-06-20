package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.gui.stash.StashHelperContainer

class StashHelperPreviewWindow : StashHelperContainer() {

    init {
        addHelperDebug(TradeOffer.getAlignmentPreviewTradeMessage()).isVisible = true
        addHelperDebug(TradeOffer.getAlignmentPreviewTradeMessage()).isVisible = true
    }

    override fun updateBounds() {
        contentPanel.revalidate()
        revalidate()
        pack()
        val stashBounds = POEWindow.stashBounds
        val helperY = POEWindow.gameBounds.y + POEWindow.stashHelperOffset - getHeight();
        setLocation(stashBounds.x, helperY)
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        if (visible) updateBounds()
    }

}