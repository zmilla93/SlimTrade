package github.zmilla93.core.saving.savefiles

import github.zmilla93.modules.saving.AbstractSaveFile

/**
 * Used to track info not controlled by the user.
 * Will likely be expanded in the future to allow certain states to be saved
 * when the app is closed, like open trades, windows, scanner, etc.
 */
class AppStateSaveFile : AbstractSaveFile() {

    @JvmField
    var tutorialVersion: Int = 0

    @JvmField
    var kalguurQuantities: ArrayList<Int> = ArrayList()

    @JvmField
    var kalguurTimers: ArrayList<String> = ArrayList()

    override fun getCurrentTargetVersion(): Int {
        return 0
    }

}
