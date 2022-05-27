package com.slimtrade.gui.history;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class HistoryTable extends JTable {

    private HistoryTableModel historyTableModel;

    public HistoryTable(String[] columnNames, ArrayList<HistoryRowData> data) {
        historyTableModel = new HistoryTableModel(columnNames, data);
        setModel(historyTableModel);

        // Adjust table columns
        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = tableHeader.getColumnModel().getColumn(i);
            TableColumnModel columnModel = tableHeader.getColumnModel();
            // This feels a bit hacky, but seems to be a simple way to force columns to try and be as small as possible.
            if (columnNames[i].equals("Time") || columnNames[i].equals("Date") || columnNames[i].equals("Price")) {
                column.setPreferredWidth(0);
            }
            tableHeader.setColumnModel(columnModel);
        }

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableCellRenderer stringRenderer = new DefaultTableCellRenderer();
        stringRenderer.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(String.class, stringRenderer);
        getTableHeader().setEnabled(false);
    }

    public HistoryTableModel getHistoryTableModel() {
        return historyTableModel;
    }

}
