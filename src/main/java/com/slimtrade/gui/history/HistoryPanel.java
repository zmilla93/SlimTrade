package com.slimtrade.gui.history;

import com.slimtrade.core.trading.TradeOffer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryPanel extends JPanel {

    JPanel contentPanel = new JPanel(new BorderLayout());
    ArrayList<HistoryRowData> data = new ArrayList();
    public static int maxMessageCount = 50;
    private HistoryTable table;

    public HistoryPanel() {


        // Dummy Data
        String[] columnNames = new String[]{"Date", "Time", "Player", "Item", "Price"};
        TradeOffer t1 = new TradeOffer();
        t1.offerType = TradeOffer.TradeOfferType.INCOMING;
        t1.time = "10:30 PM";
        t1.date = "10/3/20";
        t1.playerName = "Haksalaow";
        t1.itemName = "Tabula";
        t1.priceTypeString = "5C";
        t1.priceQuantity = 20.0f;

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton reloadButton = new JButton("Reload");
        JButton messageButton = new JButton("Message");
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        buttonPanel.add(reloadButton, gc);
        gc.gridx++;
        buttonPanel.add(messageButton, gc);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        ArrayList<HistoryRowData> testData = new ArrayList<>();
//        HistoryRowData[]{
        testData.add(new HistoryRowData(t1));
        testData.add(new HistoryRowData(t1));
        testData.add(new HistoryRowData(t1));
//            {new IconButton(), "10:30", "10/3/10", "NeatPlayer", "Brambled Jack Carcass", "10 chaos", 4},
//            {new IconButton(), "10:14", "10/3/10", "JimPlayer", "Brambled Jack Carcass", "10 chaos", 3},
//            {new IconButton(), "10:52", "10/3/10", "Weirdness", "Tabula Jack Carcass", "10 chaos", 1},
//            {new IconButton(), "1:30pm", "10/3/10", "cooldude", "Jack Carcass", "10 chaos", 5},


        ;

        setLayout(new BorderLayout());
        table = new HistoryTable(columnNames, testData);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

//        contentPanel.add(table, BorderLayout.CENTER);
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
        boolean remove = false;
        if (data.size() >= maxMessageCount) {
            data.remove(0);
            remove = true;
        }
        HistoryRowData rowData = new HistoryRowData(tradeOffer);
        data.add(rowData);
        if (updateUI) {
            if (remove) table.getHistoryTableModel().removeRow(0);
//            table.getHistoryTableModel().addRow(rowData);
            table.getHistoryTableModel().fireTableDataChanged();
        }
    }

    public void clearAllRows() {
        data.clear();
    }

}
