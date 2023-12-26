package com.slimtrade.core.enums;

import com.slimtrade.modules.theme.ThemeManager;

import java.awt.*;

public enum StashTabColor {

    ZERO(Color.WHITE, Color.BLACK),

    ONE(new Color(124, 81, 50), ThemeManager.POE_TEXT_LIGHT),
    TWO(new Color(191, 91, 0), ThemeManager.POE_TEXT_LIGHT),
    THREE(new Color(254, 191, 128), ThemeManager.POE_TEXT_DARK),

    FOUR(new Color(86, 0, 0), ThemeManager.POE_TEXT_LIGHT),
    FIVE(new Color(191, 0, 0), ThemeManager.POE_TEXT_LIGHT),
    SIX(new Color(254, 128, 128), ThemeManager.POE_TEXT_DARK),

    SEVEN(new Color(114, 0, 83), ThemeManager.POE_TEXT_LIGHT),
    EIGHT(new Color(204, 0, 154), ThemeManager.POE_TEXT_LIGHT),
    NINE(new Color(254, 128, 222), ThemeManager.POE_TEXT_DARK),

    TEN(new Color(38, 0, 86), ThemeManager.POE_TEXT_LIGHT),
    ELEVEN(new Color(88, 0, 179), ThemeManager.POE_TEXT_LIGHT),
    TWELVE(new Color(192, 128, 254), ThemeManager.POE_TEXT_DARK),

    THIRTEEN(new Color(0, 0, 128), ThemeManager.POE_TEXT_LIGHT),
    FOURTEEN(new Color(0, 0, 254), ThemeManager.POE_TEXT_LIGHT),
    FIFTEEN(new Color(128, 179, 254), ThemeManager.POE_TEXT_DARK),

    SIXTEEN(new Color(0, 73, 0), ThemeManager.POE_TEXT_LIGHT),
    SEVENTEEN(new Color(0, 191, 0), ThemeManager.POE_TEXT_DARK),
    EIGHTEEN(new Color(128, 254, 128), ThemeManager.POE_TEXT_DARK),

    NINETEEN(new Color(98, 128, 0), ThemeManager.POE_TEXT_LIGHT),
    TWENTY(new Color(191, 244, 0), ThemeManager.POE_TEXT_DARK),
    TWENTY_ONE(new Color(239, 254, 128), ThemeManager.POE_TEXT_DARK),

    TWENTY_TWO(new Color(254, 170, 0), ThemeManager.POE_TEXT_DARK),
    TWENTY_THREE(new Color(254, 213, 0), ThemeManager.POE_TEXT_DARK),
    TWENTY_FOUR(new Color(254, 254, 153), ThemeManager.POE_TEXT_DARK),

    TWENTY_FIVE(new Color(42, 42, 42), ThemeManager.POE_TEXT_LIGHT),
    TWENTY_SIX(new Color(135, 135, 135), ThemeManager.POE_TEXT_DARK),
    TWENTY_SEVEN(new Color(221, 221, 221), ThemeManager.POE_TEXT_DARK),
    ;

    private final Color backgroundColor;
    private final Color foregroundColor;

    StashTabColor(Color bg, Color foreground) {
        this.backgroundColor = bg;
        this.foregroundColor = foreground;
    }

    public Color getBackground() {
        return this.backgroundColor;
    }

    public Color getForeground() {
        return this.foregroundColor;
    }

    public static StashTabColor getValueFromColor(Color color) {
        for (StashTabColor c : StashTabColor.values()) {
            if (color.equals(c.getBackground())) {
                return c;
            }
        }
        return null;
    }

    public static StashTabColor get(int index) {
        if (index < 0 || index >= StashTabColor.values().length) return StashTabColor.ZERO;
        return StashTabColor.values()[index];
    }

}
