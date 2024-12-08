package com.slimtrade.gui.windows.test;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.TradeMessagePanel;
import com.slimtrade.modules.theme.Theme;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * A window that creates a TradeMessagePanel using every color theme.
 * WARNING: This can take a little while to load.
 */
public class MessageTestWindow extends JFrame {

    public MessageTestWindow() {
        setTitle("SlimTrade - Notification Panel Theme Test");
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Messages", getTradeMessagePanel(false));
        tabbedPane.addTab("Messages (Joined)", getTradeMessagePanel(true));

        setContentPane(tabbedPane);
        pack();
        setSize(new Dimension(1800, 800));
        setVisible(true);
    }

    private JPanel getTradeMessagePanel(boolean playerJoinedArea) {
        ThemeManager.setTheme(Theme.SOLARIZED_LIGHT);
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (Theme theme : Theme.values()) {
            ThemeManager.setTheme(theme);
            JPanel msgPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 10));
            NotificationPanel panel1 = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false);
            NotificationPanel panel2 = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false);
            if (playerJoinedArea) {
                panel1.setPlayerJoinedArea();
                panel2.setPlayerJoinedArea();
            }
            msgPanel.add(panel1);
            msgPanel.add(panel2);
            mainPanel.add(msgPanel);
        }
        return mainPanel;
    }

}
