package com.slimtrade.modules.colortheme.components;

import com.formdev.flatlaf.icons.FlatCheckBoxIcon;

import javax.swing.*;

public class ColorCheckbox extends FlatCheckBoxIcon {

    public ColorCheckbox() {
        // Background
        this.background = UIManager.getColor("TextField.background");
        this.checkmarkColor = UIManager.getColor("TextField.background");
        // Foreground
//        this.selectedBackground = UIManager.getColor("Menu.foreground");
//        this.disabledSelectedBackground = UIManager.getColor("Menu.foreground");
//        this.focusedSelectedBackground = UIManager.getColor("Menu.foreground");
//        this.hoverSelectedBackground = UIManager.getColor("Menu.foreground");
//        this.focusedBorderColor = UIManager.getColor("Menu.foreground");
//        this.focusedSelectedBorderColor = UIManager.getColor("Menu.foreground");
    }

}
