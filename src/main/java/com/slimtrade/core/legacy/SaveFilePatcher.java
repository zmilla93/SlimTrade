package com.slimtrade.core.legacy;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.modules.saving.SaveFile;
import com.slimtrade.modules.updater.data.AppVersion;

public class SaveFilePatcher {

    public static boolean checkPatchBeta3() {
        SaveFile<VersionSaveFile> versionSaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "settings.json", VersionSaveFile.class);
        versionSaveFile.loadFromDisk();
        AppVersion saveFileVersion = new AppVersion(versionSaveFile.data.versionNumber);
        AppVersion minVersion = new AppVersion("v0.3.0");
        AppVersion targetVersion = new AppVersion("v0.4.0");
        return saveFileVersion.compareTo(minVersion) >= 0
                && saveFileVersion.compareTo(targetVersion) < 0;
    }

    public static void applyPatchBeta3ToBeta4() {
        SaveFile<LegacySettingsSaveFile_0_3_5> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "settings.json", LegacySettingsSaveFile_0_3_5.class);
        legacySaveFile.loadFromDisk();
        System.out.println("client path:" + legacySaveFile.data.clientPath);
    }

}
