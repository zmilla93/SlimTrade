package com.slimtrade.gui.buttons;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {

    private String path;

    public IconButton(String path) {
        super();
        this.path = path;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (path != null)
            setIcon(ColorManager.getIcon(path));
        int borderInset = 3;
        setBorder(new FlatButtonBorder() {
            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                return new Insets(borderInset, borderInset, borderInset, borderInset);
            }
        });
    }


}
