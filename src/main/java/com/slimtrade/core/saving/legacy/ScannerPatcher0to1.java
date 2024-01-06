package com.slimtrade.core.saving.legacy;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.ISavePatcher;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.modules.saving.SaveFile;

import java.util.ArrayList;

public class ScannerPatcher0to1 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.chatScannerSaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyScannerSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "scanner.json", LegacyScannerSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) return false;

        ArrayList<ChatScannerEntry> entries = new ArrayList<>();
        for (LegacyScannerSave0.LegacyScannerMessage message : legacySaveFile.data.messages) {
            entries.add(message.toChatScannerEntry());
        }
        SaveManager.chatScannerSaveFile.data.scannerEntries = entries;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.chatScannerSaveFile.data.saveFileVersion = 1;
        SaveManager.chatScannerSaveFile.saveToDisk(false);
    }

}
