package com.slimtrade.modules.theme.components;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.gui.options.stash.StashTabCellRenderer;

import javax.swing.*;
import java.awt.*;

public class ColorCombo extends JComboBox<Color> {

    /**
     * A ComboBox that displays POE stash colors.
     */
    public ColorCombo() {
        setRenderer(new StashTabCellRenderer());
        addActionListener(e -> {
            Color color = (Color) getSelectedItem();
            setBackground(color);
        });
        for (StashTabColor stashColor : StashTabColor.values()) addItem(stashColor.getBackground());
    }

}
