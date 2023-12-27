package com.slimtrade.gui.components;

import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;

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
        setBold(true);
        setColor(new ThemeColorVariantSetting(ThemeColorVariant.RED, true));
    }

}
