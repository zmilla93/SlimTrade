package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.observing.improved.EventManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class PaintedTextPanel extends PaintedPanel implements IColorable {

    private JLabel label = new JLabel();

    public PaintedTextPanel() {
        this(null);
    }

    public PaintedTextPanel(String text) {
        label.setText(text);
        this.setLayout(new GridBagLayout());
        this.add(label);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public void appendLabe(String text) {
        this.label.setText(label.getText() + text);
    }

    @Override
    public void updateColor() {
//        super.updateColor();
        this.setForeground(textDefault);
    }
}
