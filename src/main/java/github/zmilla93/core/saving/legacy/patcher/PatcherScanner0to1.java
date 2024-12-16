package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacyScannerSave0;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;
import github.zmilla93.modules.saving.SaveFile;

import java.util.ArrayList;

public class PatcherScanner0to1 implements ISavePatcher {

    private String errorMessage;

    @Override
    public int getNewVersion() {
        return 1;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.chatScannerSaveFile.fileExists() && SaveManager.chatScannerSaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        SaveFile<LegacyScannerSave0> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory().resolve("scanner.json"), LegacyScannerSave0.class);
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
        SaveManager.chatScannerSaveFile.data.saveFileVersion = getNewVersion();
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
