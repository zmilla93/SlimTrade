package github.zmilla93.gui.messaging;

import github.zmilla93.App;

public class UpdateMessagePanel extends NotificationPanel {

    public UpdateMessagePanel(String tag) {
        pricePanel.setVisible(false);
        if (tag != null && App.getAppInfo().appVersion.isPreRelease)
            playerNameButton.setText("SlimTrade " + tag + " Available!");
        else playerNameButton.setText("SlimTrade Update Available!");
        itemButton.setText("Click here to update now, or restart later.");
        playerNameButton.addActionListener(e -> App.updateManager.runUpdateProcessFromSwing());
        itemButton.addActionListener(e -> App.updateManager.runUpdateProcessFromSwing());
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        // TODO
    }

}
