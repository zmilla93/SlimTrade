package github.zmilla93.core.enums;

import github.zmilla93.modules.theme.extensions.ThemeExtension;
import github.zmilla93.modules.theme.ThemeManager;

import java.awt.*;

/**
 * Result actions that map to {@link ThemeExtension} colors.
 */
@Deprecated
public enum ResultStatus {

    NEUTRAL, INDETERMINATE, APPROVE, DENY;

    public Color toColor() {
        ThemeExtension extension = ThemeManager.getCurrentExtensions();
        switch (this) {
            case INDETERMINATE:
                return extension.indeterminate;
            case APPROVE:
                return extension.approve();
            case DENY:
                return extension.deny;
            case NEUTRAL:
            default:
                return extension.getTextColor();
        }
    }

}
