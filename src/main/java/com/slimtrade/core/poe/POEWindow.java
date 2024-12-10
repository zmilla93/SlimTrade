package com.slimtrade.core.poe;

import com.slimtrade.core.jna.NativePoeWindow;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.gui.components.MonitorInfo;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.BasicDialog;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A platform independent representation of the Path of Exile game window.
 * Used to calculate the screen location of in game UI elements, or to pin things relative to the game window.
 * Bounds can be updated via different {@link GameDetectionMethod}s.
 * Attach a {@link POEWindowListener} to listen for events.
 */
// TODO : Could also add the option to manually define the game window region to support Mac/Linux users who play in windowed mode.
public class POEWindow {
    // FIXME : Temp static info
    private static final Rectangle POE_1_STASH_1920_1080 = new Rectangle(13, 126, 634, 634);

    private static Rectangle gameBounds = new Rectangle(1920, 0, 1920, 1080);
    private static Point centerOfScreen;
    private static Rectangle poe1StashBounds = POE_1_STASH_1920_1080;
    private static Dimension poe1StashCellSize;
    private static Dimension poe1StashCellSizeQuad;

    private static NativePoeWindow currentGameWindow;
    private static MonitorInfo currentMonitor;


    private static final ArrayList<POEWindowListener> listeners = new ArrayList<>();

    // FIXME : Temp dialog
    public static BasicDialog dialog;

    static {
        // FIXME : Temp calculation
        System.out.println("Calculating inital game bounds");
        calculateNewGameBounds();
        // FIXME : Calculating bounds early using 1920x1080 to avoid errors.
        //  Should instead use the bounds of the first monitor, then let the automated system take over.
        calculatePoe1UIData();
    }

    // FIXME : Temp function
    public static void createAndShowWindow() {
        dialog = new BasicDialog();
        dialog.setBounds(POE_1_STASH_1920_1080);
        dialog.setVisible(true);
    }

    public static Rectangle getGameBounds() {
        return gameBounds;
    }

    public static void setBoundsByMonitor(MonitorInfo monitor) {
        assert SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.MONITOR;
        assert monitor != null;
        if (monitor.equals(currentMonitor)) return;
        POEWindow.gameBounds = monitor.bounds;
        calculateNewGameBounds();
        System.out.println("Bounds set via monitor: " + gameBounds);
    }

    public static void setBoundsByWindowHandle(WinDef.HWND handle) {
        assert SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.AUTOMATIC;
        assert handle != null;
        if (currentGameWindow != null && handle.equals(currentGameWindow.handle)) return;
        currentGameWindow = new NativePoeWindow(handle);
        POEWindow.gameBounds = currentGameWindow.clientBounds;
        calculateNewGameBounds();
        System.out.println("Bounds set via native window: " + gameBounds);
    }

    private static void setBoundsFallback() {
        MonitorInfo monitor = MonitorInfo.getAllMonitors().get(0);
        POEWindow.gameBounds = monitor.bounds;
        calculateNewGameBounds();
        System.out.println("Bounds fallback set: " + gameBounds);
    }

    private static void calculateNewGameBounds() {
        System.out.println("New Game Bounds: " + gameBounds);
        centerOfScreen = new Point(gameBounds.x + gameBounds.width / 2, gameBounds.y + gameBounds.height / 2);
        calculatePoe1UIData();
        for (POEWindowListener listener : listeners) listener.onGameBoundsChange();
    }

    /**
     * Attempts to update the game bounds using user's current settings.
     * This should only be used to manually force updates, like on launch or when detection method changes.
     */
    public static void forceGameBoundsRefresh() {
        GameDetectionMethod method = SaveManager.settingsSaveFile.data.gameDetectionMethod;
        switch (method) {
            case AUTOMATIC:
                // NOTE : This currently shouldn't be reachable on non Windows platforms, just future proofing.
                if (Platform.WINDOWS == Platform.current) {
                    WinDef.HWND handle = NativePoeWindow.findPathOfExileWindow();
                    System.out.println("The handle! : " + handle);
                    if (handle != null) setBoundsByWindowHandle(handle);
                    else setBoundsFallback();
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
     * Center a window relative to the current "game bounds",
     * which could be the actual game window bounds, a monitor bounds, or a screen region.
     */
    // FIXME: Should ensure that window is fully on the monitor
    public static void centerWindow(Window window) {
        assert SwingUtilities.isEventDispatchThread();
        if (window.equals(FrameManager.setupWindow))
            System.out.println("Centering Setup using game bounds: " + gameBounds);
        if (gameBounds == null) {
            window.setLocationRelativeTo(null);
        } else {
            int halfWidth = window.getWidth() / 2;
            int halfHeight = window.getHeight() / 2;
            Point location = new Point(centerOfScreen.x - halfWidth, centerOfScreen.y - halfHeight);
            if (window.equals(FrameManager.setupWindow))
                System.out.println("Setup Window Location: " + location);
            window.setLocation(location);
        }
    }

    // Path of Exile UI Info Getters

    public static Rectangle getPoe1StashBonds() {
        return poe1StashBounds;
    }

    public static Dimension getPoe1StashCellSize() {
        return poe1StashCellSize;
    }

    public static Dimension getPoe1StashCellSizeQuad() {
        return poe1StashCellSizeQuad;
    }

    /**
     * Calculate the bounds of Path of Exile 1 UI elements.
     */
    private static void calculatePoe1UIData() {
        // FIXME : Scale stash bounds
        POEWindow.poe1StashBounds = new Rectangle(
                gameBounds.x + POE_1_STASH_1920_1080.x, gameBounds.y + POE_1_STASH_1920_1080.y,
                POE_1_STASH_1920_1080.width, POE_1_STASH_1920_1080.height);
        int cellWidth = Math.round(poe1StashBounds.width / 12f);
        int cellHeight = Math.round(poe1StashBounds.height / 12f);
        poe1StashCellSize = new Dimension(cellWidth, cellHeight);
        int quadCellWidth = Math.round(poe1StashBounds.width / 24f);
        int quadCellHeight = Math.round(poe1StashBounds.height / 24f);
        poe1StashCellSizeQuad = new Dimension(quadCellWidth, quadCellHeight);
    }

    public static void addListener(POEWindowListener listener) {
        listeners.add(listener);
    }

    public static void removeListener(POEWindowListener listener) {
        listeners.remove(listener);
    }

}
