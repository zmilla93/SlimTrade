package com.slimtrade.gui.messaging;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;

public class NotificationIconButton extends AdvancedButton {

    private final String path;
    private static final int DEFAULT_INSET = NotificationButton.INSET;
    public int inset;

    public NotificationIconButton(String path) {
        super();
        this.path = path;
        setIcon(ColorManager.getColorIcon(path));
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        int curInset = inset == 0 ? DEFAULT_INSET : inset;
        setBorder(BorderFactory.createEmptyBorder(curInset, curInset, curInset, curInset));
        if (path != null) {
            setIcon(ColorManager.getColorIcon(path));
        }
    }

}
