package com.slimtrade.gui.options.stash;

import javax.swing.*;
import java.awt.*;

public class StashTabCellRenderer extends JPanel implements ListCellRenderer<Object> {

    private static final long serialVersionUID = 1L;

    public StashTabCellRenderer() {
        this.setPreferredSize(new Dimension(30, 20));
        setOpaque(true);
    }

    boolean active = false;

    public void setBackground(Color bg) {
        if (!active) {
            return;
        }
        super.setBackground(bg);
    }

    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        active = true;
        if (isSelected) {
            this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
        } else {
            this.setBorder(BorderFactory.createEmptyBorder());
        }
        setBackground((Color) value);
        active = false;
        return this;
    }
}