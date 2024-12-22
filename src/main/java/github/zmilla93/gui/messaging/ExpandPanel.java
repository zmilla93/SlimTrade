package github.zmilla93.gui.messaging;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.modules.theme.components.AdvancedButton;

public class ExpandPanel extends NotificationPanel {

    public ExpandPanel() {
        super(ThemeColor.BUTTON_FOREGROUND);
        playerNameButton.setText("Show Messages");
        bottomContainer.setVisible(false);
        pricePanel.setVisible(false);
        closeButton.setVisible(false);
        stopTimer();
        setup();
    }

    public void setText(String text) {
        playerNameButton.setText(text);
    }

    public AdvancedButton getButton() {
        return playerNameButton;
    }

}
