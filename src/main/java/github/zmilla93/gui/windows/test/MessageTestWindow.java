package github.zmilla93.gui.windows.test;

import github.zmilla93.core.trading.TradeOffer;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CustomScrollPane;
import github.zmilla93.gui.messaging.NotificationPanel;
import github.zmilla93.gui.messaging.TradeMessagePanel;
import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

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
        tabbedPane.addTab("Messages", new CustomScrollPane(getTradeMessagePanel(false)));
        tabbedPane.addTab("Messages (Joined)", new CustomScrollPane(getTradeMessagePanel(true)));

        setContentPane(tabbedPane);
        pack();
        setSize(new Dimension(getWidth(), 800));
        setVisible(true);
    }

    private JPanel getTradeMessagePanel(boolean playerJoinedArea) {
        Theme currentTheme = ThemeManager.getCurrentTheme();
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        boolean columnFlag = false;
        for (Theme theme : Theme.values()) {
            // FIXME : Using ThemeManager to change theme is much more expensive, but produces correct colors for icons
//            ThemeManager.setTheme(theme);
            try {
                UIManager.setLookAndFeel(theme.lookAndFeel);
            } catch (UnsupportedLookAndFeelException e) {
                continue;
            }

            JPanel msgPanel = new JPanel(new BorderLayout());
            msgPanel.setBorder(BorderFactory.createTitledBorder(theme.toString()));
            NotificationPanel panel1 = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.INCOMING_TRADE), false);
            NotificationPanel panel2 = new TradeMessagePanel(TradeOffer.getExampleTrade(TradeOfferType.OUTGOING_TRADE), false);
            if (playerJoinedArea) {
                panel1.setPlayerJoinedArea();
                panel2.setPlayerJoinedArea();
            }
            msgPanel.add(panel1, BorderLayout.WEST);
            msgPanel.add(panel2, BorderLayout.EAST);
            mainPanel.add(msgPanel, gc);
            if (columnFlag) {
                gc.gridx = 0;
                gc.gridy++;
            } else {
                gc.gridx++;
            }
            columnFlag = !columnFlag;
        }
        ThemeManager.setTheme(currentTheme);
//        try {
//            UIManager.setLookAndFeel(currentTheme.lookAndFeel);
//        } catch (UnsupportedLookAndFeelException ignore) {
//        }
        return mainPanel;
    }

}
