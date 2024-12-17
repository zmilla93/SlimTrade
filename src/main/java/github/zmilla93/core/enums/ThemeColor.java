package github.zmilla93.core.enums;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.extensions.ThemeExtension;

import javax.swing.*;
import java.awt.*;

/**
 * Result actions that map to {@link ThemeExtension} colors.
 */
public enum ThemeColor {

    APPROVE(
            () -> ThemeManager.getCurrentTheme().extensions.APPROVE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.APPROVE_KEY),
    DENY(
            () -> ThemeManager.getCurrentTheme().extensions.DENY_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.DENY_KEY),
    INDETERMINATE(
            () -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY),
    NEUTRAL(
            () -> ThemeManager.getCurrentTheme().extensions.NEUTRAL_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.NEUTRAL_KEY),
    TEXT_COLOR(
            () -> "Label.foreground", () -> "Label.foreground"),
    ;

    private final ColorKeyGetter keyGetter;
    private final ColorKeyGetter keyGetterCb;

    ThemeColor(ColorKeyGetter keyGetter, ColorKeyGetter keyGetterCb) {
        this.keyGetter = keyGetter;
        this.keyGetterCb = keyGetterCb;
    }

    /// Uses the keyGetter to fetch a color from the UIManager
    public Color current() {
        return current(SaveManager.settingsSaveFile.data.colorBlindMode);
    }

    public Color current(boolean colorBlind) {
        if (colorBlind) UIManager.getColor(keyGetterCb.getKey());
        return UIManager.getColor(keyGetter.getKey());
    }

    /// Lambda for getting the UIManager color key from the current theme
    private interface ColorKeyGetter {
        String getKey();
    }

    public static void applyBackground(Component component, ThemeColor color) {
        component.setBackground(color.current());
        component.addPropertyChangeListener("UI", e -> component.setBackground(color.current()));
    }

//    public static void applyForeground(Component component, ThemeColor color) {
//        component.setForeground(color.current());
//        component.addPropertyChangeListener("UI", e -> component.setForeground(color.current()));
//    }

}
