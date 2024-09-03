package com.slimtrade.gui.components;

import com.slimtrade.core.audio.Sound;

import javax.swing.*;
import java.awt.*;

public class AudioListCellRenderer implements ListCellRenderer<Sound> {

    private final JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
    private final JLabel label = new JLabel();

    public AudioListCellRenderer() {
        label.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Sound> list, Sound value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) return separator;
        label.setText(value.name);
        separator.setForeground(UIManager.getColor("Separator.foreground"));
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
