package github.zmilla93.modules.updater.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Represents a GitHub release.
 */
public class ReleaseVersion implements Comparable<ReleaseVersion> {

    public final AppVersion appVersion;
    public final String tag;
    public final String fileName;
    public final String downloadURL;
    public final String body;
    public final boolean preRelease;

    public ReleaseVersion(String tag, String fileName, String url, String body, boolean preRelease) {
        this.tag = tag;
        this.appVersion = new AppVersion(tag);
        this.fileName = fileName;
        this.downloadURL = url;
        this.body = body;
        this.preRelease = preRelease;
    }

    public ReleaseVersion(JsonElement json) {
        this(json.getAsJsonObject());
    }

    public ReleaseVersion(JsonObject json) {
        tag = json.get("tag_name").getAsString();
        appVersion = new AppVersion(tag);
        JsonObject asset = json.getAsJsonArray("assets").get(0).getAsJsonObject();
        fileName = asset.get("name").getAsString();
        downloadURL = asset.get("browser_download_url").getAsString();
        body = json.get("body").getAsString();
        preRelease = json.get("prerelease").getAsBoolean();
    }

    @Override
    public int compareTo(ReleaseVersion other) {
        return appVersion.compareTo(other.appVersion);
    }

    @Override
    public String toString() {
        return appVersion.toString();
    }

}