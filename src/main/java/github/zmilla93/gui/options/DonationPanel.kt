package github.zmilla93.gui.options

import github.zmilla93.core.References
import github.zmilla93.core.enums.CurrencyType
import github.zmilla93.core.utility.ZUtil.getBufferedReader
import github.zmilla93.core.utility.ZUtil.getGC
import github.zmilla93.core.utility.ZUtil.openLink
import github.zmilla93.gui.components.*
import github.zmilla93.gui.donate.*
import github.zmilla93.modules.updater.ZLogger
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException
import java.util.*
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel

class DonationPanel : AbstractOptionPanel() {

    private val paypalButton = JButton("PayPal")
    val buttonToFocus: JButton = JButton("Patreon")
    private val supporterPanel = JPanel(GridBagLayout())
    private val supporters: ArrayList<Supporter>

    companion object {
        val text1 = "Software"
    }

    init {
        supporters = parseSupporters()
        background = Color.RED

        val patreonSupporters = BoxPanel()
        for (patron in Supporters.patrons)
            patreonSupporters.add(PatreonNamePlate(patron))

        val paypalSupporters = BoxPanel()
        paypalSupporters.background = Color.ORANGE
        for (patron in Supporters.paypal)
            paypalSupporters.add(PayPalNamePlate(patron.name, patron.tier))

        // Patreon Tiers
        val patreonTiers = FlowPanel()
        for (tier in PatreonTier.entries)
            patreonTiers.add(PatreonNamePlate(PatreonSupporter(tier.amount, tier, true)))

        // Paypal Tiers
        val paypalTiers = JPanel(FlowLayout())
        for (tier in PayPalTier.entries) paypalTiers.add(PayPalNamePlate(tier.amount, tier))


        // Setup panel
        addHeader("Donating")
        add(JLabel("If you enjoy using this app, please consider supporting me! Supporters will be added here."))
        add(JLabel("Contact me if you want your display named changed, or if you'd like to remain anonymous."))
        val htmlText = HTMLTextArea(
            "<html>Software development is time consuming, and a man's gotta eat.<br>" +
                    "<a href=''>Consider supporting me!</a> An occasional dollar really adds up when everyone does it.<br></html>"
        )
        add(htmlText)
        addVerticalStrutSmall()
        add(createButtonPanel())
        add(patreonTiers)
        add(paypalTiers)
        addVerticalStrut()
        addHeader("Thank You!")
//        add(supporterPanel)
        add(patreonSupporters)
        add(CustomScrollPane(paypalSupporters))

        buildSupporterPanel()

        addListeners()
    }

    private fun addListeners() {
        paypalButton.addActionListener { openLink(References.PAYPAL_URL) }
        buttonToFocus.addActionListener(ActionListener { e: ActionEvent? -> openLink(References.PATREON_URL) })
    }

    private fun createButtonPanel(): JPanel {
        val panel = JPanel(GridBagLayout())
        val gc = getGC()
        panel.add(this.buttonToFocus)
        gc.gridx++
        gc.insets.left = 10
        panel.add(paypalButton)
        return panel
    }

    private fun buildSupporterPanel() {
        supporterPanel.removeAll()
        val gc = getGC()
        gc.anchor = GridBagConstraints.WEST
        for (supporter in supporters) {
            val label: JLabel = BasicIconLabel(CurrencyType.getCurrencyType(supporter.currencyType))
            label.setText(supporter.name)
            supporterPanel.add(label, gc)
            gc.gridy++
        }
    }

    private fun parseSupporters(): ArrayList<Supporter> {
        val supporters: ArrayList<Supporter> = ArrayList<Supporter>()
        val reader = getBufferedReader("/text/supporters.txt", true)
        var currentCurrency: String? = null
        try {
            while (reader.ready()) {
                val line = reader.readLine().trim { it <= ' ' }
                if (line.startsWith("=")) continue
                else if (line.startsWith("#")) currentCurrency = getCurrencyType(line)
                else if (line.isNotEmpty()) {
                    if (currentCurrency == null) ZLogger.err("[Donation Panel] Currency not set!")
                    else supporters.add(Supporter(line, currentCurrency))
                }
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        Collections.sort(supporters)
        return supporters
    }

    private fun getCurrencyType(line: String): String? {
        if (line.contains("T1")) return "Orb of Alchemy"
        else if (line.contains("T2")) return "Chaos Orb"
        else if (line.contains("T3")) return "Divine Orb"
        else if (line.contains("PayPal")) return "Regal Orb"
        else if (line.contains("Testers")) return "Blessed Orb"
        ZLogger.err("[Donation Panel] Invalid currency line: " + line)
        return null
    }

    private class Supporter(val name: String, val currencyType: String) : Comparable<Supporter> {
        override fun compareTo(other: Supporter): Int {
            return name.compareTo(other.name)
        }

        override fun toString(): String {
            return name + " : " + currencyType
        }
    }
}
