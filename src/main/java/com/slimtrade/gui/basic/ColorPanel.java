package com.slimtrade.gui.basic;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {

    private String key;

    public ColorPanel(String key){
        this.key = key;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if(key != null){
            Color color = UIManager.getColor(key);
            setBackground(color);
        }

    }
}

