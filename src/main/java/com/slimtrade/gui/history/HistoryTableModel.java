package com.slimtrade.gui.history;

import com.slimtrade.core.enums.HistoryOrder;
import com.slimtrade.core.managers.SaveManager;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class HistoryTableModel extends AbstractTableModel {

    private final String[] columnNames;
    private final ArrayList<HistoryRowData> data;

    /**
     * Table model for the history window.
     *
     * @param columnNames
     * @param data
     */
    public HistoryTableModel(String[] columnNames, ArrayList<HistoryRowData> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        if (data == null) return 0;
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Field field = HistoryRowData.class.getFields()[columnIndex];
        if (SaveManager.settingsSaveFile.data.historyOrder == HistoryOrder.NEWEST_FIRST)
            rowIndex = data.size() - 1 - rowIndex;
        HistoryRowData row = data.get(rowIndex);
        try {
            return field.get(row);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return HistoryRowData.class.getFields()[columnIndex].getType();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

//    public void setRowData(ArrayList<HistoryRowData> data) {
//        this.data = data;
//    }


}
