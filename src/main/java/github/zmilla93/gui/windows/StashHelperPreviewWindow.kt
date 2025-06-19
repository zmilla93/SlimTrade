package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.trading.TradeOfferType
import github.zmilla93.gui.stash.StashHelperContainer
import java.awt.Color
import javax.swing.BorderFactory

class StashHelperPreviewWindow : StashHelperContainer() {

    init {
        addHelper(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE))
        addHelper(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE))
        updateBounds()
        contentPanel.border = BorderFactory.createLineBorder(Color.RED)
    }

    override fun updateBounds() {
        val stashBounds = POEWindow.getStashBounds()
        val helperOffset = POEWindow.getStashHelperOffset()
        val helperY = stashBounds.y - helperOffset - getHeight()
        setLocation(stashBounds.x, helperY)
        revalidate()
        preferredSize = null
        println("PREF: $preferredSize")
        pack()
    }

    override fun setVisible(visible: Boolean) {
        updateBounds()
        super.setVisible(visible)
    }

}