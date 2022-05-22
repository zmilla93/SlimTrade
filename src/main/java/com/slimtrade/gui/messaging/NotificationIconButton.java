package com.slimtrade.gui.messaging;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;

public class NotificationIconButton extends AdvancedButton {

    private final String path;

    public NotificationIconButton(String path) {
        super();
        this.path = path;
        setIcon(ColorManager.getColorIcon(path));
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(BorderFactory.createEmptyBorder(NotificationButton.INSET, NotificationButton.INSET, NotificationButton.INSET, NotificationButton.INSET));
        if (path != null) {
            setIcon(ColorManager.getColorIcon(path));
        }
    }

}
