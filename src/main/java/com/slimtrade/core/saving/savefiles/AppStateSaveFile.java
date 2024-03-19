package com.slimtrade.core.saving.savefiles;

/**
 * Used to track info not controlled by the user.
 * Will likely be expanded in the future to allow certain states to be saved
 * when the app is closed, like open trades, windows, scanner, etc.
 */
public class AppStateSaveFile extends AbstractSaveFile {

    public int tutorialVersion;

    @Override
    public int getTargetFileVersion() {
        return 1;
    }

}
