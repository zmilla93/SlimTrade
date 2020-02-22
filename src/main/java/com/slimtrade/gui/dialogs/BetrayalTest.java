package com.slimtrade.gui.dialogs;

import com.slimtrade.gui.basic.BasicMovableDialog;

import javax.swing.*;
import java.awt.*;

public class BetrayalTest extends BasicMovableDialog {

    public BetrayalTest() {
        super(true);
        JLabel label = new JLabel();
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource("images/betrayal.png")).getImage();
        label.setIcon(new ImageIcon(image));
        this.add(label);
        this.pack();
    }

}
