package com.slimtrade.gui.history;

import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.ArrayList;

public class HistoryTable extends JTable {

    private HistoryTableModel historyTableModel;

    public HistoryTable(String[] columnNames, ArrayList<HistoryRowData> data) {
//        super(historyTableModel = new HistoryTableModel(columnNames, data));


        historyTableModel = new HistoryTableModel(columnNames, data);
        setModel(historyTableModel);

        // Adjust table columns
//        autoResizeMode = AUTO_RESIZE_ALL_COLUMNS;
        TableColumn column = null;
        for (int i = 0; i < columnNames.length; i++) {
            column = tableHeader.getColumnModel().getColumn(i);
            TableColumnModel columnModel = tableHeader.getColumnModel();
            if (i < 2) {
//                columnModel.getColumn(i).setPreferredWidth((int) (getPreferredSize().width * 0.2f));
                column.setPreferredWidth(20);
//                column.setPreferredWidth((int) (column.getPreferredWidth() * 0.1f));
//                column.setPreferredWidth(0);
            }
//            columnModel.getColumn(i).setPreferredWidth((int) (getPreferredSize().width * 0.2f));


            tableHeader.setColumnModel(columnModel);
//            column.setPreferredWidth(20 * i);
        }

        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setDefaultRenderer(IconButton.class, new ButtonCell());
        setDefaultRenderer(double.class, new ButtonCell());
        DefaultTableCellRenderer stringRenderer = new DefaultTableCellRenderer();
        stringRenderer.setHorizontalAlignment(JLabel.CENTER);
        setDefaultRenderer(String.class, stringRenderer);


//        setDefaultRenderer(Double.class, new ButtonCell());
//        setDefaultRenderer(String.class, new ButtonCell());
//        setAutoCreateRowSorter(true);
//        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setEnabled(false);
    }

    public HistoryTableModel getHistoryTableModel() {
        return historyTableModel;
    }

}
