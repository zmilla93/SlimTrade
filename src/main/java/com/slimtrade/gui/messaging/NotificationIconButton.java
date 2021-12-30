package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;

public class NotificationIconButton extends JButton {

    int ICON_INSET = 1;
    private final String path;

    public NotificationIconButton(String path) {
        super();
        this.path = path;
        setIcon(ColorManager.getIcon(path));
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(BorderFactory.createEmptyBorder(ICON_INSET, ICON_INSET, ICON_INSET, ICON_INSET));
        if (App.initialized && path != null){
            setIcon(ColorManager.getIcon(path));
        }
    }

}
