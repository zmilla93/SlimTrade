package com.slimtrade.modules.colortheme.components;

import javax.swing.*;
import java.awt.*;

public class ColorCombo extends JComboBox<Color> {

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
