package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacyOverlaySave0;
import github.zmilla93.core.saving.savefiles.OverlaySaveFile;
import github.zmilla93.modules.saving.SaveFile;

import java.awt.*;

public class PatcherOverlay0to1 implements ISavePatcher {

    private String errorMessage;

    @Override
    public boolean requiresPatch() {
        return SaveManager.overlaySaveFile.fileExists() && SaveManager.overlaySaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyOverlaySave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory().resolve("overlay.json"), LegacyOverlaySave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        LegacyOverlaySave0 legacyData = legacySaveFile.data;
        OverlaySaveFile data = SaveManager.overlaySaveFile.data;

        // Messages
        ExpandDirection legacyExpandDirection = legacyData.messageExpandDirection.expandDirection;
        data.messageExpandDirection = legacyData.messageExpandDirection.expandDirection;
        if (legacyData.messageExpandDirection == LegacyOverlaySave0.LegacyExpandDirection.DOWN) {
            data.messageLocation = new Point(legacyData.messageX, legacyData.messageY);
        } else if (legacyData.messageExpandDirection == LegacyOverlaySave0.LegacyExpandDirection.UP) {
            int messageHeightIncrease = LegacyOverlaySave0.DEFAULT_MESSAGE_SIZE.height + LegacyOverlaySave0.TOTAL_BORDER_SIZE + legacyData.messageSizeIncrease;
            data.messageLocation = new Point(legacyData.messageX, legacyData.messageY + messageHeightIncrease);
        }

        // Menubar
        Point menubarLocation = new Point(legacyData.menubarX, legacyData.menubarY);
        if (legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.NE
                || legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SE)
            menubarLocation.x += legacyData.menubarWidth;
        if (legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SW
                || legacyData.menubarButtonLocation == LegacyOverlaySave0.LegacyMenubarButtonLocation.SE)
            menubarLocation.y += legacyData.menubarHeight;
        data.menubarLocation = menubarLocation;
        data.menubarAnchor = legacyData.menubarButtonLocation.anchor;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.overlaySaveFile.data.saveFileVersion = 1;
        SaveManager.overlaySaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.overlaySaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
