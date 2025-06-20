package github.zmilla93.core.saving.savefiles;

import github.zmilla93.modules.saving.AbstractSaveFile;
import github.zmilla93.modules.updater.PatchNotesEntry;
import github.zmilla93.modules.updater.data.AppVersion;

import java.util.ArrayList;

public class PatchNotesSaveFile extends AbstractSaveFile {

    public String versionString = "";
    public ArrayList<PatchNotesEntry> entries = new ArrayList<>();
    private transient AppVersion appVersion = null;

    public AppVersion getAppVersion() {
        if (appVersion == null) appVersion = new AppVersion(versionString);
        return appVersion;
    }

    @Override
    public int getCurrentTargetVersion() {
        return 0;
    }

}
