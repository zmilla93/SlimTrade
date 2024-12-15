package github.zmilla93.gui.options.poe;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.GameWindowMode;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.MonitorInfo;
import github.zmilla93.gui.components.MonitorPicker;
import github.zmilla93.gui.components.poe.POEFolderPicker;
import github.zmilla93.gui.components.poe.POEInstallFolderExplanationPanel;
import github.zmilla93.gui.components.poe.Poe2OutgoingTradeHotkeyPanel;
import github.zmilla93.gui.components.poe.detection.GameDetectionButton;
import github.zmilla93.gui.managers.FrameManager;
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
    private final JCheckBox usingStashFoldersPoe1Checkbox = new JCheckBox("Using Stash Folders");
    private final JCheckBox usingStashFoldersPoe2Checkbox = new JCheckBox("Using Stash Folders");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2);
    private final AbstractOptionPanel poe1OptionPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel poe2OptionPanel = new AbstractOptionPanel(false, false);
    private final Poe2OutgoingTradeHotkeyPanel poe2OutgoingTradeHotkeyPanel = new Poe2OutgoingTradeHotkeyPanel();

    public PathOfExileOptionPanel() {
        windowModeCombo.addItem(GameWindowMode.DETECT);
        windowModeCombo.addItem(GameWindowMode.MONITOR);

        /// Shared
        addHeader("Path of Exile 1 & 2");
        addComponent(new ComponentPanel(new JLabel("Game Window Mode"), windowModeCombo));
        addComponent(detectionButton);
        addComponent(monitorPicker);
        addComponent(new ComponentPanel(new JLabel("Open Chat Hotkey"), chatHotkeyButton));
        addVerticalStrut();

        /// Path of Exile 1
        poe1OptionPanel.addComponent(usingStashFoldersPoe1Checkbox);
        addHeader(Game.PATH_OF_EXILE_1.getExplicitName());
        addComponent(poe1FolderPicker);
        addFullWidthComponent(poe1OptionPanel);
        addVerticalStrut();

        /// Path of Exile 2
        poe2OptionPanel.addComponent(usingStashFoldersPoe2Checkbox);
        addHeader(Game.PATH_OF_EXILE_2.toString());
        addComponent(poe2FolderPicker);
        addFullWidthComponent(poe2OptionPanel);
        addVerticalStrut();
        addFullWidthComponent(poe2OutgoingTradeHotkeyPanel);
        addVerticalStrut();

        /// Explain Install Files
        addHeader("More Info");
        addComponent(explanationButton);
        addComponent(explanationPanel);
        addListeners();
    }

    // FIXME: This feels a little hacky?
    private void refreshPanelVisibility() {
        poe1OptionPanel.setVisible(!poe1FolderPicker.notInstalledCheckbox.isSelected());
        poe2OptionPanel.setVisible(!poe2FolderPicker.notInstalledCheckbox.isSelected());
    }

    private void addListeners() {
        explanationButton.addActionListener(e -> explanationPanel.setVisible(!explanationPanel.isVisible()));
        poe1FolderPicker.notInstalledCheckbox.addActionListener(e -> refreshPanelVisibility());
        poe2FolderPicker.notInstalledCheckbox.addActionListener(e -> refreshPanelVisibility());
        windowModeCombo.addActionListener(e -> {
            GameWindowMode mode = (GameWindowMode) windowModeCombo.getSelectedItem();
            detectionButton.setVisible(mode == GameWindowMode.DETECT);
            monitorPicker.setVisible(mode == GameWindowMode.MONITOR);
        });
    }

    @Override
    public void save() {
        /// Shared
        GameWindowMode windowMode = (GameWindowMode) windowModeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.gameWindowMode = windowMode;
        if (windowMode == GameWindowMode.DETECT)
            if (detectionButton.getLatestResultWasSuccess()) {
//                SaveManager.settingsSaveFile.data.detectedGameBounds = detectionButton.getLatestResultWindow().clientBounds;
                POEWindow.setBoundsByWindowHandle(detectionButton.getLatestResultWindow().handle, true);
            }
        if (windowMode == GameWindowMode.MONITOR) {
            MonitorInfo monitor = monitorPicker.getSelectedMonitor();
            SaveManager.settingsSaveFile.data.selectedMonitor = monitorPicker.getSelectedMonitor();
            POEWindow.setBoundsByMonitor(monitor);
        }
        SaveManager.settingsSaveFile.data.poeChatHotkey = chatHotkeyButton.getData();
        /// Game Specific
        SaveManager.settingsSaveFile.data.notInstalledPoe1 = poe1FolderPicker.notInstalledCheckbox.isSelected();
        SaveManager.settingsSaveFile.data.notInstalledPoe2 = poe2FolderPicker.notInstalledCheckbox.isSelected();
        SaveManager.settingsSaveFile.data.installFolderPoe1 = poe1FolderPicker.getPathString();
        SaveManager.settingsSaveFile.data.installFolderPoe2 = poe2FolderPicker.getPathString();
        SaveManager.settingsSaveFile.data.usingStashFoldersPoe1 = usingStashFoldersPoe1Checkbox.isSelected();
        SaveManager.settingsSaveFile.data.usingStashFoldersPoe2 = usingStashFoldersPoe2Checkbox.isSelected();
        SaveManager.settingsSaveFile.data.poe2OutgoingTradeHotkey = poe2OutgoingTradeHotkeyPanel.hotkeyButton.getData();
        FrameManager.stashHelperContainerPoe1.updateLocation();
        FrameManager.stashHelperContainerPoe2.updateLocation();
        // FIXME : Need to reinit parsers if paths have changed.

    }

    @Override
    public void load() {
        /// Shared
        GameWindowMode windowMode = SaveManager.settingsSaveFile.data.gameWindowMode;
        windowModeCombo.setSelectedItem(windowMode);
        if (windowMode == GameWindowMode.MONITOR)
            monitorPicker.setMonitor(SaveManager.settingsSaveFile.data.selectedMonitor);
        /// Game Specific
        chatHotkeyButton.setData(SaveManager.settingsSaveFile.data.poeChatHotkey);
        poe1FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.notInstalledPoe1);
        poe2FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.notInstalledPoe2);
        poe1FolderPicker.setSelectedPath(SaveManager.settingsSaveFile.data.installFolderPoe1);
        poe2FolderPicker.setSelectedPath(SaveManager.settingsSaveFile.data.installFolderPoe2);
        usingStashFoldersPoe1Checkbox.setSelected(SaveManager.settingsSaveFile.data.usingStashFoldersPoe1);
        usingStashFoldersPoe2Checkbox.setSelected(SaveManager.settingsSaveFile.data.usingStashFoldersPoe1);
        poe2OutgoingTradeHotkeyPanel.hotkeyButton.setData(SaveManager.settingsSaveFile.data.poe2OutgoingTradeHotkey);
        refreshPanelVisibility();
    }

}
