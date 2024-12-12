package github.zmilla93.gui.options.poe;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.GameWindowMode;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.MonitorPicker;
import github.zmilla93.gui.components.poe.POEFolderPicker;
import github.zmilla93.gui.components.poe.POEInstallFolderExplanationPanel;
import github.zmilla93.gui.components.poe.detection.GameDetectionButton;
import github.zmilla93.gui.options.AbstractOptionPanel;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;

public class PathOfExileOptionPanel extends AbstractOptionPanel implements ISavable {

    // Shared
    private final JComboBox<GameWindowMode> windowModeCombo = new JComboBox<>();
    private final GameDetectionButton detectionButton = new GameDetectionButton();
    private final MonitorPicker monitorPicker = new MonitorPicker();
    private final HotkeyButton chatHotkeyButton = new HotkeyButton();
    private final JButton explanationButton = new JButton("Install Folder Info");
    private final JPanel explanationPanel = new POEInstallFolderExplanationPanel(true, false);

    // Game Specific
    private final JCheckBox poe1UsingStashFoldersCheckbox = new JCheckBox("Using Stash Folders");
    private final JCheckBox poe2UsingStashFoldersCheckbox = new JCheckBox("Using Stash Folders");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2);
    private final AbstractOptionPanel poe1OptionPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel poe2OptionPanel = new AbstractOptionPanel(false, false);

    public PathOfExileOptionPanel() {
        windowModeCombo.addItem(GameWindowMode.DETECT);
        windowModeCombo.addItem(GameWindowMode.MONITOR);

        /// Shared
        addHeader("Path of Exile 1 & 2");
        addComponent(new ComponentPanel(new JLabel("Game Window"), windowModeCombo));
        addComponent(detectionButton);
        addComponent(monitorPicker);
        addComponent(new ComponentPanel(new JLabel("Open Chat Hotkey"), chatHotkeyButton));
        addVerticalStrut();

        /// Path of Exile 1
        poe1OptionPanel.addComponent(poe1UsingStashFoldersCheckbox);
        addHeader(Game.PATH_OF_EXILE_1.getExplicitName());
        addComponent(poe1FolderPicker);
        addFullWidthComponent(poe1OptionPanel);
        addVerticalStrut();

        /// Path of Exile 2
        poe2OptionPanel.addComponent(poe2UsingStashFoldersCheckbox);
        addHeader(Game.PATH_OF_EXILE_2.toString());
        addComponent(poe2FolderPicker);
        addFullWidthComponent(poe2OptionPanel);
        addVerticalStrut();

        /// Explain Install Files
        addComponent(explanationButton);
        addComponent(explanationPanel);
        addListeners();
    }

    private void addListeners() {
        explanationButton.addActionListener(e -> explanationPanel.setVisible(!explanationPanel.isVisible()));
        poe1FolderPicker.notInstalledCheckbox.addActionListener(e -> poe1OptionPanel.setVisible(!poe1FolderPicker.notInstalledCheckbox.isSelected()));
        poe2FolderPicker.notInstalledCheckbox.addActionListener(e -> poe2OptionPanel.setVisible(!poe2FolderPicker.notInstalledCheckbox.isSelected()));
        windowModeCombo.addActionListener(e -> {
            GameWindowMode mode = (GameWindowMode) windowModeCombo.getSelectedItem();
            detectionButton.setVisible(mode == GameWindowMode.DETECT);
            monitorPicker.setVisible(mode == GameWindowMode.MONITOR);
        });
    }

    // FIXME : Detection panel needs to store result

    @Override
    public void save() {
        /// Shared
        GameWindowMode windowMode = (GameWindowMode) windowModeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.gameWindowMode = windowMode;
//        if(windowMode == GameWindowMode.DETECT) {
//            if(detectionPanel.getLatestResult() == GameDetectionResult.SUCCESS);
//                SaveManager.settingsSaveFile.data.ga
//        }
        if (windowMode == GameWindowMode.MONITOR)
            SaveManager.settingsSaveFile.data.selectedMonitor = (github.zmilla93.gui.components.MonitorInfo) windowModeCombo.getSelectedItem();

//        private final GameDetectionPanel detectionPanel = new GameDetectionPanel();
//        private final MonitorPicker monitorPicker = new MonitorPicker();
//        private final HotkeyButton chatHotkeyButton = new HotkeyButton();
//        private final JButton explanationButton = new JButton("Install Folder Info");
//        private final JPanel explanationPanel = new POEInstallFolderExplanationPanel(true, false);
        /// Game Specific
    }

    @Override
    public void load() {
        GameWindowMode windowMode = SaveManager.settingsSaveFile.data.gameWindowMode;
        windowModeCombo.setSelectedItem(windowMode);
        if (windowMode == GameWindowMode.MONITOR)
            monitorPicker.setMonitor(SaveManager.settingsSaveFile.data.selectedMonitor);
    }

}
