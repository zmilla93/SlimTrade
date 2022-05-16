package com.slimtrade.gui.managers;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.overlays.MessageOverlay;
import com.slimtrade.gui.overlays.OverlayInfoDialog;
import com.slimtrade.gui.windows.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class FrameManager {
    // Windows
    public static DebugWindow debugWindow;
    public static MessageManager messageManager;
    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;

    // Overlays
    public static DummyWindow dummyWindow;
    public static OverlayInfoDialog overlayInfoWindow;
    public static MessageOverlay messageOverlay;
    public static StashGridWindow stashGridWindow;

    private static final HashMap<AppState, Window[]> windowMap = new HashMap<>();
    private static final HashMap<AppState, Boolean[]> windowVisibilityMap = new HashMap<>();

    private static AppState state = AppState.RUNNING;

    public static void init() {
        // Windows
        debugWindow = new DebugWindow();
        messageManager = new MessageManager();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();

        // Overlays
        overlayInfoWindow = new OverlayInfoDialog();
        messageOverlay = new MessageOverlay();
        stashGridWindow = new StashGridWindow();
        stashGridWindow.setVisible(true);
        dummyWindow = new DummyWindow(); // Omitted from visibility list


        // Group windows that need to be shown/hidden during state changes
        Window[] runningWindows = new Window[]{messageManager, optionsWindow, historyWindow};
        Window[] overlayWindows = new Window[]{overlayInfoWindow, messageOverlay};

        // Matching bool array if the window group should remember previous visibility
        Boolean[] runningWindowsVisibility = new Boolean[runningWindows.length];

        // Throw the data into maps for ease of use
        windowMap.put(AppState.RUNNING, runningWindows);
        windowMap.put(AppState.EDIT_OVERLAY, overlayWindows);
        windowVisibilityMap.put(AppState.RUNNING, runningWindowsVisibility);
    }

    public static void setWindowVisibility(AppState newState) {
        assert (SwingUtilities.isEventDispatchThread());
        Window[] windows = windowMap.get(FrameManager.state);
        Boolean[] windowVisibility = windowVisibilityMap.get(FrameManager.state);
        // Hide current Windows
        if (windows != null) {
            for (int i = 0; i < windows.length; i++) {
                if (windowVisibility != null) {
                    windowVisibility[i] = windows[i].isVisible();
                }
                windows[i].setVisible(false);
            }
        }
        // Show Windows
        windows = windowMap.get(newState);
        windowVisibility = windowVisibilityMap.get(newState);
        if (windows != null) {
            for (int i = 0; i < windows.length; i++) {
                if (windowVisibility != null) {
                    windows[i].setVisible(windowVisibility[i]);
                } else {
                    windows[i].setVisible(true);
                }
            }
        }
        FrameManager.state = newState;
    }

    public static void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        window.setLocation(new Point(screenSize.width / 2 - windowSize.width / 2, screenSize.height / 2 - windowSize.height / 2));
    }

}
