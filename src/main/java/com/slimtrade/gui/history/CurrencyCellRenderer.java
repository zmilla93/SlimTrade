package com.slimtrade.gui.history;

import com.slimtrade.gui.components.CurrencyLabelFactory;
import com.slimtrade.modules.theme.components.PassThroughPanel;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class CurrencyCellRenderer implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof PoePrice) {
            PassThroughPanel panel = new PassThroughPanel();
            CurrencyLabelFactory.applyPOEPriceToComponent(panel, (PoePrice) value);
            if (isSelected) {
                panel.setBackground(UIManager.getColor("Table.selectionInactiveBackground"));
                panel.setForeground(UIManager.getColor("Table.selectionInactiveForeground"));
            } else {
                panel.setBackground(UIManager.getColor("Table.background"));
            }
            return panel;
        }
        return null;
    }

}
