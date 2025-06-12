package github.zmilla93.modules.theme.components

import github.zmilla93.core.utility.ImageUtil
import github.zmilla93.modules.theme.ThemeManager.addFrame
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import java.awt.Image
import java.awt.event.WindowEvent
import java.awt.event.WindowFocusListener
import javax.swing.JFrame

open class ThemeDialog : JFrame {

    companion object {

        // FIXME : Should add prescaled versions for 32x
        val appIcon: Image = ImageUtil.resourceImage("/icons/default/chaos-orb.png")

        init {

        }

    }


    constructor()

    constructor(title: String) : super() {
        this.title = title
    }

    init {
        addFrame(this)
        if (appIcon == null) System.err.println("[ThemeDialog] App icon is not defined!")
        else iconImage = appIcon

        // FIXME : This fixes window layering issues, but can cause an infinite loop without debouncing.
        //  A better solution would be nice before a full release
        //  Idea: Track gain/lose events, see what order of execution is
        addWindowFocusListener(object : WindowFocusListener {
            override fun windowGainedFocus(e: WindowEvent?) {
                setAlwaysOnTop(false)
                setAlwaysOnTop(true)
            }

            override fun windowLostFocus(e: WindowEvent?) {
            }
        })
    }

    override fun dispose() {
        super.dispose()
        removeFrame(this)
    }

}
