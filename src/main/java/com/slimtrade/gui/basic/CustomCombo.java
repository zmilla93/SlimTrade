package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.buttons.CustomArrowButton;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomCombo<E> extends JComboBox<E> implements IColorable {

    private Color bg;
    private Color text;

    public CustomCombo(){
        super();
        this.setOpaque(false);
        this.setFocusable(false);
        this.setUI(new BasicComboBoxUI(){
            @Override

            protected JButton createArrowButton() {
                return new CustomArrowButton(BasicArrowButton.SOUTH);
            }
        });
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                list.setBorder(null);
                if(isSelected){
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
        bg = ColorManager.BACKGROUND;
        text = ColorManager.TEXT;
        this.setForeground(ColorManager.TEXT);
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
    }
}
