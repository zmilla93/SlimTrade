package github.zmilla93.gui.chatscanner;

import github.zmilla93.gui.components.ComponentPair;
import github.zmilla93.gui.components.ErrorLabel;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ChatScannerNewEntryPanel extends AbstractOptionPanel {

    private final JButton cancelButton = new JButton("Cancel");
    private final JButton createEntryButton = new JButton("Create Entry");
    private final JTextField entryNameInput = new JTextField(12);
    private final JLabel errorLabel = new ErrorLabel();

    public ChatScannerNewEntryPanel() {
        super(true);
        addHeader("New Scanner Entry");
        JLabel entryLabel = new JLabel("Name");
        addComponent(new ComponentPair(entryLabel, entryNameInput));
        addComponent(new ComponentPair(cancelButton, createEntryButton));
        addComponent(errorLabel);
    }

    public void clearName() {
        entryNameInput.setText("");
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getCreateEntryButton() {
        return createEntryButton;
    }

    public String getInputText() {
        return entryNameInput.getText();
    }

    public void setError(String error) {
        errorLabel.setText(error);
    }

}
