package com.slimtrade.gui.options.searching;

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

    private final JTextField titleInput = new PlaceholderTextField("Display Title...", 10);
    private final JTextField searchInput = new PlaceholderTextField("Search Term...", 20);
    private final ColorCombo colorCombo = new ColorCombo();

    public StashSortingTermPanel(AddRemoveContainer<StashSortingTermPanel> parent) {
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

    public StashSortingTermPanel(AddRemoveContainer<StashSortingTermPanel> parent, StashSearchTermData data) {
        this(parent);
        titleInput.setText(data.title());
        searchInput.setText(data.searchTerm());
        colorCombo.setSelectedIndex(data.colorIndex());
    }

    private void addListeners() {
        shiftUpButton.addActionListener(e -> shiftUp(shiftUpButton));
        shiftDownButton.addActionListener(e -> shiftDown(shiftDownButton));
        removeButton.addActionListener(e -> removeFromParent());
    }

    public StashSearchTermData getData() {
        return new StashSearchTermData(titleInput.getText(), searchInput.getText(), colorCombo.getSelectedIndex());
    }

}
