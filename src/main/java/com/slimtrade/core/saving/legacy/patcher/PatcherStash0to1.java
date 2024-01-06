package com.slimtrade.core.saving.legacy.patcher;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.ISavePatcher;
import com.slimtrade.core.saving.legacy.savefiles.LegacyStashSave0;
import com.slimtrade.modules.saving.SaveFile;

import java.awt.*;

public class PatcherStash0to1 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.stashSaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        if (!requiresPatch()) return false;
        SaveFile<LegacyStashSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "stash.json", LegacyStashSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) return false;
        LegacyStashSave0 data = legacySaveFile.data;
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(data.gridX, data.gridY, data.gridWidth, data.gridHeight);
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.stashSaveFile.data.saveFileVersion = 1;
        SaveManager.stashSaveFile.saveToDisk(false);
    }

}
