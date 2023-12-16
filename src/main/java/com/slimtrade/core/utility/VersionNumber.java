package com.slimtrade.core.utility;

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
        if (tag != null) {
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

    // FIXME : Move this to an actual test
    public static void runTest() {
        VersionNumber v1 = new VersionNumber("v0.3.5");
        VersionNumber v2 = new VersionNumber("v0.4.0");
        VersionNumber v3 = new VersionNumber("v0.4.5");
        VersionNumber target = new VersionNumber("v0.4.0");
        int i1 = v1.compareTo(target);
        int i2 = v2.compareTo(target);
        int i3 = v3.compareTo(target);
        System.out.println("patch?" + (i1));
        System.out.println("patch?" + (i2));
        System.out.println("patch?" + (i3));
    }

    @Override
    public int compareTo(VersionNumber o) {
        if (o.major > major) return -1;
        else if (o.major < major) return 1;
        else if (o.minor > minor) return -1;
        else if (o.minor < minor) return 1;
        else if (o.patch > patch) return -1;
        else if (o.patch < patch) return 1;
        return 0;
    }

}
