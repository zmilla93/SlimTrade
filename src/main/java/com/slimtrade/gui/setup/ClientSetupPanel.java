package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.Game;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.slimtrade.POEFolderPicker;
import com.slimtrade.gui.components.slimtrade.POEInstallFolderExplanationPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class ClientSetupPanel extends AbstractSetupPanel {

    private final JButton moreInfoButton = new JButton("Detailed Explanation");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2);
    POEInstallFolderExplanationPanel moreInfoPanel = new POEInstallFolderExplanationPanel(true, false);

    // FIXME: Paste from clipboard button?

    public ClientSetupPanel(JButton nextButton) {
        super(nextButton);
        AbstractOptionPanel panel = new AbstractOptionPanel(false, false);
        panel.addHeader("Path of Exile Install Folder");
        panel.addComponent(new JLabel("SlimTrade needs to know where Path of Exile is installed in order to read the game's chat logs."));
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
                picker.setErrorText("Failed to auto detect an install folder.", ResultStatus.DENY);
            else if (validDirectories.length == 1) {
                picker.setSelectedPath(validDirectories[0]);
                picker.setErrorText("Install folder auto detected, verify it looks correct.", ResultStatus.APPROVE);
            } else
                picker.setErrorText("Multiple install folders auto detected, select the correct one.", ResultStatus.INDETERMINATE);
        } else {
            if (path.toFile().isFile()) {
                picker.setSelectedPath(path);
                if (path.toFile().exists()) {
                    if (path.resolve(SaveManager.POE_LOG_FOLDER_NAME).toFile().exists())
                        picker.setErrorText("Using a previously set install folder.", ResultStatus.APPROVE);
                    else
                        picker.setErrorText("Using a previously set install folder, but the '" + SaveManager.POE_LOG_FOLDER_NAME + "' folder no longer exists.", ResultStatus.INDETERMINATE);
                } else
                    picker.setErrorText("An install folder was set previously, but no longer exists.", ResultStatus.APPROVE);
            }
        }
    }

    private void updateErrorLabel(JLabel label, ResultStatus resultStatus, String message) {
        label.setText(message);
        if (resultStatus == ResultStatus.APPROVE) label.setForeground(ThemeManager.getCurrentExtensions().approve);
        if (resultStatus == ResultStatus.DENY) label.setForeground(ThemeManager.getCurrentExtensions().deny);
    }

    @Override
    public boolean isSetupValid() {
        return true;
    }

}
