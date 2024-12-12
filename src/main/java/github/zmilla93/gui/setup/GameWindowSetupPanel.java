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
import github.zmilla93.gui.components.poe.detection.GameDetectionPanel;
import github.zmilla93.gui.components.poe.detection.GameDetectionResult;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class GameWindowSetupPanel extends AbstractSetupPanel {

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatic");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select a monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create a screen region");

    // Automatic
    private final GameDetectionPanel detectionPanel = new GameDetectionPanel();
    private boolean automaticTestResult = false;

    // Monitor
    private final MonitorPicker monitorPicker = new MonitorPicker();

    // Screen Region
    // TODO: Screen Region

    // Card Panel
    private final CardPanel cardPanel = new CardPanel();
    private final AbstractOptionPanel automaticPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel monitorPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel screenRegionPanel = new AbstractOptionPanel(false, false);

    public GameWindowSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(screenRegionRadioButton);

        // Detection Method Panel
        addHeader("Window Location");
        addComponent(new JLabel("How should SlimTrade determine the window location of Path of Exile?"));
        addComponent(new ResultLabel("This aligns the UI for both games."));
        addVerticalStrutSmall();
        if (Platform.current == Platform.WINDOWS)
            addComponent(automaticRadioButton);
        addComponent(monitorRadioButton);
        addComponent(screenRegionRadioButton);
        addVerticalStrut();

        // Automatic Panel
        automaticPanel.addHeader("Detect Window");
        automaticPanel.addComponent(detectionPanel);

        // Monitor Panel
        monitorPanel.addHeader("Monitor Selection");
        monitorPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Requires using Windowed Fullscreen"));
        monitorPanel.addComponent(monitorPicker);

        // Screen Region Panel
        screenRegionPanel.addHeader("Screen Region");
        screenRegionPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Coming soon (maybe)."));
        screenRegionPanel.addLabel("This is only required for Mac & Linux users playing in windowed mode.");
        screenRegionPanel.addLabel("If that means you, let me know on GitHub or Discord.");

        // Card Panel
        cardPanel.add(automaticPanel);
        cardPanel.add(monitorPanel);
        cardPanel.add(screenRegionPanel);
        addFullWidthComponent(cardPanel);

        if (Platform.current == Platform.WINDOWS) automaticRadioButton.setSelected(true);
        else monitorRadioButton.setSelected(true);
    }

    private void addListeners() {
        automaticRadioButton.addActionListener(e -> {
            cardPanel.showCard(automaticPanel);
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
        detectionPanel.addGameDetectionTestListener((result, handle) -> {
            automaticTestResult = result == GameDetectionResult.SUCCESS;
            runSetupValidation();
            ZUtil.packComponentWindow(GameWindowSetupPanel.this);
        });
    }

    private void initializeComponents(GameWindowMode method) {
        switch (method) {
            case DETECT:
                automaticRadioButton.setSelected(true);
                cardPanel.showCard(automaticPanel);
                break;
            case MONITOR:
                monitorRadioButton.setSelected(true);
                cardPanel.showCard(monitorPanel);
                break;
            case SCREEN_REGION:
                screenRegionRadioButton.setSelected(true);
                cardPanel.showCard(screenRegionPanel);
                break;
            case UNSET:
            default:
                if (Platform.current == Platform.WINDOWS) {
                    automaticRadioButton.setSelected(true);
                    cardPanel.showCard(automaticPanel);
                } else {
                    monitorRadioButton.setSelected(true);
                    cardPanel.showCard(monitorPanel);
                }
        }
    }

    @Override
    public void initializeComponents() {
        GameWindowMode method = SaveManager.settingsSaveFile.data.gameWindowMode;
        initializeComponents(method);
        MonitorInfo monitor = SaveManager.settingsSaveFile.data.selectedMonitor;
        if (monitor != null) monitorPicker.setMonitor(monitor);
    }

    @Override
    public void addComponentListeners() {
        addListeners();
    }

    @Override
    public boolean isSetupValid() {
        if (automaticRadioButton.isSelected() && automaticTestResult) return true;
        else if (monitorRadioButton.isSelected()) return true;
            // TODO: implement screen region
        else if (screenRegionRadioButton.isSelected()) return false;
        return false;
    }

    @Override
    public void applyCompletedSetup() {
        if (automaticRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.DETECT;
        } else if (monitorRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.MONITOR;
            SaveManager.settingsSaveFile.data.selectedMonitor = monitorPicker.getSelectedMonitor();
        } else if (screenRegionRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameWindowMode = GameWindowMode.SCREEN_REGION;
            // FIXME : Save region
        }
    }

}