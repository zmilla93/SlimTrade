package github.zmilla93.gui.messaging;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class ExpandPanel extends NotificationPanel {

    public ExpandPanel() {
        // FIXME : Expand message color?
        super(ThemeColor.NEUTRAL);
        playerNameButton.setText("Show Messages");
        bottomContainer.setVisible(false);
        pricePanel.setVisible(false);
//        borderPanel.setBackgroundKey("Label.foreground");
        closeButton.setVisible(false);
        stopTimer();
        setup();
    }

    @Override
    protected void resolveMessageColor() {
        messageColor = UIManager.getColor("Button.foreground");
    }

    public void setText(String text) {
        playerNameButton.setText(text);
    }

    public AdvancedButton getButton() {
        return playerNameButton;
    }

}
