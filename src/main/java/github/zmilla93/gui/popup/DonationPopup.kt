package github.zmilla93.gui.popup

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.saving.savefiles.AppStateSaveFile
import github.zmilla93.gui.managers.FrameManager
import java.awt.Window

class DonationPopup() : AbstractLaunchPopup() {

    override fun window(): Window {
        return FrameManager.optionsWindow
    }

    override fun shouldPopupBeShown(): Boolean {
        return SaveManager.appStateSaveFile.data.donatePopupVersion != AppStateSaveFile.LEAGUE_POPUP_VERSION

    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.donatePopupVersion = AppStateSaveFile.LEAGUE_POPUP_VERSION
    }

    override fun showPopup() {
        if (SaveManager.isNewInstall) {
            FrameManager.optionsWindow.isVisible = true
            markAsSeen()
        } else FrameManager.optionsWindow.showDonationPanel()
    }

}