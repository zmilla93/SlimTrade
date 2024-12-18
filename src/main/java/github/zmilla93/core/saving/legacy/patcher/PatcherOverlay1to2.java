package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.savefiles.OverlaySaveFile;

import java.awt.*;

public class PatcherOverlay1to2 implements ISavePatcher {

    private String errorMessage;

    @Override
    public int getNewVersion() {
        return 2;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.overlaySaveFile.fileExists() && SaveManager.overlaySaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        Point messageLocation = SaveManager.overlaySaveFile.data.messageLocation;
        Point menubarLocation = SaveManager.overlaySaveFile.data.menubarLocation;
        boolean messageCheck = !messageLocation.equals(OverlaySaveFile.DEFAULT_MESSAGE_LOCATION);
        boolean menubarCheck = !menubarLocation.equals(OverlaySaveFile.DEFAULT_MENUBAR_LOCATION);
        if (messageCheck || menubarCheck) SaveManager.overlaySaveFile.data.hasInitLocations = true;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.overlaySaveFile.data.saveFileVersion = getNewVersion();
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
