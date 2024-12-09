package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.gui.components.ComponentPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A button, label, and file chooser combined into a single component.
 * Uses a {@link BorderLayout} layout with a single panel positioned at CENTER to allow for easy extension.
 * Use a {@link PathChangeListener} to listen for events.
 */
public class FilePicker extends JPanel {

    private static final String DEFAULT_NO_SELECTION_STRING = "No folder selected.";

    private final JLabel pathLabel;
    private final JLabel errorLabel = new JLabel("This is an error!");
    public final JFileChooser fileChooser = new POEFileChooser();

    private Path selectedPath;
    private final ArrayList<PathChangeListener> listeners = new ArrayList<>();

    public FilePicker() {
        this(DEFAULT_NO_SELECTION_STRING);
    }

    public FilePicker(String noFileSelectedText) {
        pathLabel = new JLabel(noFileSelectedText);
        setLayout(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());
        JButton browseButton = new JButton("Browse");
        centerPanel.add(new ComponentPanel(browseButton, pathLabel), BorderLayout.NORTH);
        centerPanel.add(errorLabel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        browseButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(FilePicker.this) == JFileChooser.APPROVE_OPTION) {
                Path path = fileChooser.getSelectedFile().toPath();
                setSelectedPath(path);
                for (PathChangeListener listener : listeners) listener.onPathChanged(path);
            }
        });
    }

    /// Override this to set the text that shows when no file is selected.
//    public String getNoFileSelectedText() {
//        return DEFAULT_NO_SELECTION_STRING;
//    }
    public Path getSelectedPath() {
        return selectedPath;
    }

    public void setSelectedPath(Path path) {
        selectedPath = path;
        pathLabel.setText(path.toString());
    }

    public void setErrorText(String text) {
        boolean hideLabel = (text == null || text.isEmpty());
        setVisible(!hideLabel);
        errorLabel.setText(text);
    }

    public void addListener(PathChangeListener listener) {
        listeners.add(listener);
    }

}
