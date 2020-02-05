package com.slimtrade.gui.options.general;

import javax.swing.*;
import java.awt.*;

public class CustomComboBox extends JComboBox {

    public CustomComboBox() {

//        @Override
        this.setRenderer(new DefaultListCellRenderer(){

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean hasFocus){
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, hasFocus);
                c.setBackground(Color.ORANGE);
                return c;
            }

        });


    }



}
