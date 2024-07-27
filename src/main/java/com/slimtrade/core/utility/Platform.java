package com.slimtrade.core.utility;

public enum Platform {

    WINDOWS, MAC, LINUX;

    public static Platform current;

    static {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) current = WINDOWS;
        else if (os.startsWith("Mac")) current = MAC;
        else if (os.startsWith("Linux")) current = LINUX;
    }

}
