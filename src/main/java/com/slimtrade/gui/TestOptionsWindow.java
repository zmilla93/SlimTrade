package com.slimtrade.gui;

import javax.swing.*;
import java.awt.*;

public class TestOptionsWindow extends JFrame {

    public TestOptionsWindow(){
        super("Options");
        setSize(600,600);
        Container container = getContentPane();

        String[] tabs = new String[]{"General", "Stash Tabs", "Macros"};
        JList<String> list = new JList<>(tabs);

        container.add(list);

        JPanel selectionPanel = new JPanel();

//        list.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                System.out.println("WWW:" + list.getSelectedValue());
//            }
//        });

    }

}
