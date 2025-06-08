package github.zmilla93.gui.windows;

import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.CardPanel;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.history.HistoryPanel;
import github.zmilla93.gui.listening.IDefaultSizeAndLocation;

import javax.swing.*;
import java.awt.*;

public class HistoryWindow extends CustomDialog implements IDefaultSizeAndLocation {

    public final HistoryPanel incomingTradesPoe1 = new HistoryPanel(Game.PATH_OF_EXILE_1, TradeOfferType.INCOMING_TRADE);
    public final HistoryPanel incomingTradesPoe2 = new HistoryPanel(Game.PATH_OF_EXILE_2, TradeOfferType.INCOMING_TRADE);
    public final HistoryPanel outgoingTradesPoe1 = new HistoryPanel(Game.PATH_OF_EXILE_1, TradeOfferType.OUTGOING_TRADE);
    public final HistoryPanel outgoingTradesPoe2 = new HistoryPanel(Game.PATH_OF_EXILE_2, TradeOfferType.OUTGOING_TRADE);
    private final CardPanel cardPanel = new CardPanel();
    private final JTabbedPane poe1TabbedPanel = new JTabbedPane();
    private final JTabbedPane poe2TabbedPanel = new JTabbedPane();
    //    private final JButton settingsButton = new IconButton(DefaultIcon.COG);
    private final JButton toggleGameButton = new IconButton(DefaultIcon.ARROW_SYNC);
    private final JLabel gameLabel = new JLabel("Path of Exile 1");
    private final JButton openMessageButton = new JButton("Open Selected Message");

    public HistoryWindow() {
        super("History");
        poe1TabbedPanel.addTab("Incoming Trades", incomingTradesPoe1);
        poe1TabbedPanel.addTab("Outgoing Trades", outgoingTradesPoe1);
        poe2TabbedPanel.addTab("Incoming Trades", incomingTradesPoe2);
        poe2TabbedPanel.addTab("Outgoing Trades", outgoingTradesPoe2);
        cardPanel.add(poe1TabbedPanel);
        cardPanel.add(poe2TabbedPanel);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(new ComponentPanel(0, toggleGameButton, Box.createHorizontalStrut(2), gameLabel), BorderLayout.WEST);
        buttonPanel.add(openMessageButton, BorderLayout.EAST);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setMinimumSize(new Dimension(300, 200));
        pack();

        openMessageButton.addActionListener(e -> {
            JTabbedPane tabbedPane = poe1TabbedPanel.isVisible() ? poe1TabbedPanel : poe2TabbedPanel;
            HistoryPanel panel = (HistoryPanel) tabbedPane.getSelectedComponent();
            panel.refreshSelectedTrade();
        });
        toggleGameButton.addActionListener(e -> {
            if (poe1TabbedPanel.isVisible()) {
                cardPanel.showCard(poe2TabbedPanel);
                poe2TabbedPanel.setSelectedIndex(poe1TabbedPanel.getSelectedIndex());
                gameLabel.setText(Game.PATH_OF_EXILE_2.explicitName);
            } else {
                cardPanel.showCard(poe1TabbedPanel);
                poe1TabbedPanel.setSelectedIndex(poe2TabbedPanel.getSelectedIndex());
                gameLabel.setText(Game.PATH_OF_EXILE_1.explicitName);
            }
        });
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setSize(600, 400);
        POEWindow.centerWindow(this);
    }

}
