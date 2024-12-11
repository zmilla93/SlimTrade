package github.zmilla93.gui.chatscanner;

import github.zmilla93.gui.components.ComponentPair;
import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class ChatScannerDeletePanel extends AbstractOptionPanel {

    //    private final JLabel currentNameLabel = new JLabel();
    private final JTextField textInput = new JTextField(12);
    private final JButton deleteButton = new JButton("Delete Entry");
    private final JButton cancelButton = new JButton("Cancel");
    private JLabel headerLabel;

    private String currentName;

    public ChatScannerDeletePanel() {
        super(true);
        addListeners();
        headerLabel = addHeader("").label;
        addComponent(new JLabel("Are you sure you want to delete this entry?"));
        addVerticalStrutSmall();
        addComponent(new ComponentPair(cancelButton, deleteButton));
    }

    private void addListeners() {
        cancelButton.addActionListener(e -> FrameManager.chatScannerWindow.showEntry(currentName));
        deleteButton.addActionListener(e -> FrameManager.chatScannerWindow.deleteEntry(currentName));
    }

    public void setCurrentName(String text) {
        currentName = text;
        headerLabel.setText("Delete Entry - " + text);
        deleteButton.setText("Delete '" + text + "'");
    }

}
