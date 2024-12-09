package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.gui.components.ComponentPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A button, label, and file chooser combined into a single component.
 * Uses a {@link BorderLayout} layout with a single panel positioned at WEST to allow for easy extensions in 3 directions.
 * Use a {@link PathChangeListener} to listen for events.
 */
public class FilePicker extends JPanel {

    private static final String DEFAULT_NO_SELECTION_STRING = "No folder selected.";

    public final JLabel pathLabel;
    public final JLabel errorLabel = new JLabel("This is an error!");
    public JFileChooser fileChooser = new JFileChooser();
    public final JPanel chooserPanel = new JPanel(new BorderLayout());

    private final String noFileSelectedText;

    private Path selectedPath;
    private final ArrayList<PathChangeListener> listeners = new ArrayList<>();

    public FilePicker() {
        this(DEFAULT_NO_SELECTION_STRING);
    }

    public FilePicker(String noFileSelectedText) {
        this.noFileSelectedText = noFileSelectedText;
        pathLabel = new JLabel(noFileSelectedText);
        setLayout(new BorderLayout());
        JButton browseButton = new JButton("Browse");
        chooserPanel.add(new ComponentPanel(browseButton, pathLabel), BorderLayout.WEST);
        chooserPanel.add(errorLabel, BorderLayout.SOUTH);
        add(chooserPanel, BorderLayout.WEST);
        browseButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(FilePicker.this) == JFileChooser.APPROVE_OPTION) {
                Path path = fileChooser.getSelectedFile().toPath();
                setSelectedPath(path);
            }
        });
    }

    /**
     * @return The currently selected path
     */
    public Path getSelectedPath() {
        return selectedPath;
    }

    /**
     * Sets the file chooser's selected file (and directory, if applicable) to the target path, and updates the label.
     *
     * @param path Target path
     */
    public void setSelectedPath(Path path) {
        selectedPath = path;
        if (path == null) {
            fileChooser.setCurrentDirectory(null);
            fileChooser.setSelectedFile(null);
        } else {
            File file = path.toFile();
            fileChooser.setSelectedFile(file);
            if (file.isDirectory()) fileChooser.setCurrentDirectory(file);
        }
        String text = path == null ? noFileSelectedText : path.toString();
        pathLabel.setText(text);
        for (PathChangeListener listener : listeners) listener.onPathChanged(path);
    }

    public void setErrorText(String text, ResultStatus result) {
        boolean hideLabel = (text == null || text.isEmpty());
        setVisible(!hideLabel);
        errorLabel.setText(text);
        errorLabel.setForeground(result.toColor());
    }

    public void addPathChangeListener(PathChangeListener listener) {
        listeners.add(listener);
    }

}
