package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class CustomCombo<E> extends JComboBox<E> implements IColorable {

    private Color bg;
    private Color text;

    public CustomCombo() {
        super();
        this.setOpaque(false);
        this.setFocusable(false);
        this.setUI(new CustomComboBoxUI());
        // Color Rendering
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                list.setBorder(null);
                if (isSelected) {
                    list.setSelectionBackground(text);
                    list.setSelectionForeground(bg);
                } else {
                    c.setBackground(bg);
                    c.setForeground(text);
                }
                return c;
            }
        });
    }

    @Override
    public void updateColor() {
        bg = ColorManager.TEXT_EDIT_BACKGROUND;
        text = ColorManager.TEXT;
        this.setForeground(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        this.setUI(new CustomComboBoxUI());
    }
}
