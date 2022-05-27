package com.slimtrade.gui.windows;

import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.history.HistoryPanel;

import javax.swing.*;
import java.awt.*;

public class HistoryWindow extends CustomDialog implements ITradeListener, IParserLoadedListener {

    HistoryPanel incomingTrades = new HistoryPanel();
    HistoryPanel outgoingTrades = new HistoryPanel();
    HistoryPanel chatScanner = new HistoryPanel();
    private boolean loaded;

    public HistoryWindow() {
        super("History");
        setFocusable(false);
        setFocusableWindowState(false);
        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Incoming Trades", incomingTrades);
        tabbedPane.addTab("Outgoing Trades", outgoingTrades);
//        tabbedPane.addTab("Chat Scanner", chatScanner);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        pack();
        setSize(600, 400);
        setMinimumSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
        //
        if (tradeOffer == null) return;
        SwingUtilities.invokeLater(() -> {
            addTradeToPanel(tradeOffer, loaded);
        });
    }

    private void addTradeToPanel(TradeOffer tradeOffer, boolean updateUI) {
        if (tradeOffer == null) return;
        switch (tradeOffer.offerType) {
            case INCOMING:
                incomingTrades.addRow(tradeOffer, updateUI);
                break;
            case OUTGOING:
                outgoingTrades.addRow(tradeOffer, updateUI);
                break;
        }
    }

    @Override
    public void onParserLoaded() {
        SwingUtilities.invokeLater(() -> {
            loaded = true;
            incomingTrades.reloadUI();
            outgoingTrades.reloadUI();
        });
    }

}
