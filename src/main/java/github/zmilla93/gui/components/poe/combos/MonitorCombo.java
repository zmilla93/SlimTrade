package github.zmilla93.gui.components.poe.combos;

import github.zmilla93.core.data.MonitorInfo;

import javax.swing.*;

//FIXME: This could be made generic
public class MonitorCombo extends JComboBox<MonitorInfo> {

    public MonitorCombo() {
        refreshMonitorList();
    }

    public void setMonitorList(MonitorInfo[] monitors) {
        removeAllItems();
        for (MonitorInfo monitor : monitors)
            addItem(monitor);
    }

    public void refreshMonitorList() {
        removeAllItems();
        for (MonitorInfo monitor : MonitorInfo.getAllMonitors(false))
            addItem(monitor);
    }

}
