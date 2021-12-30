package com.slimtrade.gui.history;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class HistoryTableModel extends AbstractTableModel {

    private String[] columnNames = new String[0];
    private ArrayList<HistoryRowData> data = new ArrayList<>();

    /**
     * Table model for the history window.
     * @param columnNames
     * @param data
     */
    public HistoryTableModel(String[] columnNames, ArrayList<HistoryRowData> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        if(data == null) return 0;
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

    public void removeRow(int index){
        data.remove(0);
    }

    public void addRow(HistoryRowData rowData){
        data.add(rowData);
    }

    public void setRowData(ArrayList<HistoryRowData> data){
        this.data = data;
    }

//    public void addRow(HistoryRowData rowData) {
//        int fieldCount =  HistoryRowData.class.getFields().length;
//        Object[] obj = new Object[fieldCount];
//        for(int i =0;i<fieldCount;i++){
//            try {
//                obj[i] = HistoryRowData.class.getFields()[i].get(rowData);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//        }
//        addRow(obj);
//    }


//    public void addRow(HistoryRowData row){
//        data.add(row);
//    }
//
//    public void removeRow(int index){
//        data.remove(index);
//    }

}
