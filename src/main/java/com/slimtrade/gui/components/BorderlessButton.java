package com.slimtrade.gui.components;

import com.slimtrade.modules.theme.components.AdvancedButton;

public class BorderlessButton extends AdvancedButton {

    public BorderlessButton() {
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(null);
    }

}
