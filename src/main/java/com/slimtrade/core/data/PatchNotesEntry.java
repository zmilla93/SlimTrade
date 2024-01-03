package com.slimtrade.core.data;

import com.slimtrade.modules.updater.data.AppVersion;

public class PatchNotesEntry {

    public String versionString;
    public String text;
    private transient AppVersion appVersion;

    public PatchNotesEntry(String versionString, String text) {
        this.versionString = versionString;
        this.text = text;
    }

    public AppVersion getAppVersion() {
        if (appVersion == null) appVersion = new AppVersion(versionString);
        return appVersion;
    }

    @Override
    public String toString() {
        return getAppVersion().toString();
    }

}
