package com.slimtrade.gui.messaging;

import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class NotificationIconButton extends AdvancedButton {

    private static final int DEFAULT_INSET = NotificationButton.INSET;
    protected String path;
    private int inset;

    // FIXME : Should this be an IIcon instead?
    public NotificationIconButton(String path) {
        super();
        this.path = path;
        setIcon(ThemeManager.getColorIcon(path));
        updateUI();
    }

    public void setInset(int inset) {
        this.inset = inset;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        int curInset = inset == 0 ? DEFAULT_INSET : inset;
        setBorder(BorderFactory.createEmptyBorder(curInset, curInset, curInset, curInset));
        if (path != null) {
            setIcon(ThemeManager.getColorIcon(path));
        }
    }

}
