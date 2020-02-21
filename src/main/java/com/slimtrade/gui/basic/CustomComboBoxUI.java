package com.slimtrade.gui.basic;

import com.slimtrade.gui.buttons.CustomArrowButton;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class CustomComboBoxUI extends BasicComboBoxUI {

    public  CustomComboBoxUI() {
        super();
    }

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

}
