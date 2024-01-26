package com.slimtrade.core.saving.legacy.patcher;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.legacy.ISavePatcher;
import com.slimtrade.core.saving.legacy.savefiles.LegacyScannerSave0;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;
import com.slimtrade.modules.saving.SaveFile;

import java.util.ArrayList;

public class PatcherScanner0to1 implements ISavePatcher {

    private String errorMessage;

    @Override
    public boolean requiresPatch() {
        return SaveManager.chatScannerSaveFile.fileExists() && SaveManager.chatScannerSaveFile.data.saveFileVersion < 1;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyScannerSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "scanner.json", LegacyScannerSave0.class);
        legacySaveFile.loadFromDisk();
        if (!legacySaveFile.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }

        ArrayList<ChatScannerEntry> entries = new ArrayList<>();
        for (LegacyScannerSave0.LegacyScannerMessage message : legacySaveFile.data.messages) {
            entries.add(message.toChatScannerEntry());
        }
        SaveManager.chatScannerSaveFile.data.scannerEntries = entries;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.chatScannerSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.chatScannerSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
