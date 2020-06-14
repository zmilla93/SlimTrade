package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.slimtrade.App;
import com.slimtrade.core.References;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private VersionNumber currentVersion;
    private VersionNumber latestVersion;
//    private VersionNumber latestRelease;
//    private VersionNumber latestPreRelease;
//    private boolean allowPreReleases = false;

    private static final String gitUrl = "https://api.github.com/repos/zmilla93/slimtrade/tags";

    private boolean updateAvailable = false;
//	private VersionNumber latestVersion;

    public UpdateChecker() {
        currentVersion = new VersionNumber(References.getAppVersion());
        latestVersion = new VersionNumber(References.getAppVersion());
    }

    public void checkForUpdates() {
        checkForUpdates(false);
    }

    public void checkForUpdates(boolean allowPreReleases) {
        VersionNumber appVersion = new VersionNumber(References.getAppVersion());
        currentVersion = appVersion;
        latestVersion = appVersion;
        if (App.allowPrerelease) {
            allowPreReleases = true;
        }
        try {
            // Get list of tags from GitHub
            System.out.println("Getting version list from github...");
            URL url = new URL(gitUrl);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            // Turn each tag into a version number, then check if is the latest version.
            JsonArray obj = null;
            try {
                obj = new Gson().fromJson(builder.toString(), JsonArray.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return;
            }
            for (int i = 0; i < obj.size(); i++) {
                JsonElement tag = obj.get(i).getAsJsonObject().get("name");
                VersionNumber v = new VersionNumber(tag.toString());
                if (VersionNumber.isNewVersion(latestVersion, v)) {
                    if (v.pre && !allowPreReleases) {
                        continue;
                    }
                    latestVersion = v;
                    updateAvailable = true;
                }
            }
            br.close();
        } catch (IOException e) {
            return;
        }
        System.out.println("Running... \t\t\t\t" + getCurrentVersion());
        System.out.println("Latest Release... \t\t" + getLatestRelease());
        return;
    }

    public VersionNumber getCurrentVersion() {
        return currentVersion;
    }

    public VersionNumber getLatestRelease() {
        return latestVersion;
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

}