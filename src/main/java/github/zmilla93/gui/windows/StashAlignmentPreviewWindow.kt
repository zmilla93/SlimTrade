package github.zmilla93.gui.windows

import github.zmilla93.App
import github.zmilla93.core.events.GameChangedEvent
import github.zmilla93.core.poe.Game
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.gui.stash.GridPanel
import github.zmilla93.modules.theme.ThemeManager
import java.awt.BorderLayout
import java.awt.Window
import javax.swing.Timer

class StashAlignmentPreviewWindow : BasicDialog() {

    val stashHelperPreviewWindow = StashHelperPreviewWindow()

    private val stashPreviewTimer = Timer(3000) { FrameManager.stashAlignmentPreviewWindow.isVisible = false }

    init {
        stashHelperPreviewWindow.isVisible = false
        stashPreviewTimer.isRepeats = false
        // FIXME : Temp
//        isVisible = true
//        updateBounds(Game.PATH_OF_EXILE_1)
        layout = BorderLayout()
        background = ThemeManager.TRANSPARENT
        add(GridPanel(), BorderLayout.CENTER)
        pack()
        App.events.subscribe(GameChangedEvent::class.java) { updateBounds(it.currentGame) }
    }

    // FIXME : Just combine this with showPreview?
    @JvmOverloads
    fun updateBounds(game: Game = App.chatParser.currentGame) {
        pack()
        bounds = POEWindow.stashBounds(game)
        stashHelperPreviewWindow.updateBounds(game)
    }

    /**
     * Shows the preview window, then hides it after a delay.
     * Optionally Pass in a window to force to top so that the
     * preview doesn't render on top of the window that showed it.
     */
    fun showPreview(window: Window? = null, game: Game = App.chatParser.currentGame) {
        isVisible = true
        updateBounds(game)
        stashPreviewTimer.restart()
        if (window != null && window.isAlwaysOnTop) {
            window.isAlwaysOnTop = false
            window.isAlwaysOnTop = true
        }
    }

    override fun setVisible(visible: Boolean) {
        super.setVisible(visible)
        stashHelperPreviewWindow.isVisible = visible
    }

}