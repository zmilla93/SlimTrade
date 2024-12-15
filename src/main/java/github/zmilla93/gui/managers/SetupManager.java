package github.zmilla93.gui.managers;

import github.zmilla93.App;
import github.zmilla93.core.enums.SetupPhase;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.GameWindowMode;
import github.zmilla93.core.poe.PoeClientPath;
import github.zmilla93.gui.setup.SetupWindow;

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
        if (!SaveManager.settingsSaveFile.data.hasInitGameDirectories) {
            setupPhases.add(SetupPhase.INSTALL_DIRECTORY);
        } else {
            boolean poe1MarkedNotInstalled = SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled;
            boolean poe2MarkedNotInstalled = SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled;
            boolean poe1ValidPath = PoeClientPath.isValidInstallFolder(Game.PATH_OF_EXILE_1, SaveManager.settingsSaveFile.data.settingsPoe1.installFolder);
            boolean poe2ValidPath = PoeClientPath.isValidInstallFolder(Game.PATH_OF_EXILE_2, SaveManager.settingsSaveFile.data.settingsPoe2.installFolder);
            boolean poe1FullPathValidation = poe1MarkedNotInstalled || poe1ValidPath;
            boolean poe2FullPathValidation = poe2MarkedNotInstalled || poe2ValidPath;
            if (!poe1FullPathValidation || !poe2FullPathValidation)
                setupPhases.add(SetupPhase.INSTALL_DIRECTORY);
        }
        // Game Detection Method
        // FIXME : Make this more robust once done with screen region
        if (SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.UNSET)
            setupPhases.add(SetupPhase.GAME_WINDOW);
        // Using Stash Folders
        if (!SaveManager.settingsSaveFile.data.hasInitUsingStashFolders)
            setupPhases.add(SetupPhase.USING_STASH_FOLDERS);
        // POE2 Outgoing Trade Fix
        if (SaveManager.settingsSaveFile.data.poe2OutgoingTradeHotkey == null)
            if (SaveManager.settingsSaveFile.data.hasInitGameDirectories)
                if (!SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled)
                    setupPhases.add(SetupPhase.POE_2_OUTGOING_TRADE_FIX);
        // Add any forced setup phases
        if (!App.forcedSetupPhases.isEmpty()) {
            for (SetupPhase phase : App.forcedSetupPhases) {
                if (!setupPhases.contains(phase))
                    setupPhases.add(phase);
            }
        }
        // Finished
        return setupPhases;
    }

}
