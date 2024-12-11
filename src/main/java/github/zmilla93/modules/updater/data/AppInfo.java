package github.zmilla93.modules.updater.data;

public class AppInfo {

    public final String appName;
    public final String fullName;
    public final AppVersion appVersion;
    public final String url;

    public AppInfo(String appName, AppVersion appVersion, String url) {
        this.appName = appName;
        this.appVersion = appVersion;
        this.url = url;
        this.fullName = appName + " " + appVersion;
    }

}
