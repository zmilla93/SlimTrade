package github.zmilla93.core.utility;

/**
 * Enum representation of the 3 major OS platforms.
 * Platform.current returns the current OS.
 */
public enum Platform {

    UNKNOWN, WINDOWS, MAC, LINUX;

    public static final Platform current;
    public static final String currentOS;

    private final String name;


    static {
        currentOS = System.getProperty("os.name");
        final Platform debugPlatform = LINUX;
        if (debugPlatform != null) {
            current = debugPlatform;
        } else {
            if (currentOS.startsWith("Windows")) current = WINDOWS;
            else if (currentOS.startsWith("Mac")) current = MAC;
            else if (currentOS.startsWith("Linux")) current = LINUX;
            else current = UNKNOWN;
        }

    }

    Platform() {
        name = name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }

    @Override
    public String toString() {
        return name;
    }

}
