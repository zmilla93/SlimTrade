package com.slimtrade.gui.managers;

import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;

import java.io.File;
import java.util.ArrayList;

public class SetupManager {

    private static ArrayList<SetupPhase> setupPhases;

    public static ArrayList<SetupPhase> getSetupPhases() {
        if (setupPhases != null) return setupPhases;
        setupPhases = new ArrayList<>();

        // Client File
        if (SaveManager.settingsSaveFile.data.clientPath == null) {
            setupPhases.add(SetupPhase.CLIENT_PATH);
        } else {
            File file = new File(SaveManager.settingsSaveFile.data.clientPath);
            if (!file.exists() || !file.isFile()) {
                setupPhases.add(SetupPhase.CLIENT_PATH);
            }
        }
        // Character Name
        if (SaveManager.settingsSaveFile.data.characterName == null || SaveManager.settingsSaveFile.data.characterName.equals(""))
            setupPhases.add(SetupPhase.CHARACTER_NAME);
        // Stash location
        if (SaveManager.stashSaveFile.data.gridRect == null)
            setupPhases.add(SetupPhase.STASH_POSITION);
        // Stash Folders
        if (!SaveManager.settingsSaveFile.data.initializedFolderOffset)
            setupPhases.add(SetupPhase.STASH_FOLDERS);

        return setupPhases;
    }

}
