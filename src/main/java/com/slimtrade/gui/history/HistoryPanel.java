package com.slimtrade.gui.history;

import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel {

    ArrayList<HistoryRowData> data = new ArrayList();
    public static int maxMessageCount = 50;
    private HistoryTable table;

    JButton reloadButton = new JButton("Open Selected Message");

    public HistoryPanel() {
        String[] columnNames = new String[]{"Date", "Time", "Player", "Item", "Price"};

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        int inset = 2;
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(inset, 0, inset, inset);
        buttonPanel.add(reloadButton, gc);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        setLayout(new BorderLayout());
        table = new HistoryTable(columnNames, data);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        addListeners();
    }

    private void addListeners() {
        reloadButton.addActionListener(e -> refreshSelectedTrade());
    }

    public void reloadUI() {
//        clearAllRows();
        table.getHistoryTableModel().setRowData(data);
        table.getHistoryTableModel().fireTableDataChanged();
    }

    public void preloadRow() {

    }

    public void addRow(TradeOffer tradeOffer) {
        addRow(tradeOffer, true);
    }

    public void addRow(TradeOffer tradeOffer, boolean updateUI) {
        if (data.size() >= maxMessageCount) {
            data.remove(0);
        }
        HistoryRowData rowData = new HistoryRowData(tradeOffer);
        data.add(rowData);
        if (updateUI) {
            table.getHistoryTableModel().fireTableDataChanged();
        }
    }

    public void clearAllRows() {
        data.clear();
    }

    private void refreshSelectedTrade() {
        int index = table.getSelectedRow();
        if (index == -1 || index >= data.size()) return;
        TradeOffer trade = data.get(index).tradeOffer;
        FrameManager.messageManager.addMessage(trade, false);
    }

}
