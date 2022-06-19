package com.slimtrade.gui.chatscanner;

import com.slimtrade.gui.components.ComponentPair;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ChatScannerRenamePanel extends AbstractOptionPanel {

    private final JLabel headerLabel;
    private final JTextField newNameInput = new JTextField(ChatScannerWindow.TEXT_COLUMNS);
    private final JButton applyNameButton = new JButton("Rename Entry");
    private final JButton cancelButton = new JButton("Cancel");
    private String currentName;

    public ChatScannerRenamePanel() {
        super(true);
        headerLabel = addHeader("Rename Entry").getLabel();
        addPanel(new ComponentPair(new JLabel("New Name"), newNameInput));
        addSmallVerticalStrut();
        addPanel(new ComponentPair(cancelButton, applyNameButton));
        addListeners();
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> FrameManager.chatScannerWindow.showEntry(currentName));
        applyNameButton.addActionListener(e -> FrameManager.chatScannerWindow.renameEntry(currentName, getNewName()));
    }

    public void setCurrentName(String text) {
        currentName = text;
        headerLabel.setText("Rename Entry - " + text);
        newNameInput.setText("");
        newNameInput.requestFocus();
    }

    public String getNewName() {
        return newNameInput.getText();
    }

}
