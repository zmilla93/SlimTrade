package github.zmilla93.gui.donate

import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.RoundBorder
import github.zmilla93.gui.components.RoundPanel
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.IconManager
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.JLabel

class PayPalNamePlate(name: String, tier: PayPalTier) : RoundPanel() {

    var fontSize = 14
    var iconSize = 20
    val insetX = 10
    val insetY = 2

    init {
        // Label
//        val icon = JLabel()
        val label = JLabel(name).bold()
//        label.foreground = tier.foreground
//        background = tier.background
//        border = BorderFactory.createLineBorder(tier.foreground, 2)
        label.fontSize(fontSize)
        // FIXME : Switch to icon cache
//        label.icon = ImageIcon(ImageUtil.scaledImage(tier.image, 75))
        label.icon = IconManager.getIcon(tier.image, 20)
        // Add
        layout = GridBagLayout()
        val gc = ZUtil.getGC()
        gc.insets = Insets(insetY, insetX, insetY, insetX)
        add(label, gc)

        border = RoundBorder()
    }

}