package github.zmilla93.gui.managers

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.gui.popup.DonationPopup
import github.zmilla93.gui.popup.PatchNotesPopup
import github.zmilla93.gui.popup.RoadmapPopup
import github.zmilla93.gui.popup.TutorialPopup
import java.awt.Component
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

/**
 * Handles showing popups when the app launches.
 */
// FIXME : Should do a bit more standardization on this at some point. Frames are handling their own saving atm.
object LaunchPopupManager {

    val donationPopup = DonationPopup()

    val popups = listOf(
        TutorialPopup(),
        RoadmapPopup(),
        PatchNotesPopup(),
        donationPopup,
    )

    /** Add callbacks to all frames. */
    fun init() {
        for (popup in popups)
            registerFrameTrigger(popup.window())
    }

    /** Try and show the next popup that hasn't been shown yet, if any. */
    fun tryShowNextPopup() {
        popups.forEach { if (it.window().isVisible) return }
        for (popup in popups) {
            if (popup.shouldPopupBeShown()) {
                popup.showPopup()
                popup.markAsSeen()
                popup.hasShownThisLaunch = true
                SaveManager.appStateSaveFile.saveToDisk()
                return
            }
        }
    }

    /** Causes a frame to try and show the next popup whenever closed. */
    fun registerFrameTrigger(comp: Component) {
        comp.addComponentListener(object : ComponentAdapter() {
            override fun componentHidden(e: ComponentEvent?) {
                tryShowNextPopup()
            }
        })
    }

}