package com.slimtrade.gui.components;

import com.slimtrade.gui.components.slimtrade.combos.MonitorCombo;

import javax.swing.*;
import java.util.ArrayList;

public class MonitorPicker extends ComponentPanel {

    public final JButton identifyButton = new JButton("Identify");
    public final MonitorCombo monitorCombo = new MonitorCombo();

    public MonitorPicker() {
        add(identifyButton);
        add(monitorCombo);
        identifyButton.addActionListener(e -> {
            MonitorInfo selectedMonitor = (MonitorInfo) monitorCombo.getSelectedItem();
            ArrayList<MonitorInfo> monitors = MonitorIdentificationFrame.visuallyIdentifyMonitors();
            monitorCombo.setMonitorList(monitors);
            if (selectedMonitor != null) monitorCombo.setSelectedItem(selectedMonitor);
        });
    }

    public void setMonitor(MonitorInfo monitor) {
        monitorCombo.setSelectedItem(monitor);
    }

    public MonitorInfo getSelectedMonitor() {
        return (MonitorInfo) monitorCombo.getSelectedItem();
    }

}
