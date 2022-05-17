package com.slimtrade.gui.windows;

import com.slimtrade.core.chatparser.IChatParserLoadedListener;
import com.slimtrade.core.chatparser.IPreloadTradeListener;
import com.slimtrade.core.chatparser.ITradeListener;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.history.HistoryPanel;

import javax.swing.*;

public class HistoryWindow extends AbstractWindow implements IPreloadTradeListener, ITradeListener, IChatParserLoadedListener {

    HistoryPanel incomingTrades = new HistoryPanel();
    HistoryPanel outgoingTrades = new HistoryPanel();
    HistoryPanel chatScanner = new HistoryPanel();

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

    @Override
    public void handlePreloadTrade(TradeOffer tradeOffer) {
//        incomingTrades.addRow(tradeOffer, false);
        addTradeToPanel(tradeOffer, false);
    }

    @Override
    public void handleTrade(TradeOffer tradeOffer) {
//        SwingUtilities.invokeLater();
        SwingUtilities.invokeLater(() -> {
            addTradeToPanel(tradeOffer, true);
//            switch (tradeOffer.offerType) {
//                case INCOMING:
//                    incomingTrades.addRow(tradeOffer, true);
//                    break;
//                case OUTGOING:
//                    outgoingTrades.addRow(tradeOffer, true);
//            }
        });
    }

    private void addTradeToPanel(TradeOffer tradeOffer, boolean updateUI) {
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
    public void handleChatParserLoaded() {
        SwingUtilities.invokeLater(() -> {
            incomingTrades.reloadUI();
            outgoingTrades.reloadUI();
        });
    }
}
