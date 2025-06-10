package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.RoundBorder
import github.zmilla93.gui.components.RoundPanel
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.modules.theme.ThemeManager
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import github.zmilla93.modules.updater.data.AppInfo
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import io.github.zmilla93.modules.theme.UIProperty.FontSizeExtensions.fontSize
import java.awt.*
import javax.swing.ImageIcon
import javax.swing.JLabel
import javax.swing.JLayeredPane

class LoadingSplashWindow(appInfo: AppInfo) : BasicDialog(), IDefaultSizeAndLocation {

    val image = ImageUtil.resourceImage("/league/sota/LoadingSplash.png")
    val imageLabel = JLabel(ImageIcon(image))

    companion object {
        private const val INSET_HORIZONTAL = 25
        private const val INSET_VERTICAL = 20
    }

    init {
        ignoreVisibilitySystem(true)
        setBackground(ThemeManager.TRANSPARENT)

        val label = JLabel("${appInfo.fullName}").bold().fontSize(16)
        val textInt = 200
        val textColor = Color(textInt, textInt, textInt)
        label.foreground = textColor

        val imgWidth = image.getWidth(null)
        val imgHeight = image.getHeight(null)

        val backgroundPanel = JLayeredPane()
        backgroundPanel.preferredSize = Dimension(imgWidth, imgHeight)

        val gc = ZUtil.getGC()
        imageLabel.bounds = Rectangle(0, 0, imgWidth, imgHeight)
        val insetX = 10
        val insetY = 10
        gc.insets = Insets(insetY, insetX, insetY, insetX)
        val labelPanel = RoundPanel()
        labelPanel.layout = GridBagLayout()
        labelPanel.add(label, gc)
        labelPanel.background = Color(0, 0, 0, 100)
        labelPanel.border = RoundBorder(Color.WHITE)

        val textWidth = labelPanel.preferredSize.width
        val textHeight = labelPanel.preferredSize.height
        // Adjust the label slightly down
        val labelYAdjust = 0
        val labelX = (imgWidth / 2) - textWidth / 2
        val labelY = (imgHeight / 2) - textHeight / 2
        labelPanel.bounds = Rectangle(labelX, labelY + labelYAdjust, textWidth, textHeight)
        backgroundPanel.add(labelPanel, 0)
        backgroundPanel.add(imageLabel, 1)

        contentPanel.setLayout(GridBagLayout())

        contentPanel.add(backgroundPanel)
//        contentPanel.border = BorderFactory.createLineBorder(Color.RED)

        minimumSize = Dimension(image.getWidth(null), image.getHeight(null))
        pack()
        isAlwaysOnTop = true
        applyDefaultSizeAndLocation()
    }

    override fun dispose() {
        removeFrame(this)
        super.dispose()
    }

    override fun applyDefaultSizeAndLocation() {
        pack()
        POEWindow.centerWindow(this)
    }

}
