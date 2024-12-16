package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacyStashSave0;
import github.zmilla93.modules.saving.SaveFile;

import java.awt.*;

public class PatcherStash0to1 implements ISavePatcher {

    private String errorMessage;

    @Override
    public int getNewVersion() {
        return 1;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.stashSaveFile.fileExists() && SaveManager.stashSaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyStashSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory().resolve("stash.json"), LegacyStashSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        LegacyStashSave0 legacyData = legacySaveFile.data;
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(legacyData.gridX, legacyData.gridY, legacyData.gridWidth, legacyData.gridHeight);
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.stashSaveFile.data.saveFileVersion = getNewVersion();
        SaveManager.stashSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.stashSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
