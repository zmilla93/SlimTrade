package com.slimtrade.core.legacy;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.modules.saving.SaveFile;

import java.awt.*;

public class StashSavePatcher0to1 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.stashSaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        if (!requiresPatch()) return false;
        SaveFile<LegacyStashSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "stash.json", LegacyStashSave0.class);
        legacySaveFile.loadFromDisk();
        LegacyStashSave0 data = legacySaveFile.data;
        if (!legacySaveFile.loadedExistingData()) return false;
        SaveManager.stashSaveFile.data.gridRect = new Rectangle(data.gridX, data.gridY, data.gridWidth, data.gridHeight);
        SaveManager.stashSaveFile.data.saveFileVersion = 1;
        SaveManager.stashSaveFile.saveToDisk(false);
        return true;
    }

}
