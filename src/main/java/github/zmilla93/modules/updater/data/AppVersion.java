package github.zmilla93.modules.updater.data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a version of the current program.
 */
public class AppVersion implements Comparable<AppVersion> {

    // Public info
    public final boolean valid;
    public final boolean isPreRelease;

    // Internal
    private String string;
    private final int major;
    private final int minor;
    private final int patch;
    private final int pre;

    // Matching
    private static final String matchString = "v?(?<major>\\d+)\\.(?<minor>\\d+)\\.(?<patch>\\d+)(-pre(?<pre>\\d+))?";
    private static final Pattern pattern = Pattern.compile(matchString);

    public AppVersion(String tag) {
        Matcher matcher = null;
        if (tag != null) {
            matcher = pattern.matcher(tag);
            valid = matcher.matches();
        } else {
            valid = false;
        }
        if (valid) {
            major = Integer.parseInt(matcher.group("major"));
            minor = Integer.parseInt(matcher.group("minor"));
            patch = Integer.parseInt(matcher.group("patch"));
            String preString = matcher.group("pre");
            if (preString != null) {
                isPreRelease = true;
                pre = Integer.parseInt(matcher.group(5));
            } else {
                pre = -1;
                isPreRelease = false;
            }
        } else {
            major = -1;
            minor = -1;
            patch = -1;
            pre = -1;
            isPreRelease = false;
        }
        string = major + "." + minor + "." + patch;
        if (isPreRelease) string += "-pre" + pre;
    }

    @Override
    public int compareTo(AppVersion other) {
        if (other.major > major) return -1;
        else if (other.major < major) return 1;
        else if (other.minor > minor) return -1;
        else if (other.minor < minor) return 1;
        else if (other.patch > patch) return -1;
        else if (other.patch < patch) return 1;
        if (isPreRelease && !other.isPreRelease) return -1;
        if (!isPreRelease && other.isPreRelease) return 1;
        if (isPreRelease) {
            if (other.pre > pre) return -1;
            if (other.pre < pre) return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AppVersion)) return false;
        AppVersion other = (AppVersion) obj;
        return compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return string;
    }

}
