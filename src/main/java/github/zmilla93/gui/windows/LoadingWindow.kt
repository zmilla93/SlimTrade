package github.zmilla93.gui.windows

import github.zmilla93.core.poe.POEWindow
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.gui.components.StyledLabel
import github.zmilla93.gui.listening.IDefaultSizeAndLocation
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import github.zmilla93.modules.updater.data.AppInfo
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.border.BevelBorder

class LoadingWindow(appInfo: AppInfo) : BasicDialog(), IDefaultSizeAndLocation {
    init {
        ignoreVisibilitySystem(true)
        contentPanel.setLayout(GridBagLayout())
        val gc = ZUtil.getGC()
        gc.insets = Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL)
        val loadingLabel: JLabel = StyledLabel("Loading " + appInfo.fullName + "...").bold()
        contentPanel.add(loadingLabel, gc)
        contentPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED))
        pack()
        applyDefaultSizeAndLocation()
    }

    override fun dispose() {
        super.dispose()
        removeFrame(this)
    }

    override fun applyDefaultSizeAndLocation() {
        pack()
        POEWindow.centerWindow(this)
    }

    companion object {
        private const val INSET_HORIZONTAL = 25
        private const val INSET_VERTICAL = 20
    }
}
