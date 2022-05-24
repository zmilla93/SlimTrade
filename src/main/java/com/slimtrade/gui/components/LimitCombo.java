package com.slimtrade.gui.components;

import javax.swing.*;

public class LimitCombo<T> extends JComboBox<T> {

    public LimitCombo() {
        super();
        setMaximumRowCount(10);
    }

}
