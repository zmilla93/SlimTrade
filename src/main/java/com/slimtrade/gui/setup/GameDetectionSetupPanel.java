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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatic");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select a monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create a screen region");

    // Automatic
    private static final String automaticTestFail = "Game window not found. Make sure Path of Exile 1 or 2 is running.";
    private static final String automaticTestSuccess = "Game window detected!";
    private final JButton automaticTestButton = new JButton("Detect");
    private final ResultLabel automaticTestLabel = new ResultLabel(ResultStatus.NEUTRAL, "Verify game detection is working.");

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

        AbstractOptionPanel methodPanel = new AbstractOptionPanel(false, false);
        contentPanel.add(methodPanel, BorderLayout.CENTER);

        // Detection Method Panel
        methodPanel.addHeader("Game Window Detection");
        methodPanel.addComponent(new JLabel("How should SlimTrade know where the Path of Exile game window is located?"));
        methodPanel.addVerticalStrutSmall();
        if (Platform.current == Platform.WINDOWS)
            methodPanel.addComponent(automaticRadioButton);
        methodPanel.addComponent(monitorRadioButton);
        methodPanel.addComponent(screenRegionRadioButton);
        methodPanel.addVerticalStrut();

        // Automatic Panel
        // FIXME : Run detection at start
        // FIXME : Make it so you can't undo test completion
        automaticPanel.addHeader("Detection Test");
        automaticPanel.addComponent(new ComponentPanel(automaticTestButton, automaticTestLabel));

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
        methodPanel.addFullWidthComponent(cardPanel);

        if (Platform.current == Platform.WINDOWS) automaticRadioButton.setSelected(true);
        else monitorRadioButton.setSelected(true);

        addListeners();
    }

    private void addListeners() {
        automaticRadioButton.addActionListener(e -> cardPanel.showCard(automaticPanel));
        monitorRadioButton.addActionListener(e -> cardPanel.showCard(monitorPanel));
        screenRegionRadioButton.addActionListener(e -> cardPanel.showCard(screenRegionPanel));
        automaticTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert SwingUtilities.isEventDispatchThread();
                // FIXME: Display more info.
                WinDef.HWND handle = NativePoeWindow.findPathOfExileWindow();
                if (handle == null) automaticTestLabel.setText(ResultStatus.DENY, automaticTestFail);
                else {
                    NativeWindow window = new NativeWindow(handle);
                    automaticTestLabel.setText(ResultStatus.APPROVE, automaticTestSuccess);
                    PoeIdentificationFrame.identify(window.clientBounds);
                }
                ZUtil.packComponentWindow(GameDetectionSetupPanel.this);
            }
        });
        automaticTestButton.addActionListener(e -> NativePoeWindow.findPathOfExileWindow(window -> {

        }));
        identifyButton.addActionListener(e -> {
            MonitorInfo selectedMonitor = (MonitorInfo) monitorCombo.getSelectedItem();
            ArrayList<MonitorInfo> monitors = MonitorIdentificationFrame.visuallyIdentifyMonitors();
            monitorCombo.setMonitorList(monitors);
            if (selectedMonitor != null) monitorCombo.setSelectedItem(selectedMonitor);
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
        // FIXME : Null check?
        monitorCombo.setSelectedItem(SaveManager.settingsSaveFile.data.selectedMonitor);
    }

    @Override
    public boolean isSetupValid() {
        // FIXME: this
        return true;
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
