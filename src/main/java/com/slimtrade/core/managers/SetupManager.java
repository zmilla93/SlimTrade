package com.slimtrade.core.managers;

import com.slimtrade.App;

public class SetupManager {

    public volatile boolean setupRunning = false;

    public static boolean clientSetupCheck = false;
    public static boolean characterNameCheck = false;
    public static boolean stashOverlayCheck = false;

    public static boolean isSetupRequired() {
        boolean needsSetup = false;
        if(App.saveManager.saveFile.clientPath == null || App.saveManager.saveFile.clientPath.equals("")) {
            SetupManager.clientSetupCheck = true;
            needsSetup = true;
        }
        if(App.saveManager.saveFile.characterName == null || App.saveManager.saveFile.characterName.equals("")) {
            SetupManager.characterNameCheck = true;
            needsSetup = true;
        }
        if(!App.saveManager.stashSaveFile.initialized) {
            SetupManager.stashOverlayCheck = true;
            needsSetup = true;
        }
        return needsSetup;
    }

}
