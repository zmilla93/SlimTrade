package github.zmilla93.core.poe

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.gui.managers.FrameManager
import java.awt.Component
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent

/**
 * Handles showing popups when the app launches.
 */
// FIXME : Should do a bit more standardization on this at some point. Frames are handling their own saving atm.
object LaunchPopups {

    /** Increment this to show the league launch popups. See [LaunchPopups] */
    const val LEAGUE_POPUP_VERSION = 1

    /** Increment to show the tutorial window on launch. */
    const val TUTORIAL_VERSION = 1


    /** Try and show the next popup that hasn't been shown yet, if any. */
    fun tryShowPopups() {
        if (checkTutorial()) return
        if (checkRoadmap()) return
        if (tryShowPatchNotes()) return
        if (checkDonate()) return
    }

    fun registerFrame(comp: Component, markAsViewed: () -> Unit) {
        comp.addComponentListener(object : ComponentAdapter() {
            override fun componentShown(e: ComponentEvent) {
                markAsViewed()
            }

            override fun componentHidden(e: ComponentEvent?) {
                tryShowPopups()
            }
        })
    }

    fun markPatchNotes() {
        if (SaveManager.appStateSaveFile.data.patchNotesPopupVersion == LEAGUE_POPUP_VERSION) return
        SaveManager.appStateSaveFile.data.patchNotesPopupVersion = LEAGUE_POPUP_VERSION
        SaveManager.appStateSaveFile.saveToDisk()
    }

    fun checkTutorial(): Boolean {
        if (SaveManager.appStateSaveFile.data.tutorialVersion == TUTORIAL_VERSION) return false
        SaveManager.appStateSaveFile.data.tutorialVersion = TUTORIAL_VERSION
        SaveManager.appStateSaveFile.saveToDisk()
        FrameManager.tutorialWindow.isVisible = true
        return true
    }

    fun checkRoadmap(): Boolean {
        if (SaveManager.appStateSaveFile.data.roadmapPopupVersion == LEAGUE_POPUP_VERSION) return false
        println("Show Roadmap")
        SaveManager.appStateSaveFile.data.roadmapPopupVersion = LEAGUE_POPUP_VERSION
        SaveManager.appStateSaveFile.saveToDisk()
        FrameManager.roadMapWindow.isVisible = true
        return true
    }

    fun tryShowPatchNotes(): Boolean {
        // FIXME : Add version check.
        if (SaveManager.appStateSaveFile.data.patchNotesPopupVersion == LEAGUE_POPUP_VERSION) return false
        println("Show Patch")
        SaveManager.appStateSaveFile.data.patchNotesPopupVersion = LEAGUE_POPUP_VERSION
        SaveManager.appStateSaveFile.saveToDisk()
        FrameManager.patchNotesWindow.isVisible = true
        return true
    }

    fun checkDonate(): Boolean {
        if (SaveManager.appStateSaveFile.data.donatePopupVersion == LEAGUE_POPUP_VERSION) return false
        println("Show Donate")
        SaveManager.appStateSaveFile.data.donatePopupVersion = LEAGUE_POPUP_VERSION
        SaveManager.appStateSaveFile.saveToDisk()
        FrameManager.optionsWindow.showDonationPanel()
        return true
    }

}