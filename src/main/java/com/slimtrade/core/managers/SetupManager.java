package com.slimtrade.core.managers;

import com.slimtrade.App;

import java.io.File;

public class SetupManager {

    public static boolean clientSetupCheck = false;
    public static boolean characterNameCheck = false;
    public static boolean stashOverlayCheck = false;

    public static boolean isSetupRequired() {
        boolean needsSetup = false;
        int count = App.saveManager.validateClientPath();
        if (count != 1) {
            SetupManager.clientSetupCheck = true;
            needsSetup = true;
        } else {
            File file = new File(App.saveManager.settingsSaveFile.clientPath);
            if (!file.exists() || !file.isFile()) {
                SetupManager.clientSetupCheck = true;
                needsSetup = true;
            }
        }
        if (App.saveManager.settingsSaveFile.characterName == null || App.saveManager.settingsSaveFile.characterName.equals("")) {
            SetupManager.characterNameCheck = true;
            needsSetup = true;
        }
        if (!App.saveManager.stashSaveFile.initialized) {
            SetupManager.stashOverlayCheck = true;
            needsSetup = true;
        }
        return needsSetup;
    }

}
