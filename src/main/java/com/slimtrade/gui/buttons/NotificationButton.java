package com.slimtrade.gui.buttons;

import com.slimtrade.modules.theme.components.AdvancedButton;

@Deprecated
public class NotificationButton extends AdvancedButton {

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(null);
    }
}
