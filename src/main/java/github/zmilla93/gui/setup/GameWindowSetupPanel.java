package github.zmilla93.gui.setup;

import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.GameWindowMode;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CardPanel;
import github.zmilla93.gui.components.MonitorInfo;
import github.zmilla93.gui.components.MonitorPicker;
import github.zmilla93.gui.components.poe.ResultLabel;
import github.zmilla93.gui.components.poe.detection.GameDetectionButton;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class GameWindowSetupPanel extends AbstractSetupPanel {

    private final JRadioButton detectRadioButton = new JRadioButton("Automatic");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select a monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create a screen region");

    private final GameDetectionButton detectionButton = new GameDetectionButton();
    private final MonitorPicker monitorPicker = new MonitorPicker();
    // TODO: Screen Region ?

    // Card Panel
    private final CardPanel cardPanel = new CardPanel();
    private final AbstractOptionPanel detectPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel monitorPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel screenRegionPanel = new AbstractOptionPanel(false, false);

    public GameWindowSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(detectRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(screenRegionRadioButton);

        // Detection Method Panel
        addHeader("Window Location");
        addComponent(new JLabel("How should SlimTrade determine the window location of Path of Exile?"));
        addComponent(new ResultLabel("This aligns the UI for both games."));
        addVerticalStrutSmall();
        if (Platform.current == Platform.WINDOWS)
            addComponent(detectRadioButton);
        addComponent(monitorRadioButton);
        addComponent(screenRegionRadioButton);
        addVerticalStrut();

        // Detect Panel
        detectPanel.addHeader("Detect Window");
        detectPanel.addComponent(detectionButton);

        // Monitor Panel
        monitorPanel.addHeader("Monitor Selection");
        monitorPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Requires using Windowed Fullscreen"));
        monitorPanel.addComponent(monitorPicker);

        // Screen Region Panel
        screenRegionPanel.addHeader("Screen Region");
        screenRegionPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Coming soon (maybe)."));
        screenRegionPanel.addLabel("Only required for windowed mode if other options fail.");
        screenRegionPanel.addLabel("If this affects you, let me know on GitHub or Discord.");

        // Card Panel
        cardPanel.add(detectPanel);
        cardPanel.add(monitorPanel);
        cardPanel.add(screenRegionPanel);
        addFullWidthComponent(cardPanel);

        if (Platform.current == Platform.WINDOWS) detectRadioButton.setSelected(true);
        else monitorRadioButton.setSelected(true);
    }

    @Override
    protected void addComponentListeners() {
        detectRadioButton.addActionListener(e -> {
            cardPanel.showCard(detectPanel);
            runSetupValidation();
        });
        monitorRadioButton.addActionListener(e -> {
            cardPanel.showCard(monitorPanel);
            runSetupValidation();
        });
        screenRegionRadioButton.addActionListener(e -> {
            cardPanel.showCard(screenRegionPanel);
            runSetupValidation();
        });
        detectionButton.addGameDetectionTestListener((result, handle) -> {
            runSetupValidation();
            ZUtil.packComponentWindow(GameWindowSetupPanel.this);
        });
    }

    @Override
    public void initializeComponents() {
        GameWindowMode mode = SaveManager.settingsSaveFile.data.gameWindowMode;
        switch (mode) {
            case DETECT:
                detectRadioButton.setSelected(true);
                cardPanel.showCard(detectPanel);
                break;
            case MONITOR:
                monitorRadioButton.setSelected(true);
                cardPanel.showCard(monitorPanel);
                MonitorInfo monitor = SaveManager.settingsSaveFile.data.selectedMonitor;
                if (monitor != null) monitorPicker.setMonitor(monitor);
                break;
            case SCREEN_REGION:
                screenRegionRadioButton.setSelected(true);
                cardPanel.showCard(screenRegionPanel);
                break;
            case UNSET:
            default:
                if (Platform.current == Platform.WINDOWS) {
                    detectRadioButton.setSelected(true);
                    cardPanel.showCard(detectPanel);
                } else {
                    monitorRadioButton.setSelected(true);
                    cardPanel.showCard(monitorPanel);
                }
        }
    }

    @Override
    public boolean isSetupValid() {
        if (detectRadioButton.isSelected() && detectionButton.getLatestResultWasSuccess()) return true;
        else if (monitorRadioButton.isSelected()) return true;
            // TODO: implement screen region
        else if (screenRegionRadioButton.isSelected()) return false;
        return false;
    }

    @Override
    public void applyCompletedSetup() {
        if (detectRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.DETECT;
            SaveManager.settingsSaveFile.data.detectedGameBounds = detectionButton.getLatestResultWindow().clientBounds;
        } else if (monitorRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.MONITOR;
            SaveManager.settingsSaveFile.data.selectedMonitor = monitorPicker.getSelectedMonitor();
        } else if (screenRegionRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.SCREEN_REGION;
            // FIXME : Save region
        }
    }

}
