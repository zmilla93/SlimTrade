package com.slimtrade.gui.windows;

import com.slimtrade.core.chatparser.IParserInitListener;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.history.HistoryPanel;

import javax.swing.*;
import java.awt.*;

public class HistoryWindow extends CustomDialog implements ITradeListener, IParserInitListener, IParserLoadedListener {

    private final HistoryPanel incomingTrades = new HistoryPanel();
    private final HistoryPanel outgoingTrades = new HistoryPanel();
    private boolean loaded;

    public HistoryWindow() {
        super("History");
        setFocusable(false);
        setFocusableWindowState(false);
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Incoming Trades", incomingTrades);
        tabbedPane.addTab("Outgoing Trades", outgoingTrades);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        pack();
        setSize(600, 400);
        setMinimumSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
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
    public void handleTrade(TradeOffer tradeOffer) {
        if (tradeOffer == null) return;
        SwingUtilities.invokeLater(() -> addTradeToPanel(tradeOffer, loaded));
    }

    @Override
    public void onParserInit() {
        // FIXME : setting loaded to false causes a warning when changing client.txt path
//        loaded = false;
        clearHistory();
    }

    @Override
    public void onParserLoaded() {
        loaded = true;
        SwingUtilities.invokeLater(() -> {
            incomingTrades.reloadUI();
            outgoingTrades.reloadUI();
        });
    }

}
