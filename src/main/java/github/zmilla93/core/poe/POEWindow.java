package github.zmilla93.core.poe;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.jna.CustomUser32;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.gui.components.MonitorInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * A platform independent representation of the Path of Exile game window.
 * Used to calculate the screen location of in game UI elements, or to pin things relative to the game window.
 * Bounds can be updated via different {@link GameWindowMode}s.
 * Attach a {@link POEWindowListener} to listen for events.
 */
// TODO : Could also add the option to manually define the game window region to support Mac/Linux users who play in windowed mode.
public class POEWindow {

    private static final Rectangle2D.Float POE_1_PERCENT_STASH_NO_FOLDERS = ScaledRect.getPercentRect(
            new Rectangle(15, 133, 634, 634),
            new Rectangle(0, 0, 1920, 1080));

    private static final Rectangle2D.Float POE_1_PERCENT_STASH_WITH_FOLDERS = ScaledRect.getPercentRect(
            new Rectangle(15, 167, 634, 634),
            new Rectangle(0, 0, 1920, 1080));

    private static final Rectangle2D.Float POE_2_PERCENT_STASH_NO_FOLDERS = ScaledRect.getPercentRect(
            new Rectangle(14, 117, 634, 634),
            new Rectangle(0, 0, 1920, 1080));

    private static final Rectangle2D.Float POE_2_PERCENT_STASH_WITH_FOLDERS = ScaledRect.getPercentRect(
            new Rectangle(14, 160, 634, 634),
            new Rectangle(0, 0, 1920, 1080));

    private static final float POE_1_PERCENT_HELPER_OFFSET_NO_FOLDERS = ScaledInt.getPercentValue(40, 1080);
    private static final float POE_1_PERCENT_HELPER_OFFSET_WITH_FOLDERS = ScaledInt.getPercentValue(75, 1080);
    private static final float POE_2_PERCENT_HELPER_OFFSET_NO_FOLDERS = ScaledInt.getPercentValue(40, 1080);
    private static final float POE_2_PERCENT_HELPER_OFFSET_WITH_FOLDERS = ScaledInt.getPercentValue(75, 1080);

    /// Currently set game bounds
    private static Rectangle gameBounds;
    private static Point centerOfScreen;

    /// Calculated based on the current game bounds
    // Path of Exile 1
    private static Rectangle poe1StashBoundsWithFolders;
    private static Rectangle poe1StashBoundsNoFolders;
    private static Dimension poe1StashCellSize;
    private static Dimension poe1StashCellSizeQuad;
    private static int poe1StashHelperOffsetWithFolders;
    private static int poe1StashHelperOffsetNoFolders;
    // Path of Exile 2
    private static Rectangle poe2StashBoundsNoFolders;
    private static Rectangle poe2StashBoundsWithFolders;
    private static Dimension poe2StashCellSize;
    private static Dimension poe2StashCellSizeQuad;
    private static int poe2StashHelperOffsetWithFolders;
    private static int poe2StashHelperOffsetNoFolders;

    /// The components most recently used to update the game bounds
//    private static NativePoeWindow currentGameWindow;
    private static WinDef.HWND currentGameHandle;
    private static MonitorInfo currentMonitor;

    /// The things that care about the game bounds
    private static final ArrayList<POEWindowListener> listeners = new ArrayList<>();

    public static WinDef.HWND getGameHandle() {
        return currentGameHandle;
    }

    public static void setBoundsByWindowHandle(WinDef.HWND handle) {
        setBoundsByWindowHandle(handle, false);
    }

    public static void setBoundsByWindowHandle(WinDef.HWND handle, boolean forceUpdate) {
        assert SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.DETECT;
        assert handle != null;
        if (handle.equals(currentGameHandle) && !forceUpdate) return;
        if (CustomUser32.INSTANCE.IsIconic(handle)) return;
        currentGameHandle = handle;
        POEWindow.gameBounds = WindowUtils.getWindowLocationAndSize(handle).getBounds();
        calculateNewGameBounds();
    }

    public static void setBoundsByMonitor(MonitorInfo monitor) {
        assert SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.MONITOR;
        assert monitor != null;
        if (monitor.equals(currentMonitor)) return;
        currentMonitor = monitor;
        POEWindow.gameBounds = monitor.bounds;
        calculateNewGameBounds();
    }

    private static void setBoundsByRet(Rectangle rect) {
        POEWindow.gameBounds = rect;
        calculateNewGameBounds();
    }

    private static void setBoundsFallback() {
        MonitorInfo monitor = MonitorInfo.getAllMonitors(false)[0];
        POEWindow.gameBounds = monitor.bounds;
        calculateNewGameBounds();
    }

    private static void calculateNewGameBounds() {
        centerOfScreen = new Point(gameBounds.x + gameBounds.width / 2, gameBounds.y + gameBounds.height / 2);
        calculatePoe1UIData();
        calculatePoe2UIData();
        for (POEWindowListener listener : listeners) listener.onGameBoundsChange();
    }

    /**
     * Attempts to update the game bounds using user's current settings.
     * This should only be used to manually force updates, like on launch or when detection method changes.
     */
    public static void forceGameBoundsRefresh() {
        GameWindowMode method = SaveManager.settingsSaveFile.data.gameWindowMode;
        switch (method) {
            case DETECT:
                /// NOTE : This currently shouldn't be reachable on non Windows platforms, just future proofing.
                if (Platform.WINDOWS == Platform.current) {
                    Rectangle savedBounds = SaveManager.settingsSaveFile.data.detectedGameBounds;
                    if (savedBounds != null) setBoundsByRet(savedBounds);
                }
                break;
            case MONITOR:
                MonitorInfo monitor = SaveManager.settingsSaveFile.data.selectedMonitor;
                if (monitor.exists()) setBoundsByMonitor(monitor);
                else setBoundsFallback();
                break;
            case SCREEN_REGION:
                // FIXME: Implement this!
                setBoundsFallback();
                break;
            case UNSET:
            default:
                setBoundsFallback();
                break;
        }
    }

    /**
     * Center a window relative to the current "game bounds", which could be the
     * actual game window bounds, a monitor bounds, or a screen region. Also ensures
     * that the window is fully within the bounds of a single monitor.
     */
    public static void centerWindow(Window window) {
        assert SwingUtilities.isEventDispatchThread();
        if (gameBounds == null) {
            window.setLocationRelativeTo(null);
        } else {
            Rectangle targetWindowBounds = window.getBounds();
            int halfWidth = window.getWidth() / 2;
            int halfHeight = window.getHeight() / 2;
            targetWindowBounds.x = centerOfScreen.x - halfWidth;
            targetWindowBounds.y = centerOfScreen.y - halfHeight;
            if (!MonitorInfo.isRectWithinAMonitor(targetWindowBounds))
                MonitorInfo.lockBoundsToCurrentMonitor(targetWindowBounds);
            window.setLocation(targetWindowBounds.getLocation());
        }
    }

    public static void windowToCorner(Window window) {
        windowToCorner(window, Anchor.TOP_LEFT);
    }

    /// Moves a window to the respective corner of the game window, while also
    /// ensuring the window is fully visible on a monitor.
    public static void windowToCorner(Window window, Anchor anchor) {
        Rectangle targetBounds = window.getBounds();
        targetBounds.setLocation(gameBounds.getLocation());
        if (anchor.isRightSide()) targetBounds.x += gameBounds.width - window.getWidth();
        if (anchor.isBottomSide()) targetBounds.y += gameBounds.height - window.getHeight();
        if (!MonitorInfo.isRectWithinAMonitor(targetBounds))
            MonitorInfo.lockBoundsToCurrentMonitor(targetBounds);
        window.setLocation(targetBounds.getLocation());
    }

    // Path of Exile UI Info Getters
    // FIXME : Switch to using Game parameter.
    // FIXME : Should do this check in calculate, not getter. Same with helper offset, and ditto for POE2.
    public static Rectangle getPoe1StashBonds() {
        if (SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder) {
            return poe1StashBoundsWithFolders;
        } else return poe1StashBoundsNoFolders;
    }

    public static Dimension getPoe1StashCellSize() {
        return poe1StashCellSize;
    }

    public static Dimension getPoe1StashCellSizeQuad() {
        return poe1StashCellSizeQuad;
    }

    public static int getPoe1StashHelperOffset() {
        if (SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder) {
            return poe1StashHelperOffsetWithFolders;
        } else return poe1StashHelperOffsetNoFolders;
    }

    public static Rectangle getPoe2StashBonds() {
        if (SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder) {
            return poe2StashBoundsWithFolders;
        } else return poe2StashBoundsNoFolders;
    }

    // Note: Poe 1 & 2 have the same stash size, so these values are always the same as POE1's.
    // Keeping this calculate independent anyway on the off chance it changes.
    public static Dimension getPoe2StashCellSize() {
        return poe2StashCellSize;
    }

    public static Dimension getPoe2StashCellSizeQuad() {
        return poe2StashCellSizeQuad;
    }

    public static int getPoe2StashHelperOffset() {
        if (SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder) {
            return poe2StashHelperOffsetWithFolders;
        } else return poe2StashHelperOffsetNoFolders;
    }

    private static void calculatePoe1UIData() {
        POEWindow.poe1StashBoundsNoFolders = ScaledRect.getScaledRect(POE_1_PERCENT_STASH_NO_FOLDERS, gameBounds);
        POEWindow.poe1StashBoundsWithFolders = ScaledRect.getScaledRect(POE_1_PERCENT_STASH_WITH_FOLDERS, gameBounds);
        int cellWidth = Math.round(poe1StashBoundsNoFolders.width / 12f);
        int cellHeight = Math.round(poe1StashBoundsNoFolders.height / 12f);
        poe1StashCellSize = new Dimension(cellWidth, cellHeight);
        int quadCellWidth = Math.round(poe1StashBoundsNoFolders.width / 24f);
        int quadCellHeight = Math.round(poe1StashBoundsNoFolders.height / 24f);
        poe1StashCellSizeQuad = new Dimension(quadCellWidth, quadCellHeight);
        poe1StashHelperOffsetWithFolders = ScaledInt.getScaledValue(POE_1_PERCENT_HELPER_OFFSET_WITH_FOLDERS, gameBounds.height);
        poe1StashHelperOffsetNoFolders = ScaledInt.getScaledValue(POE_1_PERCENT_HELPER_OFFSET_NO_FOLDERS, gameBounds.height);
//        if (App.debug)
//            ZUtil.invokeLater(() -> DrawWindow.draw(DrawWindow.WindowId.STASH_BOUNDS_POE_1, getPoe1StashBonds()));
    }

    private static void calculatePoe2UIData() {
        POEWindow.poe2StashBoundsNoFolders = ScaledRect.getScaledRect(POE_2_PERCENT_STASH_NO_FOLDERS, gameBounds);
        POEWindow.poe2StashBoundsWithFolders = ScaledRect.getScaledRect(POE_2_PERCENT_STASH_WITH_FOLDERS, gameBounds);
        int cellWidth = Math.round(poe2StashBoundsNoFolders.width / 12f);
        int cellHeight = Math.round(poe2StashBoundsNoFolders.height / 12f);
        poe2StashCellSize = new Dimension(cellWidth, cellHeight);
        int quadCellWidth = Math.round(poe2StashBoundsNoFolders.width / 24f);
        int quadCellHeight = Math.round(poe2StashBoundsNoFolders.height / 24f);
        poe2StashCellSizeQuad = new Dimension(quadCellWidth, quadCellHeight);
        poe2StashHelperOffsetWithFolders = ScaledInt.getScaledValue(POE_2_PERCENT_HELPER_OFFSET_WITH_FOLDERS, gameBounds.height);
        poe2StashHelperOffsetNoFolders = ScaledInt.getScaledValue(POE_2_PERCENT_HELPER_OFFSET_NO_FOLDERS, gameBounds.height);
//        if (App.debug)
//            ZUtil.invokeLater(() -> DrawWindow.draw(DrawWindow.WindowId.STASH_BOUNDS_POE_2, getPoe2StashBonds(), Color.BLUE));
    }

    public static void addListener(POEWindowListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(POEWindowListener listener) {
        listeners.remove(listener);
    }

}
