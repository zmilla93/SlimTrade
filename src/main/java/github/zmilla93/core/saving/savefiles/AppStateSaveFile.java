package github.zmilla93.core.saving.savefiles;

import java.util.ArrayList;

/**
 * Used to track info not controlled by the user.
 * Will likely be expanded in the future to allow certain states to be saved
 * when the app is closed, like open trades, windows, scanner, etc.
 */
public class AppStateSaveFile extends AbstractSaveFile {

    public int tutorialVersion;
    public ArrayList<Integer> kalguurQuantities = new ArrayList<>();
    public ArrayList<String> kalguurTimers = new ArrayList<>();

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
