package com.slimtrade.gui.dialogs;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class BetrayalWindow extends AbstractWindow {

    public BetrayalWindow() {
        super("Betrayal Guide", true, true);
        JLabel label = new CustomLabel();
        Image image = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("images/betrayal.png"))).getImage();
        label.setIcon(new ImageIcon(image));
        this.add(label);
        this.pack();
        ColorManager.recursiveColor(this);
    }

}
