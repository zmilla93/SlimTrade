package com.slimtrade.gui.options;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.buttons.BasicButton;

public class updateButton extends BasicButton implements IColorable {

    public updateButton(String text) {
        super(text);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        primaryColor = ColorManager.GREEN_APPROVE;
    }
}
