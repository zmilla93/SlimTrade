package com.slimtrade.gui.basic;

import javax.swing.*;

public class ColorLabel extends JLabel {

    private String key;

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
    }

}
