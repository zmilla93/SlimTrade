package github.zmilla93.modules.theme;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.modules.updater.ZLogger;

import java.awt.*;

public enum ThemeColorVariant {

    RED, GREEN, ORANGE;

    public static final Color DARK_GREEN = new Color(44, 145, 35);
    public static final Color DARK_RED = new Color(133, 17, 17);
    public static final Color DARK_ORANGE = new Color(190, 96, 6);
    public static final Color DARK_BLUE_CB = new Color(31, 62, 164);
    public static final Color DARK_MAGENTA_CB = new Color(173, 21, 143);
    public static final Color DARK_ORANGE_CB = new Color(190, 96, 6);

    public static final Color LIGHT_GREEN = new Color(105, 201, 97);
    public static final Color LIGHT_RED = new Color(229, 88, 88);
    public static final Color LIGHT_ORANGE = new Color(220, 146, 72);
    public static final Color LIGHT_BLUE_CB = new Color(95, 105, 213);
    public static final Color LIGHT_MAGENTA_CB = new Color(204, 103, 157);
    public static final Color LIGHT_ORANGE_CB = new Color(220, 146, 72);

    public static Color getColorVariant(ThemeColorVariant variant) {
        return getColorVariant(variant, SaveManager.settingsSaveFile.data.colorBlindMode);
    }

    public static Color getColorVariant(ThemeColorVariant variant, boolean opposite) {
        return getColorVariant(variant, opposite, SaveManager.settingsSaveFile.data.colorBlindMode);
    }

    public static Color getColorVariant(ThemeColorVariant variant, boolean opposite, boolean colorBlind) {
        boolean dark = opposite != ThemeManager.getCurrentTheme().isDark();
        if (dark) {
            switch (variant) {
                case RED:
                    if (colorBlind) return DARK_MAGENTA_CB;
                    else return DARK_RED;
                case GREEN:
                    if (colorBlind) return DARK_BLUE_CB;
                    else return DARK_GREEN;
                case ORANGE:
                    if (colorBlind) return DARK_ORANGE_CB;
                    else return DARK_ORANGE;
            }
        }
        switch (variant) {
            case RED:
                if (colorBlind) return LIGHT_MAGENTA_CB;
                else return LIGHT_RED;
            case GREEN:
                if (colorBlind) return LIGHT_BLUE_CB;
                else return LIGHT_GREEN;
            case ORANGE:
                if (colorBlind) return LIGHT_ORANGE_CB;
                else return LIGHT_ORANGE;
        }
        ZLogger.err("Invalid color variant!");
        return Color.WHITE;
    }

    public static Color getMessageColor(TradeOfferType messageType) {
        return getMessageColor(messageType, SaveManager.settingsSaveFile.data.colorBlindMode);
    }

    public static Color getMessageColor(TradeOfferType messageType, boolean colorBlind) {
        boolean dark = ThemeManager.getCurrentTheme().isDark();
        if (dark) {
            switch (messageType) {
                case INCOMING_TRADE:
                    if (colorBlind) return DARK_BLUE_CB;
                    return DARK_GREEN;
                case OUTGOING_TRADE:
                    if (colorBlind) return DARK_MAGENTA_CB;
                    return DARK_RED;
                case CHAT_SCANNER_MESSAGE:
                    if (colorBlind) return DARK_ORANGE_CB;
                    return DARK_ORANGE;
            }
        }
        switch (messageType) {
            case INCOMING_TRADE:
                if (colorBlind) return LIGHT_BLUE_CB;
                return LIGHT_GREEN;
            case OUTGOING_TRADE:
                if (colorBlind) return LIGHT_MAGENTA_CB;
                return LIGHT_RED;
            case CHAT_SCANNER_MESSAGE:
                if (colorBlind) return LIGHT_ORANGE_CB;
                return LIGHT_ORANGE;
        }
        ZLogger.err("Invalid color variant!");
        return Color.WHITE;
    }

}
