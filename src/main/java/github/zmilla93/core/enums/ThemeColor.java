package github.zmilla93.core.enums;

import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
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
            () -> ThemeManager.getCurrentTheme().extensions.INDETERMINATE_KEY),
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

    public final ColorKeyGetter keyGetter;
    public final ColorKeyGetter keyGetterCb;

    ThemeColor(ColorKeyGetter keyGetter) {
        this(keyGetter, keyGetter);
    }

    ThemeColor(ColorKeyGetter keyGetter, ColorKeyGetter keyGetterCb) {
        this.keyGetter = keyGetter;
        this.keyGetterCb = keyGetterCb;
    }

    public Color current() {
        return current(ThemeManager.isColorblindMode());
    }

    /// Uses the keyGetter to fetch a color from the UIManager
    public Color current(boolean colorBlind) {
        if (colorBlind) return UIManager.getColor(keyGetterCb.getKey());
        else return UIManager.getColor(keyGetter.getKey());
    }

    public Color getReadableTextColor() {
        Theme theme = ThemeManager.getCurrentTheme();
        boolean whiteText = theme.isDark();
        if (ThemeManager.extensions().invertTextColor.contains(this)) whiteText = !whiteText;
        if (whiteText) return Color.WHITE;
        else return Color.BLACK;
    }

    /// Lambda for getting the UIManager color key from the current theme
    public interface ColorKeyGetter {
        String getKey();
    }

}
