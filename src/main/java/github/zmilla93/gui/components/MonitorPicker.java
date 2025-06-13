package github.zmilla93.gui.components;

import github.zmilla93.core.data.MonitorInfo;
import github.zmilla93.gui.components.poe.combos.MonitorCombo;

import javax.swing.*;

public class MonitorPicker extends ComponentPanel {

    public final JButton identifyButton = new JButton("Identify");
    public final MonitorCombo monitorCombo = new MonitorCombo();

    public MonitorPicker() {
        add(identifyButton);
        add(monitorCombo);
        identifyButton.addActionListener(e -> {
            MonitorInfo selectedMonitor = (MonitorInfo) monitorCombo.getSelectedItem();
            MonitorInfo[] monitors = MonitorIdentificationFrame.visuallyIdentifyMonitors();
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
