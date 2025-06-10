package github.zmilla93.gui.components.poe.detection;

import github.zmilla93.core.utility.Platform;
import github.zmilla93.modules.theme.OLD_ThemeColor;

/**
 * The possible results when trying to detect the Path of Exile game window.
 */
public enum GameDetectionResult {

    NOT_RUN(OLD_ThemeColor.NEUTRAL, "Click to set the game window location."),
    FAIL(OLD_ThemeColor.DENY, "Window not found. Ensure Path of Exile 1 or 2 is running."),
    MINIMIZED(OLD_ThemeColor.INDETERMINATE, "Game is minimized."),
    SUCCESS(OLD_ThemeColor.APPROVE, "Window location detected."),
    NOT_SUPPORTED(OLD_ThemeColor.DENY, "Not supported on " + Platform.current + ".");

    public final String message;
    public final OLD_ThemeColor status;

    GameDetectionResult(OLD_ThemeColor status, String message) {
        this.message = message;
        this.status = status;
    }

}
