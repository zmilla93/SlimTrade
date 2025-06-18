package github.zmilla93.gui.options

import com.formdev.flatlaf.ui.FlatBorder
import github.zmilla93.core.References
import github.zmilla93.core.utility.ZUtil.openLink
import github.zmilla93.gui.components.BorderPanel
import github.zmilla93.gui.components.BoxPanel
import github.zmilla93.gui.components.ComponentPanel
import github.zmilla93.gui.components.FlowPanel
import github.zmilla93.gui.donate.*
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.ThemeColor
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JProgressBar

class DonationPanel : JPanel() {

    val patreonButton = JButton("Patreon").onClick { openLink(References.PATREON_URL) }
    private val paypalButton = JButton("PayPal").onClick { openLink(References.PAYPAL_URL) }

    init {
        layout = BorderLayout()
        add(HeaderPanel(), BorderLayout.NORTH)
        add(BodyPanel(), BorderLayout.CENTER)
    }

    inner class HeaderPanel() : BoxPanel() {
        init {
            largeHeaders()

            /** Intro - Blurb and progress bar */
            header(PatreonTier.HEADER_TEXT)
            label(PatreonTier.INFO_TEXT)
            strutSmall()
            add(JLabel(PatreonTier.GOAL_TEXT).bold())
            val progressBar = JProgressBar()
            progressBar.value = Supporters.getCurrentPatreonCount()
            progressBar.maximum = PatreonTier.PATRON_GOAL
            add(progressBar)
            strutSmall()
            addLeft(
                ComponentPanel(
                    JButton("Roadmap").onClick { FrameManager.roadMapWindow.isVisible = true },
                    JButton("Patch Notes").onClick { FrameManager.patchNotesWindow.isVisible = true }
                ))
            strut()

            /** Patreon Info */
            header("Donate")
            val patreonTierPanel = FlowPanel()
            patreonTierPanel.add(patreonButton)
            for (tier in PatreonTier.entries)
                patreonTierPanel.add(PatreonNamePlate(PatreonSupporter(tier.amountText, tier, PayPalTier.ZERO)))
            addLeft(patreonTierPanel)
//            strut()

            /** PayPal Info */
//            header("PayPal")
            val paypalTiers = JPanel(FlowLayout())
            paypalTiers.add(paypalButton)
            for (tier in PayPalTier.entries) {
                if (tier.amount == 0) continue
                paypalTiers.add(PayPalNamePlate("$" + tier.amount, tier))
            }
            addLeft(paypalTiers)
            strut()

            /** Header for the [BodyPanel]. */
            header("Supporters")
            addCenter(JLabel("Thank you! :)").bold().fontSize(20).textColor(ThemeColor.GREEN))
        }
    }

    class BodyPanel : JPanel() {
        init {
            background = Color.GREEN
            layout = BorderLayout()

            val patreonSupporters = JPanel(FlowLayout(FlowLayout.LEFT))
            for (patron in Supporters.patreon)
                patreonSupporters.add(PatreonNamePlate(patron))

            val paypalSupporters = JPanel(FlowLayout())
            for (patron in Supporters.paypal)
                patreonSupporters.add(PayPalNamePlate(patron.name, patron.tier))

            val wrapPanel = BorderPanel(patreonSupporters, BorderLayout.CENTER)

//            val boxPanel = JPanel()
//            boxPanel.layout = BoxLayout(boxPanel, BoxLayout.PAGE_AXIS)
//            boxPanel.add(CustomScrollPane(wrapPanel))
//            boxPanel.add(paypalSupporters)

            add(patreonSupporters, BorderLayout.CENTER)
        }
    }

    override fun updateUI() {
        border = FlatBorder()
    }

}
