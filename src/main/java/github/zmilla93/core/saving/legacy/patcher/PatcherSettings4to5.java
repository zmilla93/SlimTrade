package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacySettingsSave4;
import github.zmilla93.modules.saving.SaveFile;

public class PatcherSettings4to5 implements ISavePatcher {

    private String errorMessage;

    @Override
    public int getNewVersion() {
        return 5;
    }

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < getNewVersion();
    }

    @Override
    public boolean patch() {
        SaveFile<LegacySettingsSave4> legacySave = new SaveFile<>(SaveManager.settingsSaveFile.path, LegacySettingsSave4.class);
        legacySave.loadFromDisk();
        if (!legacySave.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        SaveManager.settingsSaveFile.data.hasInitGameDirectories = legacySave.data.initGameDirectories == 1;
        SaveManager.settingsSaveFile.data.hasInitUsingStashFolders = legacySave.data.initUsingStashFolders == 1;
        SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled = legacySave.data.notInstalledPoe1;
        SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled = legacySave.data.notInstalledPoe2;
        SaveManager.settingsSaveFile.data.settingsPoe1.installFolder = legacySave.data.installFolderPoe1;
        SaveManager.settingsSaveFile.data.settingsPoe2.installFolder = legacySave.data.installFolderPoe2;
        SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder = legacySave.data.usingStashFoldersPoe1;
        SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder = legacySave.data.usingStashFoldersPoe2;
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
        return errorMessage;
    }

}
