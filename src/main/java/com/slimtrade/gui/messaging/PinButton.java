package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.DefaultIcon;

public class PinButton extends NotificationIconButton {

    private boolean pinned;

    public PinButton() {
        super(DefaultIcon.PIN1);
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
        updateUI();
    }

    @Override
    public void updateUI() {
        if (pinned) {
            path = DefaultIcon.PIN2.path();
        } else {
            path = DefaultIcon.PIN1.path();
        }
        super.updateUI();
    }
}
