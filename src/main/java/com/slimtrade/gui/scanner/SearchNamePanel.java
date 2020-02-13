package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

import static com.slimtrade.gui.scanner.ChatScannerWindow.bufferOuter;

public class SearchNamePanel extends JPanel implements IColorable {

    // Public Controls
    public JTextField saveTextField = new LimitTextField(32);
    public JComboBox<ScannerMessage> searchCombo = new CustomCombo<>();

    public BasicButton searchButton = new ConfirmButton("Search");
    public BasicButton saveButton = new ConfirmButton("Save");
    public BasicButton clearButton = new BasicButton("Clear");
    public BasicButton revertButton = new BasicButton("Revert");
    public BasicButton deleteButton = new DenyButton("Delete");

    // Private Labels
    private JLabel info1 = new JLabel("Create a save by entering a name and search terms.");
    private JLabel searchNameLabel = new JLabel("Search Name");

    // Internal
    private JPanel buttonPanel = new JPanel(FrameManager.gridBag);
    private JPanel namePanel = new JPanel(FrameManager.gridBag);
    private JPanel outerPanel = new JPanel(FrameManager.gridBag);

    public SearchNamePanel() {

        super(FrameManager.gridBag);


        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

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
        gc.fill = GridBagConstraints.NONE;


        // Name Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 5;
        namePanel.add(saveTextField, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        namePanel.add(searchCombo, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridy = 0;

        // Full Panel
        gc.gridwidth = 2;
        gc.insets.bottom = 8;
        outerPanel.add(info1, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        outerPanel.add(searchNameLabel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridwidth = 1;
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(0, 0, 5, 0);
        outerPanel.add(searchButton, gc);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        outerPanel.add(namePanel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridy++;
        outerPanel.add(Box.createHorizontalStrut(400), gc);
        gc.gridwidth = 1;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.gridy++;
        outerPanel.add(buttonPanel, gc);
        gc.gridwidth = 1;

        gc = new GridBagConstraints();
        gc.insets = new Insets(bufferOuter, bufferOuter, bufferOuter, bufferOuter);
        this.add(outerPanel, gc);



        namePanel.setBackground(ColorManager.CLEAR);

        App.eventManager.addColorListener(this);
        this.updateColor();

    }

    @Override
    public void updateColor() {

        //Background
        this.setBackground(ColorManager.BACKGROUND);
        namePanel.setBackground(ColorManager.BACKGROUND);
        buttonPanel.setBackground(ColorManager.BACKGROUND);
        outerPanel.setBackground(ColorManager.BACKGROUND);

        // Foreground
        this.setBorder(ColorManager.BORDER_TEXT);
        searchNameLabel.setForeground(ColorManager.TEXT);
    }
}
