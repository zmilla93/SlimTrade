package github.zmilla93.gui.setup;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.PoeClientPathCheck;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.poe.POEFolderPicker;
import github.zmilla93.gui.components.poe.POEInstallFolderExplanationPanel;
import github.zmilla93.modules.theme.ThemeColor;

import javax.swing.*;
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
        addHeader("Path of Exile Install Folder");
        addComponent(new JLabel("SlimTrade requires Path of Exile's install location to read chat logs."));
        addVerticalStrut();
        addHeader(Game.PATH_OF_EXILE_1.explicitName);
        addComponent(poe1FolderPicker);
        addVerticalStrut();
        addHeader(Game.PATH_OF_EXILE_2.toString());
        addComponent(poe2FolderPicker);
        addVerticalStrut();
        addComponent(moreInfoButton);
        addComponent(moreInfoPanel);
    }

    @Override
    protected void addComponentListeners() {
        poe1FolderPicker.addPathChangeListener(path -> runSetupValidation());
        poe2FolderPicker.addPathChangeListener(path -> runSetupValidation());
        poe1FolderPicker.notInstalledCheckbox.addActionListener(e -> runSetupValidation());
        poe2FolderPicker.notInstalledCheckbox.addActionListener(e -> runSetupValidation());
        moreInfoButton.addActionListener(e -> {
            moreInfoPanel.setVisible(!moreInfoPanel.isVisible());
            ZUtil.packComponentWindow(this);
        });
    }

    /**
     * Sets a very specific error message based on the state of settings.json and what files actually exist on disk.
     */
    private void setInitialErrorLabelStatus(Path path, POEFolderPicker picker) {
        if (path == null) {
            Path[] validDirectories = picker.createDuplicatePathPanels(false);
            if (validDirectories.length == 0)
                picker.setErrorText("Auto detection of install folder failed.", ThemeColor.DENY);
            else if (validDirectories.length == 1) {
                picker.setSelectedPath(validDirectories[0]);
                picker.setErrorText("Install folder auto detected.", ThemeColor.APPROVE);
            } else
                picker.setErrorText("Multiple install folders detected, select the correct one.", ThemeColor.INDETERMINATE);
        } else {
            PoeClientPathCheck validator = PoeClientPathCheck.validateInstallFolder(path);
            if (validator.status != ThemeColor.APPROVE) picker.createDuplicatePathPanels(true);
        }
    }

    @Override
    public void initializeComponents() {
        Path poe1Dir = ZUtil.getPath(SaveManager.settingsSaveFile.data.settingsPoe1.installFolder);
        Path poe2Dir = ZUtil.getPath(SaveManager.settingsSaveFile.data.settingsPoe2.installFolder);
        poe1FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled);
        poe2FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled);
        poe1FolderPicker.setSelectedPath(poe1Dir);
        poe2FolderPicker.setSelectedPath(poe2Dir);
        setInitialErrorLabelStatus(poe1Dir, poe1FolderPicker);
        setInitialErrorLabelStatus(poe2Dir, poe2FolderPicker);
    }

    @Override
    public boolean isSetupValid() {
        Path poe1Path = poe1FolderPicker.getSelectedPath();
        Path poe2Path = poe2FolderPicker.getSelectedPath();
        boolean poe1NotInstalled = poe1FolderPicker.notInstalledCheckboxValue();
        boolean poe2NotInstalled = poe2FolderPicker.notInstalledCheckboxValue();
        boolean validPoe1Path = poe1NotInstalled || PoeClientPathCheck.isValidInstallFolder(poe1Path);
        boolean validPoe2Path = poe2NotInstalled || PoeClientPathCheck.isValidInstallFolder(poe2Path);
        return validPoe1Path && validPoe2Path;
    }

    @Override
    public void applyCompletedSetup() {
        boolean poe1NotInstalled = poe1FolderPicker.notInstalledCheckboxValue();
        boolean poe2NotInstalled = poe2FolderPicker.notInstalledCheckboxValue();
        SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled = poe1NotInstalled;
        SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled = poe2NotInstalled;
        Path poe1Path = poe1FolderPicker.getSelectedPath();
        Path poe2Path = poe2FolderPicker.getSelectedPath();
        if (!poe1NotInstalled && PoeClientPathCheck.isValidInstallFolder(poe1Path))
            SaveManager.settingsSaveFile.data.settingsPoe1.installFolder = poe1Path.toString();
        if (!poe2NotInstalled && PoeClientPathCheck.isValidInstallFolder(poe2Path))
            SaveManager.settingsSaveFile.data.settingsPoe2.installFolder = poe2Path.toString();
        SaveManager.settingsSaveFile.data.hasInitGameDirectories = true;
    }

}
