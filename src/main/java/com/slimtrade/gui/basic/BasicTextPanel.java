package com.slimtrade.gui.basic;

import javax.swing.*;
import java.awt.*;

public class BasicTextPanel extends JPanel {

    public Color backgroundDefault;
    public Color backgroundHover;
    public Color backgroundClick;

    public Color textDefault;
    public Color textHover;
    public Color textClick;

    public Color borderDefault;
    public Color borderHover;
    public Color borderClick;

    private JLabel label = new JLabel();

    public BasicTextPanel() {
        this(null);
    }

    public BasicTextPanel(String text) {
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

}
