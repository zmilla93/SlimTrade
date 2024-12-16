package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.gui.chatscanner.ChatScannerEntry;

public class PatcherScanner1to2 implements ISavePatcher {

    @Override
    public int getNewVersion() {
        return 2;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.chatScannerSaveFile.fileExists() && SaveManager.chatScannerSaveFile.data.saveFileVersion < getNewVersion();
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
        SaveManager.chatScannerSaveFile.data.saveFileVersion = getNewVersion();
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
