package github.zmilla93.gui.popup

import github.zmilla93.App
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.updater.data.AppVersion
import java.awt.Window

class PatchNotesPopup : AbstractLaunchPopup() {

    val curAppVersion: AppVersion = App.getAppInfo().appVersion

    override fun window(): Window {
        return FrameManager.patchNotesWindow
    }

    override fun shouldPopupBeShown(): Boolean {
        if (App.showPatchNotesOnLaunch && !hasShownThisLaunch) return true
        return SaveManager.appStateSaveFile.data.patchNotesPopupVersion != curAppVersion
    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.patchNotesPopupVersion = curAppVersion
    }

    override fun showPopup() {
        FrameManager.patchNotesWindow.isVisible = true
    }

}