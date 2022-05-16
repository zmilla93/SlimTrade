package com.slimtrade.gui.stash;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {

    enum GridType {None, Normal, Quad}

    public GridPanel() {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(Color.GREEN);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        g.drawLine(0, 0, getWidth() - 1, getHeight() - 1);

        int lineCount = 12;
        int spacing = Math.round(getWidth() / (float) lineCount);
        for (int i = 1; i < lineCount; i++) {
            float mult = i / (float) lineCount;
            int x = Math.round(getWidth() * mult);
            int y = Math.round(getHeight() * mult);
//            System.out.println(x);
            g.drawLine(x, 0, x, getHeight());
            g.drawLine(0, y, getWidth(), y);
        }
//        for (int i = 1; i < lineCount; i++) {
//            float mult = i / (float) lineCount;
//            int x = Math.round(getWidth() * mult);
//            System.out.println(x);
//            g.drawLine(x, 0, x, getHeight());
//        }

    }
}
