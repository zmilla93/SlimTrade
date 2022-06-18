package com.slimtrade.gui.history;

import com.slimtrade.core.data.SaleItemWrapper;
import com.slimtrade.gui.components.LabelHolder;
import com.slimtrade.gui.components.CurrencyLabelFactory;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ItemCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JPanel panel = new LabelHolder();
        if (value instanceof SaleItemWrapper) {
            CurrencyLabelFactory.applyItemToComponent(panel, ((SaleItemWrapper) value).items);
        }
        if (isSelected) {
            panel.setBackground(UIManager.getColor("Table.selectionInactiveBackground"));
            panel.setForeground(UIManager.getColor("Table.selectionInactiveForeground"));
        } else {
            panel.setBackground(UIManager.getColor("Table.background"));
        }
        return panel;
    }

}