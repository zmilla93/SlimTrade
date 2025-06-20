package github.zmilla93.gui.stash

import github.zmilla93.core.enums.StashTabColor
import github.zmilla93.core.trading.TradeOffer
import github.zmilla93.core.utility.AdvancedMouseListener
import github.zmilla93.core.utility.POEInterface
import github.zmilla93.core.utility.TradeUtil
import github.zmilla93.core.utility.ZUtil.getGC
import github.zmilla93.core.utility.ZUtil.removeFromParent
import github.zmilla93.gui.components.CurrencyLabelFactory
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.theme.ThemeManager.lighter
import github.zmilla93.modules.theme.components.AdvancedButton
import java.awt.Cursor
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import javax.swing.*
import javax.swing.border.Border

/**
 * A helper panel that displays an item name and stash tab name. Use StashHelperBulkWrapper for bulk trades.
 * Displays a [StashHighlighterFrame] when hovered. Searches item name in stash when clicked.
 */
class StashHelperPanel : AdvancedButton {
    private val tradeOffer: TradeOffer
    var highlighterFrame: StashHighlighterFrame? = null
        private set
    private var stashTabColor: StashTabColor? = null

    private val searchTerm: String
    private var index = -1

    /**
     * A stash helper for a regular trade offer.
     */
    @JvmOverloads
    constructor(tradeOffer: TradeOffer, addToParent: Boolean = true, addListeners: Boolean = true) {
        this.tradeOffer = tradeOffer
        searchTerm = TradeUtil.cleanItemName(tradeOffer.itemName)
        buildPanel(addToParent, addListeners)
    }

    /**
     * Used when creating helpers for bulk trades.
     */
    constructor(tradeOffer: TradeOffer, index: Int) {
        this.tradeOffer = tradeOffer
        this.index = index
        val saleItem = tradeOffer.getItems()[index]
        searchTerm = TradeUtil.cleanItemName(saleItem.itemName)
        //        PriceLabel.applyBulkItemToComponent(priceLabel, tradeOffer, index);
        buildPanel()
    }

    private fun buildPanel(addToParent: Boolean = true, addListeners: Boolean = true) {
        assert(SwingUtilities.isEventDispatchThread())
        // FIXME : default visibility to true and make sure no debug panels are being added
        setVisible(false)
        if (tradeOffer.stashTabName != null) {
            if (tradeOffer.game.isPoe1) highlighterFrame = StashHighlighterFramePoe1(tradeOffer)
            else highlighterFrame = StashHighlighterFramePoe2(tradeOffer)
        }
        setCursor(Cursor(Cursor.HAND_CURSOR))
        setLayout(GridBagLayout())
        val stashTabLabel = JLabel(tradeOffer.stashTabName)
        val itemPanel = JPanel()
        itemPanel.setOpaque(false)
        if (tradeOffer.isBulkTrade) {
            CurrencyLabelFactory.applyItemToComponent(
                itemPanel,
                tradeOffer.game,
                tradeOffer.getItems().get(index).toArrayList()
            )
        } else {
            CurrencyLabelFactory.applyItemToComponent(itemPanel, tradeOffer.game, tradeOffer.getItems())
        }

        val gc = getGC()
        add(stashTabLabel, gc)
        gc.gridy++
        add(itemPanel, gc)
        gc.gridy++
        stashTabColor = this.tradeOffer.getStashTabColor()
        if (stashTabColor != StashTabColor.ZERO) {
            setBackground(stashTabColor!!.background)
            setBackgroundHover(lighter(stashTabColor!!.background))
            stashTabLabel.setForeground(stashTabColor!!.foreground)
            CurrencyLabelFactory.applyColorToLabel(itemPanel, stashTabColor!!.foreground)
        }
        createBorder(stashTabColor!!)
        revalidate()
        if (addListeners) addListeners()
        if (!this.tradeOffer.isBulkTrade && addToParent) {
            if (tradeOffer.game.isPoe1) FrameManager.stashHelperContainerPoe1.addHelper(this)
            else FrameManager.stashHelperContainerPoe2.addHelper(this)
        }
    }

    private fun addListeners() {
        addMouseListener(object : AdvancedMouseListener() {
            override fun click(e: MouseEvent) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.searchInStash(searchTerm)
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    setVisible(false)
                    if (highlighterFrame != null) highlighterFrame!!.setVisible(false)
                }
            }

            override fun mouseEntered(e: MouseEvent?) {
                super.mouseEntered(e)
                if (highlighterFrame != null) {
                    highlighterFrame!!.setVisible(true)
                    highlighterFrame!!.stopTimer()
                }
            }

            override fun mouseExited(e: MouseEvent?) {
                super.mouseExited(e)
                if (highlighterFrame != null) {
                    highlighterFrame!!.startTimer()
                }
            }
        })
    }

    private fun createBorder(stashTabColor: StashTabColor) {
        var color = stashTabColor.foreground
        if (stashTabColor == StashTabColor.ZERO) color = UIManager.getColor("Label.foreground")
        val insetHorizontal = 6
        val insetVertical = 4
        val innerBorder =
            BorderFactory.createEmptyBorder(insetVertical, insetHorizontal, insetVertical, insetHorizontal)
        val outerBorder = BorderFactory.createLineBorder(color, 2)
        val compoundBorder: Border = BorderFactory.createCompoundBorder(outerBorder, innerBorder)
        setBorder(compoundBorder)
    }

    fun cleanup() {
        /** Hide first to trigger a repack on parent window */
        setVisible(false)
        removeFromParent(this)
        if (!tradeOffer.isBulkTrade) {
            if (tradeOffer.game.isPoe1) FrameManager.stashHelperContainerPoe1.remove(this)
            else FrameManager.stashHelperContainerPoe2.remove(this)
        }
        if (highlighterFrame != null) {
            highlighterFrame!!.dispose()
            highlighterFrame = null
        }
    }

    private fun updateParent() {
        val parent = getTopLevelAncestor()
        if (parent == null) return
        if (parent is StashHelperContainer) {
            parent.updateBounds()
        }
    }

    // Repack the parent window anytime the visibility of this panel changes
    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        updateParent()
    }

    override fun updateUI() {
        super.updateUI()
        if (stashTabColor == StashTabColor.ZERO) {
            createBorder(stashTabColor!!)
        }
    }

}
