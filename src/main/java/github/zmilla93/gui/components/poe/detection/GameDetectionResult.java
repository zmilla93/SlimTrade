package github.zmilla93.gui.components.poe.detection;

import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.core.utility.Platform;

/**
 * The possible results when trying to detect the Path of Exile game window.
 */
public enum GameDetectionResult {

    NOT_RUN(ResultStatus.NEUTRAL, "Click to set the game window location."),
    FAIL(ResultStatus.DENY, "Window not found. Ensure Path of Exile 1 or 2 is running."),
    MINIMIZED(ResultStatus.INDETERMINATE, "Game is minimized."),
    SUCCESS(ResultStatus.APPROVE, "Window location detected."),
    NOT_SUPPORTED(ResultStatus.DENY, "Not supported on " + Platform.current + ".");

    public final String message;
    public final ResultStatus status;

    GameDetectionResult(ResultStatus status, String message) {
        this.message = message;
        this.status = status;
    }

}
