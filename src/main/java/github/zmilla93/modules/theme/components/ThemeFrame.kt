package github.zmilla93.modules.theme.components

import github.zmilla93.modules.theme.ThemeManager.addFrame
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import javax.swing.JFrame

open class ThemeFrame() : JFrame() {

    constructor(title: String) : this() {
        this.title = title
    }

    init {
        addFrame(this)
        if (ThemeDialog.appIcon == null) System.err.println("[ThemeDialog] App icon is not defined!")
        else {
            iconImage = ThemeDialog.appIcon
        }
    }

    override fun dispose() {
        super.dispose()
        removeFrame(this)
    }

}
