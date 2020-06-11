package com.slimtrade.gui.options.stashsearch;

import com.slimtrade.enums.StashTabColor;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.custom.CustomCombo;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.stash.LimitTextField;
import com.slimtrade.gui.stash.StashTabCellRenderer;

import javax.swing.*;
import java.awt.*;

public class SearchInputPanel extends JPanel {

    private final int HORIZONTAL_BUFFER = 10;

    private JButton addButton;
    private JTextField searchNameInput;
    private JTextField searchTermsInput;
    private JComboBox<Color> colorCombo;

    public SearchInputPanel() {
        super(new GridBagLayout());
        this.setOpaque(false);

        // Components
        JLabel searchNameLabel = new CustomLabel("Search Name");
        JLabel searchTermsLabel = new CustomLabel("Search Terms");
        addButton = new BasicButton("Add Terms");
        searchNameInput = new LimitTextField(32, 10);
        searchTermsInput = new LimitTextField(50, 30);

        colorCombo = new CustomCombo<>();
        colorCombo.setFocusable(false);
        colorCombo.setRenderer(new StashTabCellRenderer());
        for (StashTabColor c : StashTabColor.values()) {
            colorCombo.addItem(c.getBackground());
        }

        // Build UI
        GridBagConstraints gc = new GridBagConstraints();

        gc.gridx = 1;
        gc.gridy = 0;
        add(searchNameLabel, gc);
        gc.gridx++;
        gc.insets.right = 0;
        gc.insets.left = HORIZONTAL_BUFFER;
        add(searchTermsLabel, gc);

        gc.gridx = 0;
        gc.gridy = 1;
        gc.insets.left = 0;
        add(addButton, gc);
        gc.gridx++;
        gc.insets.left = HORIZONTAL_BUFFER;
        add(searchNameInput, gc);
        gc.gridx++;
        add(searchTermsInput, gc);
        gc.gridx++;
        add(colorCombo, gc);

    }

    public void clearText() {
        searchNameInput.setText("");
        searchTermsInput.setText("");
    }

    public JButton getAddButton() {
        return addButton;
    }

    public String getSearchName() {
        return searchNameInput.getText();
    }

    public String getSearchTerms() {
        return searchTermsInput.getText();
    }

    public StashTabColor getColor() {
        return StashTabColor.getValueFromColor((Color) colorCombo.getSelectedItem());
    }

}
