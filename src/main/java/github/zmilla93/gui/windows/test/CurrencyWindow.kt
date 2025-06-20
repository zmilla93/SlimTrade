package github.zmilla93.gui.windows.test

import github.zmilla93.core.enums.CurrencyType
import github.zmilla93.gui.components.CustomScrollPane
import github.zmilla93.modules.theme.components.ThemeFrame
import github.zmilla93.modules.zswing.extensions.PanelExtensions.fitParentWidth
import github.zmilla93.modules.zswing.theme.IconManager
import java.awt.Dimension
import java.awt.FlowLayout
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel

class CurrencyWindow : ThemeFrame("Icon Tester") {

    val iconSize = 40

    init {
        val panel = JPanel(FlowLayout()).fitParentWidth()
        val wrapper = JPanel(GridBagLayout())
        add(CustomScrollPane(panel))
        for (currency in CurrencyType.currencyNamesPoe1) {
            try {
                val icon = IconManager.getIcon("/poe1/icons/$currency.png", iconSize)
                panel.add(JLabel(icon))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        for (currency in CurrencyType.currencyNamesPoe2) {
            try {
                val icon = IconManager.getIcon("/poe2/icons/${currency.replace(":", "_")}.png", iconSize)
                panel.add(JLabel(icon))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        pack()
        size = Dimension(800, 600)
    }

}