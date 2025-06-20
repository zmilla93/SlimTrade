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
        val stashBounds = POEWindow.getStashBounds()
        val helperY = POEWindow.getGameBounds().y + POEWindow.getStashHelperOffset() - getHeight();
        setLocation(stashBounds.x, helperY)
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        if (visible) updateBounds()
    }

}