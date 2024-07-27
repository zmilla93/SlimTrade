package com.slimtrade.core.saving.legacy.patcher;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.legacy.ISavePatcher;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.gui.chatscanner.ChatScannerEntry;

public class PatcherScanner1to2 implements ISavePatcher {

    @Override
    public boolean requiresPatch() {
        return SaveManager.chatScannerSaveFile.fileExists() && SaveManager.chatScannerSaveFile.data.saveFileVersion < 2;
    }

    @Override
    public boolean patch() {
        for (ChatScannerEntry entry : SaveManager.chatScannerSaveFile.data.scannerEntries) {
            for (MacroButton macro : entry.macros) {
                macro.lmbResponse = macro.lmbResponse.replaceAll("/kick \\{self}", "/leave");
                macro.rmbResponse = macro.rmbResponse.replaceAll("/kick \\{self}", "/leave");
            }
        }
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.chatScannerSaveFile.data.saveFileVersion = 2;
        SaveManager.chatScannerSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.chatScannerSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

}
