package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.Game;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.slimtrade.POEFolderPicker;
import com.slimtrade.gui.components.slimtrade.POEInstallFolderExplanationPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Path;

public class ClientSetupPanel extends AbstractSetupPanel {

    private final JButton moreInfoButton = new JButton("Detailed Explanation");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1, true);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2, true);
    POEInstallFolderExplanationPanel moreInfoPanel = new POEInstallFolderExplanationPanel(true, false);

    // FIXME: Paste from clipboard button?

    public ClientSetupPanel(JButton nextButton) {
        super(nextButton);
        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        panel.addHeader("Path of Exile Install Folder");
        panel.addComponent(new JLabel("SlimTrade requires Path of Exile's install location to read chat logs."));
        panel.addVerticalStrut();
        panel.addHeader(Game.PATH_OF_EXILE_1.toString());
        panel.addComponent(poe1FolderPicker);
        panel.addVerticalStrut();
        panel.addHeader(Game.PATH_OF_EXILE_2.toString());
        panel.addComponent(poe2FolderPicker);
        panel.addVerticalStrut();
        panel.addComponent(moreInfoButton);
        panel.addComponent(moreInfoPanel);

        contentPanel.add(panel, BorderLayout.CENTER);

        setAllErrorLabelsToInitialStatus();
        addListeners();
    }

    private void addListeners() {
        poe1FolderPicker.addPathChangeListener(path -> runSetupValidation());
        poe2FolderPicker.addPathChangeListener(path -> runSetupValidation());
        poe1FolderPicker.notInstalledCheckbox.addActionListener(e -> {
            runSetupValidation();
        });
        poe2FolderPicker.notInstalledCheckbox.addActionListener(e -> {

            runSetupValidation();
        });
        moreInfoButton.addActionListener(e -> {
            moreInfoPanel.setVisible(!moreInfoPanel.isVisible());
            ZUtil.packComponentWindow(this);
        });
    }

    private void setAllErrorLabelsToInitialStatus() {
        Path poe1Dir = SaveManager.settingsSaveFile.data.poe1InstallDirectory;
        Path poe2Dir = SaveManager.settingsSaveFile.data.poe1InstallDirectory;
        setInitialErrorLabelStatus(poe1Dir, poe1FolderPicker, Game.PATH_OF_EXILE_1);
        setInitialErrorLabelStatus(poe2Dir, poe2FolderPicker, Game.PATH_OF_EXILE_2);
    }

    /**
     * Sets a very specific error message based on the state of settings.json and what files actually exist on disk.
     */
    private void setInitialErrorLabelStatus(Path path, POEFolderPicker picker, Game game) {
        if (path == null) {
            Path[] validDirectories = SaveManager.getValidGameDirectories(game);
            picker.createDuplicatePathPanels(validDirectories);
            if (validDirectories.length == 0)
                picker.setErrorText("Auto detection of install folder failed.", ResultStatus.DENY);
            else if (validDirectories.length == 1) {
                picker.setSelectedPath(validDirectories[0]);
                picker.setErrorText("Install folder auto detected.", ResultStatus.APPROVE);
            } else
                picker.setErrorText("Multiple install folders detected, select the correct one.", ResultStatus.INDETERMINATE);
        } else {
            if (path.toFile().isFile()) {
                picker.setSelectedPath(path);
                if (path.toFile().exists()) {
                    if (path.resolve(SaveManager.POE_LOG_FOLDER_NAME).toFile().exists())
                        picker.setErrorText("Using a previously set install folder.", ResultStatus.APPROVE);
                    else
                        picker.setErrorText("Using a previously set install folder, but the '" + SaveManager.POE_LOG_FOLDER_NAME + "' folder is missing.", ResultStatus.INDETERMINATE);
                } else
                    picker.setErrorText("The previously set install folder no longer exists.", ResultStatus.INDETERMINATE);
            }
        }
    }


    /**
     * Verify that a given path points to the Path of Exile 1 or 2's install directory.
     * Checks that the path isn't null, is a directory that ends with Path of Exile 1 or 2, and contains a 'logs' subfolder
     */
    // FIXME : Move somewhere more general?
    private boolean isValidPOEFolder(Path path, Game game) {
        if (path == null) return false;
        if (!path.endsWith(game.toString())) return false;
        File file = path.toFile();
        boolean validFolder = file.exists() && file.isDirectory();
        if (!validFolder) return false;
        return path.resolve(SaveManager.POE_LOG_FOLDER_NAME).toFile().exists();
    }

//    @Override
//    protected void runSetupValidation() {
//        super.runSetupValidation();
////        assert SwingUtilities.isEventDispatchThread();
////        System.out.println("PACK!");
////        ZUtil.packComponentWindow(this);
//    }

    @Override
    public boolean isSetupValid() {
        Path poe1Path = poe1FolderPicker.getSelectedPath();
        Path poe2Path = poe2FolderPicker.getSelectedPath();
        boolean poe1NotInstalled = poe1FolderPicker.notInstalledCheckboxValue();
        boolean poe2NotInstalled = poe2FolderPicker.notInstalledCheckboxValue();
        boolean validPoe1Path = poe1NotInstalled || isValidPOEFolder(poe1Path, Game.PATH_OF_EXILE_1);
        boolean validPoe2Path = poe2NotInstalled || isValidPOEFolder(poe2Path, Game.PATH_OF_EXILE_2);
        return validPoe1Path && validPoe2Path;
    }

}
