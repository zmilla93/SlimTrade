package github.zmilla93.modules.theme.components;

import github.zmilla93.core.enums.StashTabColor;
import github.zmilla93.gui.options.stash.StashTabCellRenderer;

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
