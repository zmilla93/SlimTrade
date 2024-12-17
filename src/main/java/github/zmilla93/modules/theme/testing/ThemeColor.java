package github.zmilla93.modules.theme.testing;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Retrieves context specific colors using color keys from the current theme's {@link github.zmilla93.modules.theme.extensions.ThemeExtension}.
 */
public enum ThemeColor {

    APPROVE(() -> ThemeManager.getCurrentTheme().extensions.APPROVE_KEY),
    DENY(() -> ThemeManager.getCurrentTheme().extensions.DENY_KEY),
    INDETERMINATE(() -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY),
    NEUTRAL(() -> ThemeManager.getCurrentTheme().extensions.NEUTRAL_KEY),
    TEXT_COLOR(() -> "Label.foreground"),
    // TODO : Trade colors
    ;

    private final ColorKeyGetter keyGetter;

    ThemeColor(ColorKeyGetter keyGetter) {
        this.keyGetter = keyGetter;
    }

    /// Uses the keyGetter to fetch a color from the UIManager
    public Color current() {
        return UIManager.getColor(keyGetter.getKey());
    }

    /// Lambda for getting the UIManager color key from the current theme
    private interface ColorKeyGetter {
        String getKey();
    }

}
