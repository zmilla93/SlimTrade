package com.slimtrade.gui.windows;

import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.history.HistoryPanel;

import javax.swing.*;

public class HistoryWindow extends AbstractWindow implements ITradeListener, IParserLoadedListener {

    HistoryPanel incomingTrades = new HistoryPanel();
    HistoryPanel outgoingTrades = new HistoryPanel();
    HistoryPanel chatScanner = new HistoryPanel();
    private boolean loaded;

    public HistoryWindow() {
        super("History");

        JTabbedPane tabbedPane = new JTabbedPane();

//        JPanel p1 = new HistoryPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();

        tabbedPane.addTab("Incoming Trades", incomingTrades);
        tabbedPane.addTab("Outgoing Trades", outgoingTrades);
        tabbedPane.addTab("Chat Scanner", chatScanner);

        container.add(tabbedPane);

        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
    }

//    @Override
//    public void handlePreloadTrade(TradeOffer tradeOffer) {
//        if (tradeOffer == null) return;
//        addTradeToPanel(tradeOffer, false);
//    }

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
