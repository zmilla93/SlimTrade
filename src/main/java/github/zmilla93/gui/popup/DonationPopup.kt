package github.zmilla93.gui.popup

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.saving.savefiles.AppStateSaveFile
import github.zmilla93.gui.managers.FrameManager

class DonationPopup : AbstractLaunchPopup() {

    override fun shouldPopupBeShown(): Boolean {
        return SaveManager.appStateSaveFile.data.donatePopupVersion != AppStateSaveFile.LEAGUE_POPUP_VERSION
    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.donatePopupVersion = AppStateSaveFile.LEAGUE_POPUP_VERSION
    }

    override fun showPopup() {
        FrameManager.optionsWindow.showDonationPanel()
    }

}