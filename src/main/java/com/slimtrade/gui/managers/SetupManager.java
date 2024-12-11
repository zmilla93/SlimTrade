package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.enums.SetupPhase;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.Game;
import com.slimtrade.core.poe.GameDetectionMethod;
import com.slimtrade.core.utility.TradeUtil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Generates a list of {@link SetupPhase}s, which are used by the {@link com.slimtrade.gui.setup.SetupWindow} to run a setup prompt.
 */
public class SetupManager {

    private static ArrayList<SetupPhase> setupPhases;

    public static ArrayList<SetupPhase> getSetupPhases() {
        // Return cached list if possible
        if (setupPhases != null) return setupPhases;
        setupPhases = new ArrayList<>();
        // Debug - Force all setup phases
        if (App.forceSetup) {
            setupPhases.addAll(Arrays.asList(SetupPhase.values()));
            return setupPhases;
        }
        /// Start of setup phase checks
        // Path of Exile Folders
        boolean hasInitializedGamePaths = SaveManager.settingsSaveFile.data.hasInitializedGameDirectories;
        if (hasInitializedGamePaths) {
            boolean poe1MarkedNotInstalled = SaveManager.settingsSaveFile.data.poe1NotInstalled;
            boolean poe2MarkedNotInstalled = SaveManager.settingsSaveFile.data.poe2NotInstalled;
            boolean poe1ValidPath = false;
            boolean poe2ValidPath = false;
            String poe1PathString = SaveManager.settingsSaveFile.data.installFolderPoe1;
            String poe2PathString = SaveManager.settingsSaveFile.data.installFolderPoe2;
            if (poe1PathString != null)
                poe1ValidPath = TradeUtil.isValidPOEFolder(Paths.get(poe1PathString), Game.PATH_OF_EXILE_1);
            if (poe2PathString != null)
                poe2ValidPath = TradeUtil.isValidPOEFolder(Paths.get(poe2PathString), Game.PATH_OF_EXILE_2);
            boolean poe1FullPathValidation = poe1ValidPath || poe1MarkedNotInstalled;
            boolean poe2FullPathValidation = poe2ValidPath || poe2MarkedNotInstalled;
            if (!poe1FullPathValidation || !poe2FullPathValidation)
                setupPhases.add(SetupPhase.GAME_INSTALL_DIRECTORY);
        } else {
            setupPhases.add(SetupPhase.GAME_INSTALL_DIRECTORY);
        }
        // Game Detection Method
        // FIXME : Make this more robust
        if (SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.UNSET)
            setupPhases.add(SetupPhase.GAME_DETECTION_METHOD);
        // Stash Folders
        if (!SaveManager.settingsSaveFile.data.hasInitializedUsingStashFolders)
            setupPhases.add(SetupPhase.STASH_FOLDERS);
        // Finished
        return setupPhases;
    }

}
