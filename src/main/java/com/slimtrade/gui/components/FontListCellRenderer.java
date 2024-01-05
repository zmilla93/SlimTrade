package com.slimtrade.gui.components;

import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

/**
 * Used for a JComboBox that has a list of font names to render the fonts in the dropdown.
 */
public class FontListCellRenderer extends JLabel implements ListCellRenderer<String> {

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        Font font = new Font(value, Font.PLAIN, SaveManager.settingsSaveFile.data.fontSize);
        setFont(font);
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setText(value);
        return this;
    }


}
