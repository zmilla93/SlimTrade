package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class CustomCombo<E> extends JComboBox<E> {

    protected void paintComponent(Graphics g) {
//        final Graphics2D g2 = (Graphics2D) g.create();

        // Selection box
        this.setBackground(ColorManager.BACKGROUND);
        this.setForeground(ColorManager.TEXT);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
//        g2.setPaint(Color.BLUE);
//        g2.fillRect(0, 0, getWidth(), getHeight());
//        g2.dispose();
        super.paintComponent(g);
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
//
//    }

}
