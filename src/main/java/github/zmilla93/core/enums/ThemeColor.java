package github.zmilla93.core.enums;

import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

import java.awt.*;

/**
 * Keys for context specific colors like "Approve" or "Deny". A ThemeExtension can map these keys to
 * the actual colors that should be used by the current theme.
 */
public enum ThemeColor {

    APPROVE, DENY, INDETERMINATE, NEUTRAL,
    INCOMING_MESSAGE, OUTGOING_MESSAGE, SCANNER_MESSAGE, UPDATE_MESSAGE,
    TEXT_COLOR;

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
