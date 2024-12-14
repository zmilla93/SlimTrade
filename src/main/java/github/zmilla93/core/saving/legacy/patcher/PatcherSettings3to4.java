package github.zmilla93.core.saving.legacy.patcher;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.saving.legacy.ISavePatcher;
import github.zmilla93.core.saving.legacy.savefiles.LegacySettingsSave3;
import github.zmilla93.modules.saving.SaveFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PatcherSettings3to4 implements ISavePatcher {

    private String errorMessage;

    @Override
    public boolean requiresPatch() {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < 4;
    }

    @Override
    public boolean patch() {
        SaveFile<LegacySettingsSave3> legacySave = new SaveFile<>(SaveManager.settingsSaveFile.path, LegacySettingsSave3.class);
        legacySave.loadFromDisk();
        if (!legacySave.loadedExistingData()) {
            errorMessage = FAILED_TO_LOAD;
            return false;
        }
        /// Import the old POE 1 client.txt file path, converting it to the new path format & validating it along the way.
        if (legacySave.data.clientPath == null) return true;
        Path clientPath = Paths.get(legacySave.data.clientPath);
        if (!clientPath.endsWith(SaveManager.POE_CLIENT_TXT_NAME)) return true;
        if (!clientPath.toFile().exists()) return true;
        Path logsFolder = clientPath.getParent();
        if (!logsFolder.endsWith(SaveManager.POE_LOG_FOLDER_NAME)) return true;
        Path poe1Folder = logsFolder.getParent();
        if (!poe1Folder.endsWith(Game.PATH_OF_EXILE_1.toString())) return true;
        SaveManager.settingsSaveFile.data.installFolderPoe1 = poe1Folder.toString();
        /// Intentionally not importing usingStashFolder setting to make sure people reset it correctly.
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
