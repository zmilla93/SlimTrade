package github.zmilla93.gui.options.searching;

import github.zmilla93.gui.components.AddRemovePanel;
import github.zmilla93.gui.components.PlaceholderTextField;
import github.zmilla93.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

public class StashSearchTermPanel extends AddRemovePanel<StashSearchTermData> {

    private final JTextField titleInput = new PlaceholderTextField("Display Title...", 15);
    private final JTextField searchInput = new PlaceholderTextField("Search Term...", 20);
    private final ColorCombo colorCombo = new ColorCombo();

    public StashSearchTermPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(deleteButton);
        add(dragButton);
        add(titleInput);
        add(searchInput);
        add(colorCombo);
    }

    public StashSearchTermPanel(StashSearchTermData data) {
        this();
        setData(data);
    }

    @Override
    public StashSearchTermData getData() {
        return new StashSearchTermData(titleInput.getText(), searchInput.getText(), colorCombo.getSelectedIndex());
    }

    @Override
    public void setData(StashSearchTermData data) {
        titleInput.setText(data.title);
        searchInput.setText(data.searchTerm);
        colorCombo.setSelectedIndex(data.colorIndex);
    }

}
