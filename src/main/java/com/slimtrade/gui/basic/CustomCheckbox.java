package com.slimtrade.gui.basic;

import javax.swing.*;

public class CustomCheckbox extends JCheckBox {

    public CustomCheckbox() {
//        super();
        this.setFocusPainted(false);
        this.setIcon(new CustomCheckboxIcon());
    }

}
