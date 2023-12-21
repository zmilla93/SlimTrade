package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

@Deprecated
// FIXME : Should start using StyledLabel
public class ColorLabel extends JLabel {

    private String key;
    public boolean bold;

    public ColorLabel(String text) {
        super(text);
    }

    public ColorLabel(String text, String key) {
        super(text);
        this.key = key;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (key != null)
            setForeground(UIManager.getColor(key));
        if (getFont() != null)
            if (bold)
                setFont(getFont().deriveFont(Font.BOLD));
    }

}