package com.slimtrade.gui.buttons;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {

    private String path;
    private int size;

    public IconButton(String path) {
        super();
        this.path = path;
        updateUI();
    }

    public IconButton(String path, int size) {
        super();
        this.path = path;
        this.size = size;
        updateUI();
    }

    public void setIconSize(int size) {
        this.size = size;
        updateUI();
        // FIXME:
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (path != null)
            setIcon(ColorManager.getColorIcon(path, size));
        int borderInset = 5;
        setBorder(new FlatButtonBorder() {
            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                return new Insets(borderInset, borderInset, borderInset, borderInset);
            }
        });
    }


}
