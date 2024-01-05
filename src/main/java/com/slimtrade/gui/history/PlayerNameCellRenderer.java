package com.slimtrade.gui.history;

import com.slimtrade.core.managers.FontManager;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class PlayerNameCellRenderer extends JLabel implements TableCellRenderer {

    public PlayerNameCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }
        PlayerNameWrapper playerNameWrapper = (PlayerNameWrapper) value;
        setText(playerNameWrapper.playerName);
        FontManager.applyFont(this);
        return this;
    }

}
