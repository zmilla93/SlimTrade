package com.slimtrade.core.utility;

import com.slimtrade.gui.messaging.OverlayExamplePanel;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionNumber implements Comparable<VersionNumber> {

    public boolean valid;
    public final int major;
    public final int minor;
    public final int patch;
    public boolean prerelease;

    private static final String matchString = "v?(\\d+)\\.(\\d+)\\.(\\d+)(-pre\\d+)?";
    private static final Pattern pattern = Pattern.compile(matchString);

    public VersionNumber(String tag) {
        Matcher matcher = null;
        if(tag != null) {
            matcher = pattern.matcher(tag);
            valid = matcher.matches();
        }
        if (valid) {
            major = Integer.parseInt(matcher.group(1));
            minor = Integer.parseInt(matcher.group(2));
            patch = Integer.parseInt(matcher.group(3));
        } else {
            major = -1;
            minor = -1;
            patch = -1;
        }
    }

    @Override
    public int compareTo(@NotNull VersionNumber o) {
        if (o.major > major) return -1;
        else if (o.major < major) return 1;
        else if (o.minor > minor) return -1;
        else if (o.minor < minor) return 1;
        else if (o.patch > patch) return -1;
        else if (o.patch < patch) return 1;
        return 0;
    }
}
