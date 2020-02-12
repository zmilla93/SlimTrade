package com.slimtrade.gui.scanner;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

public class SearchNamePanel extends JPanel {

    protected JLabel searchNameLabel = new JLabel("Search Name");
    protected JComboBox<ScannerMessage> searchCombo = new CustomCombo<>();
    protected JButton searchButton = new BasicButton("Search");
    protected JTextField searchNameTextField = new LimitTextField(32);
    protected JButton saveButton = new BasicButton("Save");
    protected JButton clearButton = new BasicButton("Clear");
    protected JButton revertButton = new BasicButton("Revert");
    protected JButton deleteButton = new BasicButton("Delete");

    private JPanel namePanel = new JPanel(FrameManager.gridBag);
    private JPanel buttonPanel = new JPanel(FrameManager.gridBag);

    public SearchNamePanel() {
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
//		buttonPanel.add(searchButton, gc);
        gc.gridx = 0;
        gc.weightx = 1;
        gc.insets.left = 0;
        gc.insets.top = 0;


        // Name Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 5;
        namePanel.add(searchNameTextField, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        namePanel.add(searchCombo, gc);
        gc.gridy = 0;


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
    }

}
