package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.jna.NativePoeWindow;
import com.slimtrade.core.jna.NativeWindow;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.GameDetectionMethod;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CardPanel;
import com.slimtrade.gui.components.ComponentPanel;
import com.slimtrade.gui.components.MonitorIdentificationFrame;
import com.slimtrade.gui.components.MonitorInfo;
import com.slimtrade.gui.components.slimtrade.ResultLabel;
import com.slimtrade.gui.components.slimtrade.combos.MonitorCombo;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.util.ArrayList;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatic");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select a monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create a screen region");

    // Automatic
    private final JLabel automaticTestNotRun = new JLabel("Click to set the game window location.");
    private final ResultLabel automaticTestFail = new ResultLabel(ResultStatus.DENY, "No window found. Ensure Path of Exile 1 or 2 is running.");
    private final ResultLabel automaticTestMinimized = new ResultLabel(ResultStatus.INDETERMINATE, "Game is minimized.");
    private final ResultLabel automaticTestSuccess = new ResultLabel(ResultStatus.APPROVE, "Game window location set.");
    private final ResultLabel automaticTestNotSupported = new ResultLabel(ResultStatus.DENY, "Not supported on " + Platform.current + ".");
    private final CardPanel automaticResultsCardPanel = new CardPanel(automaticTestNotRun, automaticTestSuccess, automaticTestFail, automaticTestMinimized, automaticTestNotSupported);
    private final JButton automaticTestButton = new JButton("Detect");
    private final ResultLabel automaticTestLabel = new ResultLabel(ResultStatus.NEUTRAL, "Verify game detection is working.");
    private boolean automaticTestResult = false;

    // Monitor
    private final JButton identifyButton = new JButton("Identify");
    private final MonitorCombo monitorCombo = new MonitorCombo();

    // Card Panel
    private final CardPanel cardPanel = new CardPanel();
    private final AbstractOptionPanel automaticPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel monitorPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel screenRegionPanel = new AbstractOptionPanel(false, false);

    public GameDetectionSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(screenRegionRadioButton);

        // Detection Method Panel
        addHeader("Game Window Location");
        addComponent(new JLabel("How should SlimTrade determine the Path of Exile window location?"));
        addComponent(new JLabel("This is used to align UI Elements."));
        addVerticalStrutSmall();
        if (Platform.current == Platform.WINDOWS)
            addComponent(automaticRadioButton);
        addComponent(monitorRadioButton);
        addComponent(screenRegionRadioButton);
        addVerticalStrut();

        // Automatic Panel
        // FIXME : Run detection at start
        // FIXME : Make it so you can't undo test completion
        automaticPanel.addHeader("Detect Window");
        automaticPanel.addComponent(new ComponentPanel(automaticTestButton, automaticResultsCardPanel));

        // Monitor Panel
        monitorPanel.addHeader("Monitor Selection");
        // FIXME : Make monitor selector its own component
        monitorPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Requires using Windowed Fullscreen"));
        monitorPanel.addComponent(new ComponentPanel(identifyButton, monitorCombo));

        // Screen Region Panel
        screenRegionPanel.addHeader("Screen Region");
        screenRegionPanel.addLabel("Coming soon!");

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
        automaticTestButton.addActionListener(e -> {
            automaticTestResult = false;
            // FIXME: Windows only
            if (Platform.current != Platform.WINDOWS) {
                automaticResultsCardPanel.showCard(automaticTestNotSupported);
                return;
            }
            WinDef.HWND handle = NativePoeWindow.findPathOfExileWindow();
            if (handle == null) automaticResultsCardPanel.showCard(automaticTestFail);
            else {
                NativeWindow window = new NativeWindow(handle);
                if (window.minimized) {
                    automaticResultsCardPanel.showCard(automaticTestMinimized);
                } else {
                    automaticResultsCardPanel.showCard(automaticTestSuccess);
                    PoeIdentificationFrame.identify(window.clientBounds);
                    automaticTestResult = true;
                }
            }
            runSetupValidation();
            ZUtil.packComponentWindow(GameDetectionSetupPanel.this);
        });
        identifyButton.addActionListener(e -> {
            MonitorInfo selectedMonitor = (MonitorInfo) monitorCombo.getSelectedItem();
            ArrayList<MonitorInfo> monitors = MonitorIdentificationFrame.visuallyIdentifyMonitors();
            monitorCombo.setMonitorList(monitors);
            if (selectedMonitor != null) monitorCombo.setSelectedItem(selectedMonitor);
            runSetupValidation();
        });
    }

    private void initializeComponents(GameDetectionMethod method) {
        switch (method) {
            case AUTOMATIC:
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
        GameDetectionMethod method = SaveManager.settingsSaveFile.data.gameDetectionMethod;
        initializeComponents(method);
        MonitorInfo monitor = SaveManager.settingsSaveFile.data.selectedMonitor;
        if (monitor != null) monitorCombo.setSelectedItem(SaveManager.settingsSaveFile.data.selectedMonitor);
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
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.AUTOMATIC;
        } else if (monitorRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.MONITOR;
            SaveManager.settingsSaveFile.data.selectedMonitor = (MonitorInfo) monitorCombo.getSelectedItem();
        } else if (screenRegionRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.SCREEN_REGION;
            // FIXME : Save region
        }
    }

}
