package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.utility.MacroButton;

public class PatcherSettings1to2 implements ISavePatcher {

    @Override
    public int getNewVersion() {
        return 2;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        for (MacroButton macro : SaveManager.settingsSaveFile.data.incomingMacroButtons) {
            macro.lmbResponse = macro.lmbResponse.replaceAll("/kick \\{self}", "/leave");
            macro.rmbResponse = macro.rmbResponse.replaceAll("/kick \\{self}", "/leave");
        }
        for (MacroButton macro : SaveManager.settingsSaveFile.data.outgoingMacroButtons) {
            macro.lmbResponse = macro.lmbResponse.replaceAll("/kick \\{self}", "/leave");
            macro.rmbResponse = macro.rmbResponse.replaceAll("/kick \\{self}", "/leave");
        }
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.settingsSaveFile.data.saveFileVersion = getNewVersion();
        SaveManager.settingsSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.settingsSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

}
