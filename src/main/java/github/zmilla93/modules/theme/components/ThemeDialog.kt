package github.zmilla93.modules.theme.components

import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.modules.theme.ThemeManager.addFrame
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import java.awt.Image
import javax.swing.JFrame

open class ThemeDialog : JFrame {

    companion object {
        // FIXME : Should add prescaled versions for 32x
        val appIcon: Image = ImageUtil.resourceImage("/icons/default/chaos-orb.png")
    }

    constructor()

    constructor(title: String) : super() {
        this.title = title
    }

    init {
        addFrame(this)
        if (appIcon == null) System.err.println("[ThemeDialog] App icon is not defined!")
        else iconImage = appIcon
    }

    override fun dispose() {
        super.dispose()
        removeFrame(this)
    }

}
