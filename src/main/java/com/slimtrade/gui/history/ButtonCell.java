package com.slimtrade.gui.history;

import com.slimtrade.gui.buttons.IconButton;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonCell extends IconButton implements TableCellRenderer {

    public ButtonCell(){
//        System.out.println("BUTTON CELL RENDERER");
//        setText("WOW DUDE");
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
