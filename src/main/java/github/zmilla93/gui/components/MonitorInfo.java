package github.zmilla93.gui.components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores a simple version of the info stored in {@link GraphicsDevice} and {@link DisplayMode}.
 * Formatted toString method to allow for use with JComboBoxes.
 * Only usable from a static context due to id handling.
 */
public class MonitorInfo {

    private static final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
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
        for (GraphicsDevice device : graphicsEnvironment.getScreenDevices()) monitors.add(new MonitorInfo(device));
        return monitors;
    }

    public boolean exists() {
        for (GraphicsDevice device : graphicsEnvironment.getScreenDevices()) {
            MonitorInfo monitor = new MonitorInfo(device);
            if (monitor.equals(this)) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        MonitorInfo info = (MonitorInfo) object;
        return isFullScreenSupported == info.isFullScreenSupported && refreshRate == info.refreshRate && Objects.equals(bounds, info.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isFullScreenSupported, bounds, refreshRate);
    }

}
