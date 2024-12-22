package github.zmilla93.core.enums;

import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

import java.awt.*;

/**
 * Keys for context specific colors like "Approve" or "Deny". A {@link github.zmilla93.modules.theme.extensions.ThemeExtension}
 * is used to map these keys to actual colors used by a theme.
 */
public enum ThemeColor {

    /// Results
    APPROVE, DENY, INDETERMINATE, NEUTRAL,
    /// Message Colors
    INCOMING_MESSAGE, OUTGOING_MESSAGE, SCANNER_MESSAGE, UPDATE_MESSAGE,
    /// Swing References
    LABEL_FOREGROUND, BUTTON_BACKGROUND, BUTTON_FOREGROUND;

    public Color current() {
        return current(ThemeManager.isColorblindMode());
    }

    public Color current(boolean colorblind) {
        return ThemeManager.extensions().resolve(this, colorblind);
    }

    public Color getReadableTextColor() {
        Theme theme = ThemeManager.getCurrentTheme();
        boolean whiteText = theme.isDark();
        if (ThemeManager.extensions().invertTextColor.contains(this)) whiteText = !whiteText;
        if (whiteText) return Color.WHITE;
        else return Color.BLACK;
    }

}
