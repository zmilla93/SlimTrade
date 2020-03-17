package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.buttons.BasicButton;

import java.awt.*;

public class CheckUpdateButton extends BasicButton implements IColorable {

    public CheckUpdateButton(String text) {
        super(text);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        if(App.updateChecker.isUpdateAvailable()) {
            primaryColor = ColorManager.GREEN_APPROVE;
        }
    }
}
