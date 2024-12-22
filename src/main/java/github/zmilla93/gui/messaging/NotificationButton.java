package github.zmilla93.gui.messaging;

import github.zmilla93.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class NotificationButton extends AdvancedButton {

    public static final int DEFAULT_INSET = 2;
    private int horizontalInset = -1;

    public NotificationButton() {
        this(null);
    }

    public NotificationButton(String text) {
        super();
        setText(text);
        updateUI();
    }

    public void setHorizontalInset(int inset) {
        horizontalInset = inset;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        int horizontal = horizontalInset == -1 ? DEFAULT_INSET : horizontalInset;
        setBorder(BorderFactory.createEmptyBorder(DEFAULT_INSET, horizontal, DEFAULT_INSET, horizontal));
    }

}
