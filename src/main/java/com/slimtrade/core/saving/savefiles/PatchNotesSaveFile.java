package com.slimtrade.core.saving.savefiles;

import com.slimtrade.core.data.PatchNotesEntry;
import com.slimtrade.modules.updater.data.AppVersion;

import java.util.ArrayList;

public class PatchNotesSaveFile extends AbstractSaveFile {

    public String versionString;
    public ArrayList<PatchNotesEntry> entries;
    private transient AppVersion appVersion;

    public AppVersion getAppVersion() {
        if (appVersion == null) appVersion = new AppVersion(versionString);
        return appVersion;
    }

    @Override
    public int getTargetFileVersion() {
        return 1;
    }

}
