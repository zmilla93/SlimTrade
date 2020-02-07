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
        App.eventManager.addListener(this);
        updateColor();
    }

    protected void paintComponent(Graphics g) {
        this.setOpaque(false);


        updateColor();
//        this.setPreferredSize(null);
//        this.setPreferredSize(new Dimension(this.getPreferredSize().windowWidth + 10, this.getPreferredSize().windowHeight + 2));
        super.paintComponent(g);
    }

    @Override
    public void updateColor() {
        bg = ColorManager.BACKGROUND;
        text = ColorManager.TEXT;
        this.setForeground(ColorManager.TEXT);
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
//        this.setPreferredSize(null);
//        this.setPreferredSize(new Dimension(this.getPreferredSize().windowWidth + 10, this.getPreferredSize().windowHeight + 2));
    }

//    // HIDE BUTTON
//    public void updateUI() {
//        super.updateUI();
//        UIManager.put("ComboBox.squareButton", false);
//        setUI(new BasicComboBoxUI(){
//            @Override
//            protected JButton createArrowButton() {
//                return new JButton(){
//
//                    @Override
//                    public int getWidth() {
//                        return 0;
//                    }
//
//                };
//            }
//        });
//    }

}
