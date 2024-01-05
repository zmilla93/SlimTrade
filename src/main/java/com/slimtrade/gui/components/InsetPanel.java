package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class InsetPanel extends JPanel {

    public JPanel contentPanel = new JPanel();

    public InsetPanel(Insets insets) {
        setLayout(new BorderLayout());
        addBufferPanel(BorderLayout.NORTH, insets.top);
        addBufferPanel(BorderLayout.SOUTH, insets.bottom);
        addBufferPanel(BorderLayout.EAST, insets.right);
        addBufferPanel(BorderLayout.WEST, insets.left);
        add(contentPanel, BorderLayout.CENTER);
        setOpaque(false);
    }

    private void addBufferPanel(String direction, int size) {
        if (size <= 0) return;
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        if (direction.equals(BorderLayout.NORTH) || direction.equals(BorderLayout.SOUTH)) {
            panel.add(Box.createVerticalStrut(size), BorderLayout.CENTER);
        } else {
            panel.add(Box.createHorizontalStrut(size));
        }
        add(panel, direction);
    }

    public void setBorderBackground(Color color) {
        for (Component c : getComponents()) {
//            if(c instanceof JPanel){
//                ((JPanel) c).setOpaque(true);
//            }
            c.setBackground(color);
        }
    }

}
