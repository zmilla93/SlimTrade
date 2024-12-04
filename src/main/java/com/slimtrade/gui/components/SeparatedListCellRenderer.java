package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Renders normal objects as strings, and null values as separators.
 */
public class SeparatedListCellRenderer implements ListCellRenderer<Object> {

    private final JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
    private final JLabel label = new JLabel();

    public SeparatedListCellRenderer() {
        label.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) {
            separator.setForeground(UIManager.getColor("Separator.foreground"));
            return separator;
        }
        label.setText(value.toString());
        if (isSelected) {
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            label.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        return label;
    }

}
