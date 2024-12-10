package com.slimtrade.gui.components.slimtrade.combos;

import com.slimtrade.gui.components.MonitorInfo;

import javax.swing.*;
import java.util.ArrayList;

//FIXME: This could be made generic
public class MonitorCombo extends JComboBox<MonitorInfo> {

    public MonitorCombo() {
        refreshMonitorList();
    }

    public void setMonitorList(ArrayList<MonitorInfo> monitors) {
        removeAllItems();
        for (MonitorInfo monitor : monitors)
            addItem(monitor);
    }

    public void refreshMonitorList() {
        removeAllItems();
        for (MonitorInfo monitor : MonitorInfo.getAllMonitors())
            addItem(monitor);
    }

}
