package com.slimtrade.gui.scanner;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

public class SearchNamePanel extends JPanel {

    private JLabel searchNameLabel = new JLabel("Search Name");
    public JButton searchButton = new BasicButton("Search");
    public JComboBox<ScannerMessage> searchCombo = new CustomCombo<>();
    public JTextField saveTextField = new LimitTextField(32);

    public JButton saveButton = new BasicButton("Save");
    public JButton clearButton = new BasicButton("Clear");
    public JButton revertButton = new BasicButton("Revert");
    public JButton deleteButton = new BasicButton("Delete");

    private JPanel buttonPanel = new JPanel(FrameManager.gridBag);
    private JPanel namePanel = new JPanel(FrameManager.gridBag);

    public SearchNamePanel() {

        super(FrameManager.gridBag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        // Button Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.top = 10;
        buttonPanel.add(deleteButton, gc);
        gc.gridx++;
        gc.insets.left = 40;
        buttonPanel.add(revertButton, gc);
        gc.gridx++;
        buttonPanel.add(clearButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);
        gc.gridx++;
        gc.gridx = 0;
        gc.weightx = 1;
        gc.insets.left = 0;
        gc.insets.top = 0;

        // Name Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 5;
        namePanel.add(saveTextField, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        namePanel.add(searchCombo, gc);
        gc.gridy = 0;

        // Upper Panel
        // Row 1
        gc.fill = GridBagConstraints.BOTH;
        this.add(searchNameLabel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(0, 0, 5, 0);
        this.add(searchButton, gc);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        this.add(namePanel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridy++;
        this.add(Box.createHorizontalStrut(400), gc);
        gc.gridwidth = 1;


        // Row 2
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.gridy++;
        this.add(buttonPanel, gc);
        gc.gridwidth = 1;
    }

}
