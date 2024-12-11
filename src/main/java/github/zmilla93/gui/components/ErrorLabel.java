package github.zmilla93.gui.components;

import github.zmilla93.modules.theme.ThemeColorVariant;
import github.zmilla93.modules.theme.ThemeColorVariantSetting;

public class ErrorLabel extends ThemedStyleLabel {

    public ErrorLabel() {
        super();
        setup();
    }

    public ErrorLabel(String text) {
        super(text);
        setup();
    }

    private void setup() {
        setColor(new ThemeColorVariantSetting(ThemeColorVariant.RED, true));
    }

}
