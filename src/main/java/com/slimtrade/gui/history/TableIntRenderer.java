package com.slimtrade.gui.history;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableIntRenderer extends JButton implements TableCellRenderer {

    public TableIntRenderer() {
        setText("TABLEINTRENDERER");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
