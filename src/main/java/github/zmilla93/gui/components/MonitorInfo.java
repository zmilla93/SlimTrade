package github.zmilla93.gui.components;

import java.awt.*;
import java.util.Objects;

/**
 * Stores a simple version of the info stored in {@link GraphicsDevice} and {@link DisplayMode}.
 * Works nicely with JCombos. Contains utility functions for comparing window and monitor bounds.
 */
public class MonitorInfo {

    private static final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

    public final int id;
    public final boolean isFullScreenSupported;
    public final Rectangle bounds;
    public final int refreshRate;
    private transient String displayName;
    private static MonitorInfo[] cachedMonitors;
    private static Rectangle[] cachedMonitorBoundsArray;
    private static final PointerInfo mousePointer = MouseInfo.getPointerInfo();

    private MonitorInfo(GraphicsDevice device, int id) {
        this.id = id;
        DisplayMode displayMode = device.getDisplayMode();
        isFullScreenSupported = device.isFullScreenSupported();
        bounds = device.getDefaultConfiguration().getBounds();
        refreshRate = displayMode.getRefreshRate();
    }

    /// Actual monitor names are platform dependent, so names are displayed as "Monitor #".
    public String getDisplayName() {
        if (displayName == null) displayName = "Monitor " + id;
        return displayName;
    }

    /// Determines if a given monitor still exists within the current GraphicsEnvironment.
    public boolean exists() {
        int id = 0;
        for (GraphicsDevice device : graphicsEnvironment.getScreenDevices()) {
            MonitorInfo monitor = new MonitorInfo(device, id);
            if (monitor.equals(this)) return true;
            id++;
        }
        return false;
    }

    /// Returns all monitors in the current GraphicsEnvironment.
    public static synchronized MonitorInfo[] getAllMonitors(boolean allowCache) {
        if (allowCache && cachedMonitors != null) return cachedMonitors;
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
        MonitorInfo[] monitors = new MonitorInfo[devices.length];
        Rectangle[] boundsArray = new Rectangle[devices.length];
        for (int i = 0; i < devices.length; i++) {
            monitors[i] = new MonitorInfo(devices[i], i + 1);
            boundsArray[i] = devices[i].getDefaultConfiguration().getBounds();
        }
        cachedMonitors = monitors;
        cachedMonitorBoundsArray = boundsArray;
        return monitors;
    }

    /// Returns the bounds of all monitors in the current GraphicsEnvironment.
    private static Rectangle[] getAllMonitorBounds(boolean allowCache) {
        if (allowCache && cachedMonitorBoundsArray != null) return cachedMonitorBoundsArray;
        GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
        Rectangle[] boundsArray = new Rectangle[devices.length];
        for (int i = 0; i < devices.length; i++)
            boundsArray[i] = devices[i].getDefaultConfiguration().getBounds();
        cachedMonitorBoundsArray = boundsArray;
        return boundsArray;
    }

    /**
     * Return the bounds of the monitor at the target point,
     * or the bounds of the first connected monitor if the target point is off-screen.
     */
    public static Rectangle getMonitorBoundsFromPoint(Point point) {
        Rectangle[] monitorBoundsArray = getAllMonitorBounds(true);
        for (Rectangle rect : monitorBoundsArray)
            if (rect.contains(point)) return rect;
        return monitorBoundsArray[0];
    }

    /**
     * Checks if a given rect is fully contained within any single monitor.
     * Returns false if bounds are spanning between two monitors.
     */
    public static boolean isRectWithinAMonitor(Rectangle windowBounds) {
        for (Rectangle monitorBounds : getAllMonitorBounds(true)) {
            if (monitorBounds.contains(windowBounds)) return true;
        }
        return false;
    }


    public static void applyWindowLock(Rectangle windowBounds) {
        applyWindowLock(windowBounds, getMonitorBoundsFromPoint(mousePointer.getLocation()));
    }

    /**
     * Checks which monitor is at the center of the target bounds, then ensures the target bounds are
     * fully within that monitor. Uses the first connected monitor if center point is off-screen.
     */
    public static void lockBoundsToCurrentMonitor(Rectangle windowBounds) {
        Point center = new Point((int) Math.round(windowBounds.getCenterX()),
                (int) Math.round(windowBounds.getCenterY()));
        applyWindowLock(windowBounds, getMonitorBoundsFromPoint(center));
    }

    // FIXME : Move this somewhere more logical? It technically is abstracted away from MonitorInfo
    // FIXME : Reuse this code for window locking for movable windows
    public static void applyWindowLock(Rectangle windowBounds, Rectangle confinementRect) {
        /// X
        if (windowBounds.x > confinementRect.x + confinementRect.width - windowBounds.width)
            windowBounds.x = confinementRect.x + confinementRect.width - windowBounds.width;
        if (windowBounds.x < confinementRect.x) windowBounds.x = confinementRect.x;
        /// Y
        if (windowBounds.y > confinementRect.y + confinementRect.height - windowBounds.height)
            windowBounds.y = confinementRect.y + confinementRect.height - windowBounds.height;
        if (windowBounds.y < confinementRect.y) windowBounds.y = confinementRect.y;
//        return windowBounds;
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
