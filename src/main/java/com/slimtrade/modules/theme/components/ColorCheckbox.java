package com.slimtrade.modules.theme.components;

import com.formdev.flatlaf.icons.FlatCheckBoxIcon;

import javax.swing.*;

/**
 * A checkbox that will always match the color of a JTextField.
 * Used when the theme's default checkbox doesn't look quite right.
 */
public class ColorCheckbox extends FlatCheckBoxIcon {

    public ColorCheckbox() {
        this.background = UIManager.getColor("TextField.background");
        this.checkmarkColor = UIManager.getColor("TextField.background");
    }

}
