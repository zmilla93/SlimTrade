package github.zmilla93.gui.popup

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.saving.savefiles.AppStateSaveFile
import github.zmilla93.gui.managers.FrameManager
import java.awt.Window

class RoadmapPopup : AbstractLaunchPopup() {

    override fun window(): Window {
        return FrameManager.roadMapWindow
    }

    override fun shouldPopupBeShown(): Boolean {
        return SaveManager.appStateSaveFile.data.roadmapPopupVersion != AppStateSaveFile.LEAGUE_POPUP_VERSION
    }

    override fun markAsSeen() {
        SaveManager.appStateSaveFile.data.roadmapPopupVersion = AppStateSaveFile.LEAGUE_POPUP_VERSION
    }

    override fun showPopup() {
        FrameManager.roadMapWindow.isVisible = true
    }

}