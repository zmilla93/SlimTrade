package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.GameDetectionMethod;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Generates a list of {@link SetupPhase}s, which are used by the {@link com.slimtrade.gui.setup.SetupWindow} to run a setup prompt.
 */
public class SetupManager {

    private static ArrayList<SetupPhase> setupPhases;

    public static ArrayList<SetupPhase> getSetupPhases() {
        if (setupPhases != null) return setupPhases;
        setupPhases = new ArrayList<>();

        if (App.forceSetup) {
            setupPhases.addAll(Arrays.asList(SetupPhase.values()));
            return setupPhases;
        }

        // Path of Exile Folders
        boolean hasInitializedGamePaths = SaveManager.settingsSaveFile.data.hasInitializedGamePaths;
        boolean poe1MarkedNotInstalled = SaveManager.settingsSaveFile.data.poe1NotInstalled && hasInitializedGamePaths;
        boolean poe2MarkedNotInstalled = SaveManager.settingsSaveFile.data.poe2NotInstalled && hasInitializedGamePaths;
        Path poe1Path = Paths.get(SaveManager.settingsSaveFile.data.poe1InstallDirectory);
        Path poe2Path = Paths.get(SaveManager.settingsSaveFile.data.poe2InstallDirectory);
        boolean poe1ValidPath = poe1Path != null && poe1Path.toFile().exists();
        boolean poe2ValidPath = poe2Path != null && poe2Path.toFile().exists();
        boolean poe1FullPathValidation = poe1ValidPath || poe1MarkedNotInstalled;
        boolean poe2FullPathValidation = poe2ValidPath || poe2MarkedNotInstalled;
        if (hasInitializedGamePaths) {
            if (!poe1FullPathValidation || !poe2FullPathValidation)
                setupPhases.add(SetupPhase.GAME_INSTALL_DIRECTORY);
        } else {
            setupPhases.add(SetupPhase.GAME_INSTALL_DIRECTORY);
        }
//        if(!SaveManager.settingsSaveFile.data.hasInitializedGamePaths || !poe1ValidPath || !poe2ValidPath);
//        if (SaveManager.settingsSaveFile.data.clientPath == null) {
//            setupPhases.add(SetupPhase.CLIENT_PATH);
//        } else {
//            File file = new File(SaveManager.settingsSaveFile.data.clientPath);
//            if (!file.exists() || !file.isFile()) {
//                setupPhases.add(SetupPhase.CLIENT_PATH);
//            }
//        }
        // Game Detection Method
        if (SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.UNSET)
            setupPhases.add(SetupPhase.GAME_DETECTION_METHOD);
        // Stash location
        if (SaveManager.stashSaveFile.data.gridRect == null)
            setupPhases.add(SetupPhase.STASH_POSITION);
        // Stash Folders
        if (!SaveManager.settingsSaveFile.data.initializedFolderOffset)
            setupPhases.add(SetupPhase.STASH_FOLDERS);

        return setupPhases;
    }

}
