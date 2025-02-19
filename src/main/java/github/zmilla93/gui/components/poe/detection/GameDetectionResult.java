package github.zmilla93.gui.components.poe.detection;

import github.zmilla93.core.utility.Platform;
import github.zmilla93.modules.theme.ThemeColor;

/**
 * The possible results when trying to detect the Path of Exile game window.
 */
public enum GameDetectionResult {

    NOT_RUN(ThemeColor.NEUTRAL, "Click to set the game window location."),
    FAIL(ThemeColor.DENY, "Window not found. Ensure Path of Exile 1 or 2 is running."),
    MINIMIZED(ThemeColor.INDETERMINATE, "Game is minimized."),
    SUCCESS(ThemeColor.APPROVE, "Window location detected."),
    NOT_SUPPORTED(ThemeColor.DENY, "Not supported on " + Platform.current + ".");

    public final String message;
    public final ThemeColor status;

    GameDetectionResult(ThemeColor status, String message) {
        this.message = message;
        this.status = status;
    }

}
