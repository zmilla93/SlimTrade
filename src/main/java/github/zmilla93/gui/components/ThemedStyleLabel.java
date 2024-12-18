package github.zmilla93.gui.components;

import github.zmilla93.modules.theme.ThemeColorVariant;
import github.zmilla93.modules.theme.ThemeColorVariantSetting;

/**
 * A StyledLabel that will respond to change in colorblind mode.
 * Only relevant if you use setColor(ThemeColorVariantSetting).
 *
 * @see StyledLabel
 */
public class ThemedStyleLabel extends StyledLabel {

    public ThemedStyleLabel() {
        super();
    }

    public ThemedStyleLabel(String text) {
        super(text);
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

}
