package com.slimtrade.core.utility;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.slimtrade.App;
import com.slimtrade.core.References;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

	// TODO : Switch to /releases/latest with new versionMatchString in order to
	// avoid detecting prereleases
    private VersionNumber currentVersion;
    private VersionNumber latestRelease;
    private VersionNumber latestPreRelease;
    private boolean allowPreReleases;

    //Github URLs
	private final String latestReleaseURL = "https://github.com/zmilla93/SlimTrade/releases/latest";
    private final String allReleases = "https://github.com/zmilla93/SlimTrade/releases";

    //Regex match strings
	private final String latestReleaseMatchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";
	private final String allReleasesMatchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";
	private static final String gitUrl = "https://api.github.com/repos/zmilla93/slimtrade/tags";

	// private ArrayList<String> versions = new ArrayList<String>();

	private boolean newVersion = false;
	private VersionNumber latestVersion;

	public UpdateChecker() {
        currentVersion = new VersionNumber(References.APP_VERSION);
        latestRelease = new VersionNumber(References.APP_VERSION);
        latestPreRelease = new VersionNumber(References.APP_VERSION);
	}

//	public VersionNumber getLatestVersion() {
//		return latestVersion;
//	}

	public boolean checkForUpdates(){
		return checkForUpdates(false);
	}

	public boolean checkForUpdates(boolean allowPreReleases) {

	    VersionNumber appVersion = new VersionNumber(References.APP_VERSION);
        currentVersion = appVersion;
        latestRelease = appVersion;
        latestPreRelease = appVersion;
        if(allowPreReleases | App.debugMode) {
            this.allowPreReleases = true;
        }
//        this.allowPreReleases = (allowPreReleases | App.debugMode);

        try {
            System.out.println("Checking for updates...");
            URL url = new URL(gitUrl);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            System.out.println(builder.toString());
            JsonArray obj = new Gson().fromJson(builder.toString(), JsonArray.class);
            System.out.println("HMM");
            for(int i = 0; i<obj.size(); i++) {
                // Returns all tag names
                JsonElement tag = obj.get(i).getAsJsonObject().get("name");
                System.out.println("tag: " + tag);
                VersionNumber v = new VersionNumber(tag.toString());
                System.out.println(v.toString());
                //TODO : CLEAN + PRE
                v.checkVersion();
//                if (VersionNumber.isNewVersion(v)) {
//                    newVersion = true;
//                    if (latestVersion != null) {
//                        if (VersionNumber.isNewVersion(v, latestVersion)) {
//                            latestVersion = v;
//                        }
//                    } else {
//                        latestVersion = v;
//                    }
//                }
            }
            br.close();
        } catch (IOException e) {
            return false;
//            e.printStackTrace();
        }
        System.out.println("Checked!");
        System.out.println("Current : " + getCurrentVersion());
        System.out.println("Ver : " + getLatestRelease());
        System.out.println("Pre : " + getLatestPreRelease());
        System.out.println("Allow Pre : " + this.isAllowPreReleases());
        System.out.println("isNewVer : " + this.isNewReleaseAvailable());
        System.out.println("isNewPre : " + this.isNewPreReleaseAvailable());

        if(this.isAllowPreReleases()) {
            return isNewReleaseAvailable();
        } else {
            return isNewPreReleaseAvailable();
        }
//        return newVersion;
    }

    public VersionNumber getCurrentVersion(){
        return currentVersion;
    }

    public VersionNumber getLatestRelease(){
        return latestRelease;
    }

    public VersionNumber getLatestPreRelease(){
        return latestPreRelease;
    }

    public boolean isNewReleaseAvailable() {
        if(VersionNumber.isNewVersion(currentVersion, latestRelease)) {
            return true;
        }
        return false;
    }

    public boolean isNewPreReleaseAvailable() {
        if(VersionNumber.isNewVersion(currentVersion, latestPreRelease)) {
            return true;
        }
        return false;
    }

    public boolean isAllowPreReleases(){
	    return this.allowPreReleases;
    }

    public void setLatestRelease(VersionNumber version) {
	    latestRelease = version;
    }
    public void setLatestPreRelease(VersionNumber version) {
	    latestPreRelease = version;
    }

    public boolean isUpdateAvailable() {
	    boolean update = (this.allowPreReleases && this.isNewPreReleaseAvailable()) ? this.isNewPreReleaseAvailable() : this.isNewReleaseAvailable();
	    return update;
    }

    public VersionNumber getNewestVersion() {
	    if(this.allowPreReleases && VersionNumber.isNewVersion(this.getLatestRelease(), this.getLatestPreRelease())){
	        return this.getLatestPreRelease();
        }
        return this.getLatestRelease();
    }

}
