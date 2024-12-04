package com.slimtrade.core.utility;

/**
 * Enum representation of the 3 major OS platforms.
 * Platform.current returns the current OS.
 */
public enum Platform {

    UNKNOWN, WINDOWS, MAC, LINUX;

    public static final Platform current;

    static {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) current = WINDOWS;
        else if (os.startsWith("Mac")) current = MAC;
        else if (os.startsWith("Linux")) current = LINUX;
        else current = UNKNOWN;
    }

}
