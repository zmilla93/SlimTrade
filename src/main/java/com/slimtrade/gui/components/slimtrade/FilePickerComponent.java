package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.gui.components.ComponentPanel;

import javax.swing.*;
import java.awt.*;

/**
 * A button, label, and file chooser combined into a single component.
 */
public class FilePickerComponent extends JPanel {

    private static final String NO_SELECTION_STRING = "No folder selected.";

//    private JPanel upperPanel = new JPanel();

    private final JButton browseButton = new JButton("Browse");
    private final JLabel pathLabel = new JLabel(NO_SELECTION_STRING);
    private final JLabel errorLabel = new JLabel("This is an error!");
    private final JFileChooser fileChooser = new POEFileChooser();

    public FilePickerComponent() {
        setLayout(new BorderLayout());
        add(new ComponentPanel(browseButton, pathLabel), BorderLayout.NORTH);
        add(errorLabel, BorderLayout.CENTER);
        addListeners();
    }

    private void addListeners() {
        browseButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(FilePickerComponent.this) == JFileChooser.APPROVE_OPTION) {
                pathLabel.setText(fileChooser.getSelectedFile().getPath());
            }
        });
    }

}
