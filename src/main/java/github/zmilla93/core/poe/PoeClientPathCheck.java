package github.zmilla93.core.poe;

import github.zmilla93.modules.theme.ThemeColor;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Results when validating the path to A
 */
public enum PoeClientPathCheck {

    MISSING_POE_FOLDER(ThemeColor.DENY, "The '%s' no longer exists.", true),
    MISSING_LOGS_FOLDER(ThemeColor.INDETERMINATE, "Folder name is correct, but is missing a '" + GameSettings.LOG_FOLDER_NAME + "' folder."),
    MISSING_CLIENT_FILE(ThemeColor.INDETERMINATE, "%s's " + GameSettings.LOG_FOLDER_NAME + " folder is missing a " + GameSettings.CLIENT_TXT_NAME + " file.", true),
    VALID(ThemeColor.APPROVE, "Folder set correctly."),

    DETECTED_SINGLE(ThemeColor.APPROVE, "Folder auto detected."),
    DETECTED_MULTIPLE(ThemeColor.INDETERMINATE, "Multiple folders auto detected."),
    ;

    public final ThemeColor status;
    private final String message;
    public final boolean requiresFormatting;

    PoeClientPathCheck(ThemeColor status, String message) {
        this(status, message, false);
    }

    PoeClientPathCheck(ThemeColor status, String message, boolean requiresFormatting) {
        this.status = status;
        this.message = message;
        this.requiresFormatting = requiresFormatting;
    }

    /// Some messages require formatting
    public String getMessage(Game game) {
        if (requiresFormatting) return String.format(message, game);
        else return message;
    }

    ///
    public static boolean isValidInstallFolder(String pathString) {
        if (pathString == null) return false;
        return isValidInstallFolder(Paths.get(pathString));
    }

    public static boolean isValidInstallFolder(Path path) {
        return validateInstallFolder(path).status == ThemeColor.APPROVE;
    }

    public static PoeClientPathCheck validateInstallFolder(Path poeFolderPath) {
        if (poeFolderPath == null) return MISSING_POE_FOLDER;
        if (!poeFolderPath.toFile().exists()) return MISSING_POE_FOLDER;
        Path logsFolder = poeFolderPath.resolve(GameSettings.LOG_FOLDER_NAME);
        if (!logsFolder.toFile().exists()) return MISSING_LOGS_FOLDER;
        Path clientFile = logsFolder.resolve(GameSettings.CLIENT_TXT_NAME);
        if (!clientFile.toFile().exists()) return MISSING_CLIENT_FILE;
        return VALID;
    }

    // FIXME : Auto path detection

}
