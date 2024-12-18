package github.zmilla93.core.enums;

import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * Maps a context specific color like "incoming trade" to a UIManager key defined by the current theme.
 * Allows optionally defining a secondary key for colorblind mode.
 */
public enum ThemeColor {

    APPROVE(
            () -> ThemeManager.getCurrentTheme().extensions.APPROVE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.APPROVE_KEY_CB),
    DENY(
            () -> ThemeManager.getCurrentTheme().extensions.DENY_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.DENY_KEY_CB),
    INDETERMINATE(
            () -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY_CB),
    NEUTRAL(
            () -> ThemeManager.getCurrentTheme().extensions.NEUTRAL_KEY),
    INCOMING_MESSAGE(
            () -> ThemeManager.getCurrentTheme().extensions.INCOMING_TRADE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.INCOMING_TRADE_CB_KEY),
    OUTGOING_MESSAGE(
            () -> ThemeManager.getCurrentTheme().extensions.OUTGOING_TRADE_KEY,
            () -> ThemeManager.getCurrentTheme().extensions.OUTGOING_TRADE_CB_KEY),
    SCANNER_MESSAGE(
            () -> ThemeManager.getCurrentTheme().extensions.SCANNER_MESSAGE_CB_KEY),
    UPDATE_MESSAGE(
            () -> ThemeManager.getCurrentTheme().extensions.UPDATE_MESSAGE_CB_KEY),
    TEXT_COLOR(
            () -> "Label.foreground", () -> "Label.foreground"),
    ;

    private final ColorKeyGetter keyGetter;
    private final ColorKeyGetter keyGetterCb;

    ThemeColor(ColorKeyGetter keyGetter) {
        this.keyGetter = keyGetter;
        this.keyGetterCb = keyGetter;
    }

    ThemeColor(ColorKeyGetter keyGetter, ColorKeyGetter keyGetterCb) {
        this.keyGetter = keyGetter;
        this.keyGetterCb = keyGetterCb;
    }

    /// Uses the keyGetter to fetch a color from the UIManager
    public Color current() {
        return current(ThemeManager.isColorblindMode());
    }

    public ColorUIResource current(boolean colorBlind) {
//        if (colorBlind) UIManager.getColor(keyGetterCb.getKey());
//        return UIManager.getColor(keyGetter.getKey());
        if (colorBlind) return (ColorUIResource) UIManager.get(keyGetterCb.getKey());
        else return (ColorUIResource) UIManager.get(keyGetter.getKey());
    }

    /// Lambda for getting the UIManager color key from the current theme
    private interface ColorKeyGetter {
        String getKey();
    }

    public static void applyBackground(Component component, ThemeColor color) {
        component.setBackground(color.current());
        component.addPropertyChangeListener("UI", e -> component.setBackground(color.current()));
    }

    public Color getReadableTextColor() {
        Theme theme = ThemeManager.getCurrentTheme();
        if (theme.isDark()) return Color.WHITE;
        return Color.BLACK;
    }

//    public Color getReadableTextColor() {
//        Color currentColor = current();
//        double luminance = (0.299 * currentColor.getRed() +
//                0.587 * currentColor.getGreen() +
//                0.114 * currentColor.getBlue()) / 255.0;
//        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
//    }

//    public static void applyForeground(Component component, ThemeColor color) {
//        component.setForeground(color.current());
//        component.addPropertyChangeListener("UI", e -> component.setForeground(color.current()));
//    }

}
