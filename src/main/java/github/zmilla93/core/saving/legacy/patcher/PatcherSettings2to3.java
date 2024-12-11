package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacySettingsSave2;
import github.zmilla93.modules.saving.SaveFile;

public class PatcherSettings2to3 implements ISavePatcher {

    private String errorMessage;

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < 3;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacySettingsSave2> legacySave = new SaveFile<>(SaveManager.settingsSaveFile.path, LegacySettingsSave2.class);
        legacySave.loadFromDisk();
        if (!legacySave.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        SaveManager.settingsSaveFile.data.kingsmarchHotkey = legacySave.data.necropolisHotkey;
        return true;
    }

    @Override
    public void applyNewVersion() {
        SaveManager.settingsSaveFile.data.saveFileVersion = 3;
        SaveManager.settingsSaveFile.saveToDisk(false);
    }

    @Override
    public void handleCorruptedFile() {
        SaveManager.settingsSaveFile.initData();
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

}
