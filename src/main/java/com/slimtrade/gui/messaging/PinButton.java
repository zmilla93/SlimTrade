package com.slimtrade.gui.messaging;

import com.slimtrade.core.utility.ColorManager;

public class PinButton extends NotificationIconButton {

    private boolean pinned;

    public PinButton() {
        super("/icons/default/pin1x48.png");
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (pinned) {
            setIcon(ColorManager.getColorIcon("/icons/default/pin2x48.png"));
        }
    }
}
