package com.slimtrade.gui.options.searching;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashSearchTermPanel extends AddRemovePanel {

    private final JButton removeButton = new IconButton(DefaultIcon.CLOSE);
    private final JButton shiftUpButton = new IconButton(DefaultIcon.ARROW_UP);
    private final JButton shiftDownButton = new IconButton(DefaultIcon.ARROW_DOWN);

    private final JTextField titleInput = new PlaceholderTextField("Display Title...", 15);
    private final JTextField searchInput = new PlaceholderTextField("Search Term...", 20);
    private final ColorCombo colorCombo = new ColorCombo();

    public StashSearchTermPanel(AddRemoveContainer<StashSearchTermPanel> parent) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(removeButton);
        add(shiftDownButton);
        add(shiftUpButton);
        add(titleInput);
        add(searchInput);
        add(colorCombo);

        addListeners();
    }

    public StashSearchTermPanel(AddRemoveContainer<StashSearchTermPanel> parent, StashSearchTermData data) {
        this(parent);
        titleInput.setText(data.title);
        searchInput.setText(data.searchTerm);
        colorCombo.setSelectedIndex(data.colorIndex);
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
