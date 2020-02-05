package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public class CustomComboRenderer extends JLabel implements ListCellRenderer {

    public CustomComboRenderer() {
        setOpaque(true);
        setForeground(Color.ORANGE);
        setBackground(Color.GREEN);
        setBorder(null);
    }

//    @Override
//    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//        setText(value.toString());
//        return this;
//    }

    @Override
    protected void paintComponent(Graphics g) {
        final Graphics2D g2 = (Graphics2D) g.create();
//        ComboBoxModel model = this.getModel();
        Color primaryColor = ColorManager.PRIMARY;
        Color secondaryColor = Color.BLACK;
        g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), primaryColor));
        //BORDER
//                if(model.isRollover()){
//                    this.setBorder(borderRollover);
//                }else{
//                    this.setBorder(borderDefault);
//                }
        //FILL
//                if(model.isPressed() && model.isRollover()){
//                    g2.setPaint(primaryColor);
//                }else if(model.isPressed() && !model.isRollover()){
//                    g2.setPaint(Color.LIGHT_GRAY);
//                }else{
//
//                }c
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        return null;
    }

//    @Override
//    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus) {
////        Component c = this.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
//        Component c;
//        if (isSelected) {
//            if (Boolean.TRUE.equals(value)) {
////                JLabel l = (JLabel) c;
////                l.setBackground(Color.BLUE);
//            }
////            c.setBackground(Color.ORANGE);
////                    c.setBackground(Color.PINK);
//            list.setSelectionBackground(Color.PINK);
//            list.setSelectionForeground(Color.BLUE);
//            list.setBorder(null);
//        } else {
////            c.setBackground(Color.BLUE);
//            list.setSelectionBackground(Color.CYAN);
//            list.setSelectionForeground(Color.BLUE);
//            list.setBorder(null);
//        }
//        return this;
//
////        return c;
//    }
}



