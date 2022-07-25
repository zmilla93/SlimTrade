package com.slimtrade.core.legacy;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.VersionNumber;
import com.slimtrade.modules.saving.SaveFile;

public class SaveFilePatcher {

    public static boolean checkPatchBeta3() {
        SaveFile<VersionSaveFile> versionSaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "settings.json", VersionSaveFile.class);
        versionSaveFile.loadFromDisk();
        VersionNumber saveFileVersion = new VersionNumber(versionSaveFile.data.versionNumber);
        VersionNumber minVersion = new VersionNumber("v0.3.0");
        VersionNumber targetVersion = new VersionNumber("v0.4.0");
        return saveFileVersion.compareTo(minVersion) >= 0
                && saveFileVersion.compareTo(targetVersion) < 0;
    }

    public static void applyPatchBeta3ToBeta4() {
        SaveFile<LegacySettingsSaveFile_0_3_5> legacySaveFile = new SaveFile<>(SaveManager.getSaveDirectory() + "settings.json", LegacySettingsSaveFile_0_3_5.class);
        legacySaveFile.loadFromDisk();
        System.out.println("client path:" + legacySaveFile.data.clientPath);
    }

}
