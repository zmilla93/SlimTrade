package com.slimtrade.modules.colortheme.components;

import javax.swing.*;

public class AdvancedColorButton extends AdvancedButton {

    public AdvancedColorButton(String text) {
        super(text);
//        setBorder();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(BorderFactory.createLineBorder(UIManager.getColor("")));
    }
}
