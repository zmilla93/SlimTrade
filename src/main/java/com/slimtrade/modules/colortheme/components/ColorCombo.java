package com.slimtrade.modules.colortheme.components;

import com.slimtrade.gui.components.LimitCombo;

import java.awt.*;

public class ColorCombo extends LimitCombo<Color> {

    public ColorCombo() {
        addActionListener(e -> {
            Color color = (Color) getSelectedItem();
            setBackground(color);
        });
    }

    @Override
    public void addItem(Color item) {
        super.addItem(item);
    }
}
