package github.zmilla93.core.saving.savefiles

import github.zmilla93.gui.managers.LaunchPopupManager
import github.zmilla93.modules.saving.AbstractSaveFile
import github.zmilla93.modules.updater.data.AppVersion

/**
 * Used to track info not controlled by the user.
 * Will likely be expanded in the future to allow certain states to be saved
 * when the app is closed, like open trades, windows, scanner, etc.
 */
class AppStateSaveFile : AbstractSaveFile() {

    companion object {
        /** Increment this to show the league launch popups. See [LaunchPopupManager] */
        const val LEAGUE_POPUP_VERSION = 1
        const val TUTORIAL_VERSION = 1
        const val NEW_INSTALL_POPUP_VERSION = 1
    }

    @JvmField
    var kalguurQuantities: ArrayList<Int> = ArrayList()

    @JvmField
    var kalguurTimers: ArrayList<String> = ArrayList()


    //    var patchNotesPopupVersion = LeagueLaunchPopups.LEAGUE_POPUP_VERSION
    @JvmField
    var tutorialVersion = 0
    var roadmapPopupVersion = 0
    var patchNotesPopupVersion: AppVersion? = null
    var donatePopupVersion = 0

    override fun getCurrentTargetVersion(): Int {
        return 0
    }

}
