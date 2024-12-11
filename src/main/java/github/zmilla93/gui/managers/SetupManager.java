package github.zmilla93.gui.managers;

import github.zmilla93.App;
import github.zmilla93.core.enums.SetupPhase;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.GameDetectionMethod;
import github.zmilla93.core.saving.savefiles.SettingsSaveFile;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.gui.setup.SetupWindow;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Generates a list of {@link SetupPhase}s, which are used by the {@link SetupWindow} to run a setup prompt.
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
        // Game Directories
        boolean initializedGamePaths = SaveManager.settingsSaveFile.data.initGameDirectories == SettingsSaveFile.targetInitGameDirectories;
        if (initializedGamePaths) {
            boolean poe1MarkedNotInstalled = SaveManager.settingsSaveFile.data.notInstalledPoe1;
            boolean poe2MarkedNotInstalled = SaveManager.settingsSaveFile.data.notInstalledPoe2;
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
        // FIXME : Make this more robust once done with screen region
        if (SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.UNSET)
            setupPhases.add(SetupPhase.GAME_DETECTION_METHOD);
        // Using Stash Folders
        boolean hasInitFolders = SaveManager.settingsSaveFile.data.initUsingStashFolders == SettingsSaveFile.targetInitUsingStashFolders;
        if (!hasInitFolders) setupPhases.add(SetupPhase.STASH_FOLDERS);
        // Finished
        return setupPhases;
    }

}
