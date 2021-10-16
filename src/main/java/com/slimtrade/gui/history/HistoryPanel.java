package com.slimtrade.gui.history;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.options.OrderType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel implements IColorable {

    private static final long serialVersionUID = 1L;
    // private TradeOffer savedTrade;

    private ArrayList<TradeOffer> trades = new ArrayList<TradeOffer>();
    private ArrayList<HistoryRow> tradePanels = new ArrayList<HistoryRow>();


    private JPanel contentPanel;

//	private static int maxTrades = 10;

    private boolean close = false;

    HistoryPanel() {
        assert(SwingUtilities.isEventDispatchThread());
        this.setLayout(new BorderLayout());
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        this.add(contentPanel, BorderLayout.CENTER);
    }

    public void addTrade(TradeOffer trade, boolean updateUI) {
        assert(SwingUtilities.isEventDispatchThread());
        int i = 0;
        // Delete old duplicate
        for (TradeOffer savedTrade : trades) {
            if (TradeUtility.isMatchingTrades(trade, savedTrade)) {
                trades.remove(i);
                if (updateUI) {
                    contentPanel.remove(tradePanels.get(i));
                    tradePanels.remove(i);
                }
                break;
            }
            i++;
        }
        // Delete oldest trade if at max trades
        if (trades.size() >= App.saveManager.settingsSaveFile.historyLimit && App.saveManager.settingsSaveFile.historyLimit > 0) {
            trades.remove(0);
            if (updateUI) {
                contentPanel.remove(tradePanels.get(0));
                tradePanels.remove(0);
            }
        }
        // Add new trade
        trades.add(trade);
        if (updateUI) {
            HistoryRow row = new HistoryRow(trade, close);
            tradePanels.add(row);
            if (HistoryWindow.orderType == OrderType.NEW_FIRST) {
                contentPanel.add(tradePanels.get(tradePanels.size() - 1), 0);
            } else {
                contentPanel.add(tradePanels.get(tradePanels.size() - 1));
            }
            ColorManager.recursiveColor(row);
            this.revalidate();
            this.repaint();
        }
    }

    public void initUI() {
        assert(SwingUtilities.isEventDispatchThread());
        for (TradeOffer trade : trades) {
            HistoryRow row = new HistoryRow(trade, close);
            if (HistoryWindow.orderType == OrderType.NEW_FIRST) {
                contentPanel.add(row, 0);
            } else {
                contentPanel.add(row);
            }
            tradePanels.add(row);
        }
        ColorManager.recursiveColor(this);
//		App.logger.log(Level.INFO, "HISTORY BUILD TIME : " + Debugger.benchmark());
        this.revalidate();
        this.repaint();
    }

    public void refreshOrder() {
        assert(SwingUtilities.isEventDispatchThread());
        if (HistoryWindow.orderType == OrderType.NEW_FIRST) {
            for (HistoryRow row : tradePanels) {
                contentPanel.add(row, 0);
            }
        } else {
            for (HistoryRow row : tradePanels) {
                contentPanel.add(row);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void clearTrades() {
        assert(SwingUtilities.isEventDispatchThread());
        contentPanel.removeAll();
        trades.clear();
        tradePanels.clear();
    }

    public void updateDate() {
        assert(SwingUtilities.isEventDispatchThread());
        for (HistoryRow row : tradePanels) {
            row.updateDate();
        }
    }

    public void updateTime() {
        assert(SwingUtilities.isEventDispatchThread());
        for (HistoryRow row : tradePanels) {
            row.updateTime();
        }
    }

    public void setClose(boolean close) {
        this.close = close;
    }


    @Override
    public void updateColor() {
        contentPanel.setBackground(ColorManager.BACKGROUND);
    }
}
