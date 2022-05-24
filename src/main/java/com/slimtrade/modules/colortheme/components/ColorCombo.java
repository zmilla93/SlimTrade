package com.slimtrade.modules.colortheme.components;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.options.stash.StashTabCellRenderer;

import java.awt.*;

public class ColorCombo extends LimitCombo<Color> {

    public ColorCombo() {
        for (StashTabColor stashColor : StashTabColor.values()) addItem(stashColor.getBackground());
        setRenderer(new StashTabCellRenderer());
        addActionListener(e -> {
            Color color = (Color) getSelectedItem();
            setBackground(color);
        });
    }

    @Override
    public void addItem(Color item) {
        super.addItem(item);
    }
}
