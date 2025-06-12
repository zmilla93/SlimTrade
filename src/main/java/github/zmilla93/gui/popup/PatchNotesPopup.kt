package github.zmilla93.gui.popup

import github.zmilla93.App
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.updater.data.AppVersion

class PatchNotesPopup : AbstractLaunchPopup() {

    val curAppVersion: AppVersion = App.getAppInfo().appVersion

    override fun shouldPopupBeShown(): Boolean {
        return SaveManager.appStateSaveFile.data.patchNotesPopupVersion != curAppVersion
    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.patchNotesPopupVersion = curAppVersion
    }

    override fun showPopup() {
        FrameManager.patchNotesWindow.isVisible = true
    }

}