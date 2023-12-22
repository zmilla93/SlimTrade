package com.slimtrade.gui.components;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.listening.IColorBlindChangeListener;
import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;

/**
 * A StyledLabel that will respond to change in colorblind mode.
 * Only relevant if you use setColor(ThemeColorVariantSetting).
 *
 * @see StyledLabel
 */
public class ThemedStyleLabel extends StyledLabel implements IColorBlindChangeListener {

    public ThemedStyleLabel() {
        super();
        registerListener();
    }

    public ThemedStyleLabel(String text) {
        super(text);
        registerListener();
    }

    /**
     * Sets text color using a ThemeColorVariant.
     *
     * @param colorVariantSetting Target color variant
     * @see ThemeColorVariant
     * @see ThemeColorVariantSetting
     */
    public void setColor(ThemeColorVariantSetting colorVariantSetting) {
        this.colorVariant = colorVariantSetting;
        colorMode = ColorMode.VARIANT;
        updateColor();
    }

    private void registerListener() {
        SaveManager.settingsSaveFile.data.addColorBlindListener(this);
    }

    @Override
    public void onColorBlindChange(boolean colorBlind) {
        colorVariant.setColorBlind(colorBlind);
        updateColor();
    }

}
