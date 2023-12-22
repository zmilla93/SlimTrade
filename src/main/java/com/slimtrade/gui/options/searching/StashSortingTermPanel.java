package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashSortingTermPanel extends AddRemovePanel {

    private final JButton removeButton = new IconButton("/icons/default/closex64.png");
    private final JButton shiftUpButton = new IconButton("/icons/default/arrow-upx48.png");
    private final JButton shiftDownButton = new IconButton("/icons/default/arrow-downx48.png");

    private final ColorCombo colorCombo = new ColorCombo();
    private final JTextField titleInput = new PlaceholderTextField("Display Title...", 10);
    private final JTextField searchInput = new PlaceholderTextField("Search Term...", 20);

    public StashSortingTermPanel(AddRemoveContainer parent) {
        super(parent);

        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(removeButton);
        add(shiftDownButton);
        add(shiftUpButton);
        add(titleInput);
        add(searchInput);
        add(colorCombo);

        addListeners();
    }

    private void addListeners() {
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        removeButton.addActionListener(e -> removeFromParent());
    }

}
