package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;

public class PatcherStash1to2 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.stashSaveFile.fileExists() && SaveManager.stashSaveFile.data.saveFileVersion < 2;
    }

    @Override
    public boolean patch() {
        if (SaveManager.stashSaveFile.data.gridRect.x == 0 && SaveManager.stashSaveFile.data.gridRect.y == 0)
            SaveManager.stashSaveFile.data.gridRect = null;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.stashSaveFile.data.saveFileVersion = 2;
        SaveManager.stashSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.stashSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

}
