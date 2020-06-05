package com.slimtrade.gui.custom;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;

public class CustomTextFieldLabel extends CustomTextField implements IColorable {

    public CustomTextFieldLabel() {
        super();
        buildLabel();
    }

    public CustomTextFieldLabel(int col) {
        super(col);
        buildLabel();
    }

    public CustomTextFieldLabel(String text) {
        super(text);
        buildLabel();
    }

    private void buildLabel() {
        this.setEditable(false);
//        this.setOpaque(false);
        this.setFocusable(false);
        this.setBorder(null);
        this.enableBorder = false;
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
    }
}
