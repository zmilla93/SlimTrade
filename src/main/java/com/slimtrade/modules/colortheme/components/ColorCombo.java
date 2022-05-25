package com.slimtrade.modules.colortheme.components;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.options.stash.StashTabCellRenderer;

import java.awt.*;

public class ColorCombo extends LimitCombo<Color> {

    public ColorCombo() {
        setRenderer(new StashTabCellRenderer());
        addActionListener(e -> {
            Color color = (Color) getSelectedItem();
            setBackground(color);
        });
        for (StashTabColor stashColor : StashTabColor.values()) addItem(stashColor.getBackground());
    }

}
