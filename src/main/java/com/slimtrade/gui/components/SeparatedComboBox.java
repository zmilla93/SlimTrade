package com.slimtrade.gui.components;

import javax.swing.*;

/**
 * The same as a normal JComboBox, but renders a separator when given a null value.
 * @param <T>
 */
public class SeparatedComboBox<T> extends JComboBox<T> {

    public SeparatedComboBox() {
        setRenderer(new SeparatedListCellRenderer());
    }

    // Separators are represented by null values, so don't allow them to be selected.
    @Override
    public void setSelectedItem(Object obj) {
        if (obj == null) return;
        super.setSelectedItem(obj);
    }

}
