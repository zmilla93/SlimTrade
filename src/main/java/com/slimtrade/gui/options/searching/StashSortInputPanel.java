package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.theme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class StashSortInputPanel extends JPanel {

    private JButton submitButton = new JButton("Add Search");
    private JTextField tagInput = new JTextField(10);
    private JTextField searchInput = new JTextField(20);
    private JComboBox colorCombo = new ColorCombo();

    public StashSortInputPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.gridx = 1;
        add(new JLabel("Button Text"), gc);
        gc.gridx++;
        add(new JLabel("Search Terms"), gc);
        gc.gridx++;
        add(new JLabel("Color"), gc);
        gc.gridx = 0;
        gc.gridy++;
        add(submitButton, gc);
        gc.gridx++;
        add(tagInput, gc);
        gc.gridx++;
        add(searchInput, gc);
        gc.gridx++;
        add(colorCombo, gc);
        gc.gridx++;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public StashSortData getData() {
        return new StashSortData(tagInput.getText(), searchInput.getText(), colorCombo.getSelectedIndex());
    }

}
