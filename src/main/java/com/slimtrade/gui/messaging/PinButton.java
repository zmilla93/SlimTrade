package com.slimtrade.gui.messaging;

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
        if (pinned) {
            path = "/icons/default/pin2x48.png";
        } else {
            path = "/icons/default/pin1x48.png";
        }
        super.updateUI();
    }
}
