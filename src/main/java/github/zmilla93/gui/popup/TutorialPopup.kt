package github.zmilla93.gui.popup

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.saving.savefiles.AppStateSaveFile
import github.zmilla93.gui.managers.FrameManager
import java.awt.Window

class TutorialPopup : AbstractLaunchPopup() {

    override fun window(): Window {
        return FrameManager.tutorialWindow
    }

    override fun shouldPopupBeShown(): Boolean {
        return SaveManager.appStateSaveFile.data.tutorialVersion != AppStateSaveFile.TUTORIAL_VERSION
    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.tutorialVersion = AppStateSaveFile.TUTORIAL_VERSION
    }

    override fun showPopup() {
        FrameManager.tutorialWindow.isVisible = true
    }

}