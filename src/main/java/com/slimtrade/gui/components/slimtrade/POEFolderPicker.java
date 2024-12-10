package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.Game;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class POEFolderPicker extends FilePicker implements PathChangeListener {

    private final Game game;
    public final JCheckBox notInstalledCheckbox = new JCheckBox("Not Installed");
    private final JLabel notInstalledLabel;
    private final JPanel pathPanel = new JPanel(new GridBagLayout());
    public boolean packParentWindow;

    public POEFolderPicker(Game game) {
        this(game, false);
    }

    public POEFolderPicker(Game game, boolean packParentWindow) {
        super("Select the '" + game + "' install folder.");
        this.game = game;
        this.packParentWindow = packParentWindow;
        notInstalledLabel = new ResultLabel(ResultStatus.INDETERMINATE, "If you ever install " + game + ", update this setting.");
        notInstalledLabel.setVisible(false);
        fileChooser = new POEFileChooser(game);
        JPanel pathWrapperPanel = new JPanel(new BorderLayout());
        pathWrapperPanel.add(notInstalledLabel, BorderLayout.NORTH);
        pathWrapperPanel.add(pathPanel, BorderLayout.SOUTH);
        add(notInstalledCheckbox, BorderLayout.NORTH);
        add(pathWrapperPanel, BorderLayout.SOUTH);
        addPathChangeListener(this);
        notInstalledCheckbox.addActionListener(e -> {
            boolean showMainComponents = !notInstalledCheckbox.isSelected();
            chooserPanel.setVisible(showMainComponents);
            pathPanel.setVisible(showMainComponents);
            notInstalledLabel.setVisible(!showMainComponents);
            if (packParentWindow) ZUtil.packComponentWindow(this);
        });
    }

    public void createDuplicatePathPanels(Path[] paths) {
        if (paths.length < 2) return;
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        for (Path path : paths) {
            JButton pathButton = new JButton("Select");
            JLabel pathLabel = new JLabel(path.toString());
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panel.add(pathButton);
            panel.add(pathLabel);
            pathPanel.add(panel, gc);
            gc.gridy++;
            pathButton.addActionListener(e -> setSelectedPath(path));
        }
    }

    public boolean notInstalledCheckboxValue() {
        return notInstalledCheckbox.isSelected();
    }

    @Override
    public void onPathChanged(Path path) {
        boolean validFolderName = path.endsWith(game.toString());
        if (validFolderName) {
            Path logsFolder = path.resolve(SaveManager.POE_LOG_FOLDER_NAME);
            if (logsFolder.toFile().exists()) {
                setErrorText("Install folder set correctly.", ResultStatus.APPROVE);
            } else {
                setErrorText("Correct folder name, wrong folder! The install folder must contain a '" + SaveManager.POE_LOG_FOLDER_NAME + "' folder.", ResultStatus.INDETERMINATE);
            }
        } else {
            setErrorText("Folder should be named '" + game + "'.", ResultStatus.DENY);
        }
        if (packParentWindow) ZUtil.packComponentWindow(this);
    }

}
