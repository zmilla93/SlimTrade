package com.slimtrade.gui.managers;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.messaging.TradeMessagePanel;

import javax.swing.*;
import java.awt.*;

public class MessageManager extends JFrame {

    public Point anchorPoint = new Point(800, 0);
    boolean expandUp = false;


    private int MESSAGE_GAP = 2;
    private Container container;

    private GridBagConstraints gc;


    public MessageManager() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
        setBackground(new Color(0, 0, 0, 0));

        container = getContentPane();
        setLocationRelativeTo(null);
//        WindowUtils.setWindowTransparent(this, true);

        container.setLayout(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        setLocation(anchorPoint);
        pack();
    }

    public void addMessage(TradeOffer tradeOffer) {
        assert (SwingUtilities.isEventDispatchThread());
        gc.insets = new Insets(0, 0, MESSAGE_GAP, 0);
        gc.gridy = container.getComponentCount();
        JPanel panel = new TradeMessagePanel(tradeOffer);
        container.add(panel, gc);
        pack();
        adjustPosition();
        repaint();
    }

    public void removeMessage(JPanel panel) {
        assert (SwingUtilities.isEventDispatchThread());
        container.remove(panel);
        pack();
        adjustPosition();
        container.revalidate();
    }

    private void adjustPosition() {
        Point p = new Point(anchorPoint);
        if (expandUp) {
            p.y -= getHeight();
            setLocation(p);
        } else {
            setLocation(p);
        }
    }
}