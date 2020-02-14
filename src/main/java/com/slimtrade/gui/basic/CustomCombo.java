package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.buttons.CustomArrowButton;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import java.awt.*;

public class CustomCombo<E> extends JComboBox<E> implements IColorable {

    private Color bg;
    private Color text;

    public CustomCombo() {
        super();
        this.setOpaque(false);
        this.setFocusable(false);

        this.setUI(new BasicComboBoxUI() {

            // Arrow Button
            @Override
            protected JButton createArrowButton() {
                return new CustomArrowButton(BasicArrowButton.SOUTH);
            }

            // Scrollbar
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scrollPane = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
                        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
                        return scrollPane;
                    }
                };
            }

        });

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
        App.eventManager.addColorListener(this);
        updateColor();
    }

    @Override
    public void updateColor() {
        bg = ColorManager.TEXT_EDIT_BACKGROUND;
        text = ColorManager.TEXT;
        this.setForeground(ColorManager.TEXT);
        this.setBackground(ColorManager.TEXT_EDIT_BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
    }
}
