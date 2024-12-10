package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.ResultStatus;
import com.slimtrade.core.jna.NativeWindow;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CardPanel;
import com.slimtrade.gui.components.ComponentPanel;
import com.slimtrade.gui.components.MonitorIdentificationFrame;
import com.slimtrade.gui.components.MonitorInfo;
import com.slimtrade.gui.components.slimtrade.ResultLabel;
import com.slimtrade.gui.components.slimtrade.combos.MonitorCombo;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatically");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select Monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create Screen Region");

    // Automatic
    private static final String automaticTestFail = "Game window not found. Make sure Path of Exile 1 or 2 is running.";
    private static final String automaticTestSuccess = "Game window detected!";
    private final JButton automaticTestButton = new JButton("Detect");
    private final ResultLabel automaticTestLabel = new ResultLabel(ResultStatus.NEUTRAL, "Verify game detection is working.");

    // Monitor
    private final MonitorCombo monitorCombo = new MonitorCombo();
    private final JButton identifyMonitorsButton = new JButton("Identify Monitors");

    // Card Panel
    private final CardPanel cardPanel = new CardPanel();
    private final AbstractOptionPanel automaticPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel monitorPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel screenRegionPanel = new AbstractOptionPanel(false, false);

    public GameDetectionSetupPanel(JButton nextButton) {
        super(nextButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(screenRegionRadioButton);

        AbstractOptionPanel methodPanel = new AbstractOptionPanel(false, false);
        contentPanel.add(methodPanel, BorderLayout.CENTER);

        // Detection Method Panel
        methodPanel.addHeader("Game Window Detection");
        methodPanel.addComponent(new JLabel("How should SlimTrade detect where the Path of Exile game window is?"));
        methodPanel.addVerticalStrutSmall();
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
        monitorPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Requires using Windowed Fullscreen"));
        monitorPanel.addComponent(new ComponentPanel(monitorCombo, identifyMonitorsButton));

        // Screen Region Panel
        screenRegionPanel.addHeader("Create Screen Region");

        // Card Panel
        cardPanel.add(automaticPanel);
        cardPanel.add(monitorPanel);
        cardPanel.add(screenRegionPanel);
        methodPanel.addFullWidthComponent(cardPanel);

        if (Platform.current == Platform.WINDOWS) {
            automaticRadioButton.setSelected(true);
        } else {
            monitorRadioButton.setSelected(true);
        }
        addListeners();
    }

    private void addListeners() {
        automaticRadioButton.addActionListener(e -> cardPanel.showCard(automaticPanel));
        monitorRadioButton.addActionListener(e -> cardPanel.showCard(monitorPanel));
        screenRegionRadioButton.addActionListener(e -> cardPanel.showCard(screenRegionPanel));
        automaticTestButton.addActionListener(e -> NativeWindow.findPathOfExileWindow(window -> {
            assert SwingUtilities.isEventDispatchThread();
            if (window == null) automaticTestLabel.setText(ResultStatus.DENY, automaticTestFail);
            else automaticTestLabel.setText(ResultStatus.APPROVE, automaticTestSuccess);
            ZUtil.packComponentWindow(this);
        }));
        identifyMonitorsButton.addActionListener(e -> {
            ArrayList<MonitorInfo> monitors = MonitorIdentificationFrame.visuallyIdentifyMonitors();
            monitorCombo.setMonitorList(monitors);
        });
    }

    @Override
    public boolean isSetupValid() {
        // FIXME: this
        return true;
    }

    @Override
    public void applyCompletedSetup() {
        // FIXME: this
    }

}
