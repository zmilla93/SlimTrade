package com.slimtrade.gui.components;

import java.awt.*;
import java.util.ArrayList;

/**
 * Stores a simple version of the info stored in {@link GraphicsDevice} and {@link DisplayMode}.
 * Formatted toString method to allow for use with JComboBoxes.
 * Only usable from a static context due to id handling.
 */
public class MonitorInfo {

    private static int CURRENT_ID;

    public final int id;
    public final boolean isFullScreenSupported;
    public final Rectangle bounds;
    public final int refreshRate;
    private transient String displayName;

    private MonitorInfo(GraphicsDevice device) {
        DisplayMode displayMode = device.getDisplayMode();
        id = MonitorInfo.CURRENT_ID;
        isFullScreenSupported = device.isFullScreenSupported();
        bounds = device.getDefaultConfiguration().getBounds();
        refreshRate = displayMode.getRefreshRate();
        MonitorInfo.CURRENT_ID++;
    }

    public String getDisplayName() {
        if (displayName == null) displayName = "Monitor " + id + " [" + bounds.width + "x" + bounds.height + "]";
        return displayName;
    }

    public static synchronized ArrayList<MonitorInfo> getAllMonitors() {
        MonitorInfo.CURRENT_ID = 1;
        ArrayList<MonitorInfo> monitors = new ArrayList<>();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (GraphicsDevice device : ge.getScreenDevices()) monitors.add(new MonitorInfo(device));
        return monitors;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

}
