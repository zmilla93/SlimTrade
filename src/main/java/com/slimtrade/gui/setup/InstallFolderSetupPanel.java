package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.Game;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.slimtrade.POEFolderPicker;
import com.slimtrade.gui.components.slimtrade.POEInstallFolderExplanationPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

/**
 * Handles finding the Path of Exile 1 and/or 2 install folders.
 */
public class InstallFolderSetupPanel extends AbstractSetupPanel {

    private final JButton moreInfoButton = new JButton("Detailed Explanation");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1, true);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2, true);
    private final POEInstallFolderExplanationPanel moreInfoPanel = new POEInstallFolderExplanationPanel(true, false);

    public InstallFolderSetupPanel() {
        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        panel.addHeader("Path of Exile Install Folder");
        panel.addComponent(new JLabel("SlimTrade requires Path of Exile's install location to read chat logs."));
        panel.addVerticalStrut();
        panel.addHeader(Game.PATH_OF_EXILE_1.getExplicitName());
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
        Path poe1Dir = ZUtil.getPath(SaveManager.settingsSaveFile.data.poe1InstallDirectory);
        Path poe2Dir = ZUtil.getPath(SaveManager.settingsSaveFile.data.poe2InstallDirectory);
        setInitialErrorLabelStatus(poe1Dir, poe1FolderPicker);
        setInitialErrorLabelStatus(poe2Dir, poe2FolderPicker);
    }

    /**
     * Sets a very specific error message based on the state of settings.json and what files actually exist on disk.
     */
    private void setInitialErrorLabelStatus(Path path, POEFolderPicker picker) {
        if (path == null) {
            Path[] validDirectories = picker.createDuplicatePathPanels(false);
            if (validDirectories.length == 0)
                picker.setErrorText("Auto detection of install folder failed.", ResultStatus.DENY);
            else if (validDirectories.length == 1) {
                picker.setSelectedPath(validDirectories[0]);
                picker.setErrorText("Install folder auto detected.", ResultStatus.APPROVE);
            } else
                picker.setErrorText("Multiple install folders detected, select the correct one.", ResultStatus.INDETERMINATE);
        } else {
            if (path.toFile().exists()) {
                picker.setSelectedPath(path);
                if (path.toFile().exists()) {
                    if (path.resolve(SaveManager.POE_LOG_FOLDER_NAME).toFile().exists())
                        picker.setErrorText("Using a saved install folder.", ResultStatus.APPROVE);
                    else
                        picker.setErrorText("Using a saved install folder, but the '" + SaveManager.POE_LOG_FOLDER_NAME + "' folder is missing.", ResultStatus.INDETERMINATE);
                } else
                    picker.setErrorText("The previously set install folder no longer exists.", ResultStatus.INDETERMINATE);
            } else {
                picker.setErrorText("The previously set folder no longer exists.", ResultStatus.DENY);
                picker.createDuplicatePathPanels(true);
            }
        }
    }

    @Override
    public boolean isSetupValid() {
        Path poe1Path = poe1FolderPicker.getSelectedPath();
        Path poe2Path = poe2FolderPicker.getSelectedPath();
        boolean poe1NotInstalled = poe1FolderPicker.notInstalledCheckboxValue();
        boolean poe2NotInstalled = poe2FolderPicker.notInstalledCheckboxValue();
        boolean validPoe1Path = poe1NotInstalled || TradeUtil.isValidPOEFolder(poe1Path, Game.PATH_OF_EXILE_1);
        boolean validPoe2Path = poe2NotInstalled || TradeUtil.isValidPOEFolder(poe2Path, Game.PATH_OF_EXILE_2);
        return validPoe1Path && validPoe2Path;
    }

    @Override
    public void applyCompletedSetup() {
        SaveManager.settingsSaveFile.data.poe1InstallDirectory = poe1FolderPicker.getSelectedPath().toString();
        SaveManager.settingsSaveFile.data.poe2InstallDirectory = poe2FolderPicker.getSelectedPath().toString();
        SaveManager.settingsSaveFile.data.hasInitializedGamePaths = true;
    }

}
