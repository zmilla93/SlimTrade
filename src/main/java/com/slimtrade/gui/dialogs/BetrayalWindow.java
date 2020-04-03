package com.slimtrade.gui.dialogs;

import com.slimtrade.gui.basic.BasicMovableDialog;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;
import java.awt.*;

public class BetrayalWindow extends BasicMovableDialog {

    public BetrayalWindow() {
        super(true);
        JLabel label = new CustomLabel();
        Image image = new ImageIcon(this.getClass().getClassLoader().getResource("images/betrayal.png")).getImage();
        label.setIcon(new ImageIcon(image));
        this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        this.add(label);
        this.pack();
    }

}
