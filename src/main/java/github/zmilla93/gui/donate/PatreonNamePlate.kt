package github.zmilla93.gui.donate

import github.zmilla93.core.utility.ZUtil
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel

class PatreonNamePlate(patron: PatreonSupporter) : JPanel() {

    var fontSize = 16
    val insetX = 10
    val insetY = 2

    init {
        // Label
        val label = JLabel(patron.name).bold()
        var displayTier = if (patron.active) patron.tier else PatreonTier.ZERO
        label.foreground = displayTier.foreground
        background = displayTier.background
        border = BorderFactory.createLineBorder(displayTier.foreground, 2)
        label.fontSize(fontSize)
        // Add
        layout = GridBagLayout()
        val gc = ZUtil.getGC()
        gc.insets = Insets(insetY, insetX, insetY, insetX)
        add(label, gc)
    }
}