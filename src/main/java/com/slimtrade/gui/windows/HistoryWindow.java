package com.slimtrade.gui.windows;

import com.slimtrade.core.chatparser.IParserInitListener;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.chatparser.IPreloadTradeListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.history.HistoryPanel;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;

import javax.swing.*;
import java.awt.*;

public class HistoryWindow extends CustomDialog implements ITradeListener, IPreloadTradeListener, IParserInitListener, IParserLoadedListener, IDefaultSizeAndLocation {

    private final HistoryPanel incomingTrades = new HistoryPanel();
    private final HistoryPanel outgoingTrades = new HistoryPanel();

    public HistoryWindow() {
        super("History");
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Incoming Trades", incomingTrades);
        tabbedPane.addTab("Outgoing Trades", outgoingTrades);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        setMinimumSize(new Dimension(300, 200));
        pack();
    }

    private void addTradeToPanel(TradeOffer tradeOffer, boolean updateUI) {
        if (tradeOffer == null) return;
        switch (tradeOffer.offerType) {
            case INCOMING_TRADE:
                incomingTrades.addRow(tradeOffer, updateUI);
                break;
            case OUTGOING_TRADE:
                outgoingTrades.addRow(tradeOffer, updateUI);
                break;
        }
    }

    private void clearHistory() {
        incomingTrades.clearAllRows();
        outgoingTrades.clearAllRows();
        incomingTrades.reloadUI();
        outgoingTrades.reloadUI();
    }

    @Override
    public void handlePreloadTrade(TradeOffer tradeOffer) {
        SwingUtilities.invokeLater(() -> addTradeToPanel(tradeOffer, false));
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
        SwingUtilities.invokeLater(() -> addTradeToPanel(tradeOffer, true));
    }

    @Override
    public void onParserInit() {
        SwingUtilities.invokeLater(this::clearHistory);
    }

    @Override
    public void onParserLoaded() {
        SwingUtilities.invokeLater(() -> {
            incomingTrades.reloadUI();
            outgoingTrades.reloadUI();
        });
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

}
