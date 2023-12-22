package com.slimtrade.modules.theme;

import com.slimtrade.core.managers.SaveManager;

public class ThemeColorVariantSetting {

    private final ThemeColorVariant variant;
    private final boolean opposite;
    private final boolean colorBlind;
    private final boolean inferColorBind;

    public ThemeColorVariantSetting(ThemeColorVariant variant) {
        this.variant = variant;
        this.opposite = false;
        this.colorBlind = false;
        this.inferColorBind = true;
    }

    public ThemeColorVariantSetting(ThemeColorVariant variant, boolean opposite) {
        this.variant = variant;
        this.opposite = opposite;
        this.colorBlind = false;
        this.inferColorBind = true;
    }

    public ThemeColorVariantSetting(ThemeColorVariant variant, boolean opposite, boolean colorBlind) {
        this.variant = variant;
        this.opposite = opposite;
        this.colorBlind = colorBlind;
        this.inferColorBind = false;
    }

    public ThemeColorVariant variant() {
        return variant;
    }

    public boolean opposite() {
        return opposite;
    }

    public boolean colorBlind() {
        if (inferColorBind) return SaveManager.settingsSaveFile.data.colorBlindMode;
        return colorBlind;
    }

}
