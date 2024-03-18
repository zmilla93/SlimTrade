package com.slimtrade.gui.managers;

import com.slimtrade.App;
import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.chatscanner.ChatScannerWindow;
import com.slimtrade.gui.menubar.MenubarButtonDialog;
import com.slimtrade.gui.menubar.MenubarDialog;
import com.slimtrade.gui.options.ignore.ItemIgnoreWindow;
import com.slimtrade.gui.options.searching.StashSearchGroupData;
import com.slimtrade.gui.options.searching.StashSearchWindow;
import com.slimtrade.gui.options.searching.StashSearchWindowMode;
import com.slimtrade.gui.overlays.MenubarOverlay;
import com.slimtrade.gui.overlays.MessageOverlay;
import com.slimtrade.gui.overlays.OverlayInfoDialog;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.stash.StashHelperContainer;
import com.slimtrade.gui.windows.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class FrameManager {

    // Windows
    public static DebugWindow debugWindow;
    public static MessageManager messageManager;
    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static ChatScannerWindow chatScannerWindow;
    public static ItemIgnoreWindow itemIgnoreWindow;
    public static TutorialWindow tutorialWindow;
    public static PatchNotesWindow patchNotesWindow;
    public static HashMap<String, CheatSheetWindow> cheatSheetWindows = new HashMap<>();
    public static HashMap<String, StashSearchWindow> searchWindows = new HashMap<>();
    public static StashSearchWindow combinedSearchWindow;
    public static SetupWindow setupWindow;
    public static UpdateProgressWindow updateProgressWindow;

    // Overlays
    public static DummyWindow dummyWindow;
    public static OverlayInfoDialog overlayInfoWindow;
    public static MessageOverlay messageOverlay;
    public static MenubarOverlay menubarOverlay;
    public static StashGridWindow stashGridWindow;
    public static StashHelperContainer stashHelperContainer;

    // Menubar
    public static MenubarButtonDialog menubarIcon;
    public static MenubarDialog menubarDialog;

    private static final HashMap<AppState, Window[]> windowMap = new HashMap<>();
    private static final HashMap<AppState, Boolean[]> windowVisibilityMap = new HashMap<>();

    private static boolean menubarExpanded = false;
    private static boolean initialized = false;

    public static void init() {
        // Windows
        // FIXME : Remove debug window
        debugWindow = new DebugWindow();
        stashHelperContainer = new StashHelperContainer();
        messageManager = new MessageManager();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        chatScannerWindow = new ChatScannerWindow();
        itemIgnoreWindow = new ItemIgnoreWindow();
        tutorialWindow = new TutorialWindow();
        patchNotesWindow = new PatchNotesWindow();

        setupWindow = new SetupWindow();

        // Overlays
        overlayInfoWindow = new OverlayInfoDialog();
        messageOverlay = new MessageOverlay();
        menubarOverlay = new MenubarOverlay();
        stashGridWindow = new StashGridWindow();

        dummyWindow = new DummyWindow(); // Omitted from visibility list

        // Menubar
        menubarIcon = new MenubarButtonDialog();
        menubarDialog = new MenubarDialog();

        buildCheatSheetWindows();
        buildSearchWindows();

        // FIXME : Add all windows
        // FIXME: Check CheatSheet windows and StashSearch windows.
        // Group windows that need to be shown/hidden during state changes
        Window[] runningWindows = new Window[]{messageManager, optionsWindow, historyWindow, chatScannerWindow, menubarIcon, menubarDialog, stashHelperContainer, tutorialWindow};
        Window[] stashWindows = new Window[]{stashGridWindow};
        Window[] setupWindows = new Window[]{setupWindow};
        Window[] overlayWindows = new Window[]{overlayInfoWindow, messageOverlay, menubarOverlay};

        // Matching boolean array so running remember previous visibility.
        Boolean[] runningWindowsVisibility = new Boolean[runningWindows.length];

        // Throw the data into maps for ease of use
        windowMap.put(AppState.RUNNING, runningWindows);
        windowMap.put(AppState.EDIT_OVERLAY, overlayWindows);
        windowMap.put(AppState.EDIT_STASH, stashWindows);
        windowMap.put(AppState.SETUP, setupWindows);
        windowVisibilityMap.put(AppState.RUNNING, runningWindowsVisibility);
        initialized = true;
    }

    public static boolean hasBeenInitialized() {
        return initialized;
    }

    public static void showAppFrames() {
        // FIXME: Show proper windows
        FrameManager.optionsWindow.setVisible(true);
//        tutorialWindow.setVisible(true);
//        patchNotesWindow.setVisible(true);
        FrameManager.messageManager.setVisible(true);
        if (SaveManager.settingsSaveFile.data.enableMenuBar) {
            FrameManager.menubarIcon.setVisible(true);
        }
    }

    public static void setWindowVisibility(AppState newState) {
        assert (SwingUtilities.isEventDispatchThread());
        Window[] windows = windowMap.get(App.getState());
        Boolean[] windowVisibility = windowVisibilityMap.get(App.getState());
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
        if (newState == AppState.RUNNING) SystemTrayManager.showDefault();
        else SystemTrayManager.showSimple();
        App.setState(newState);
    }

    public static void buildCheatSheetWindows() {
        HashSet<String> openWindows = new HashSet<>();
        for (CheatSheetWindow window : cheatSheetWindows.values()) {
            if (window.isVisible()) openWindows.add(window.getPinTitle());
            PinManager.removePinnable(window);
            window.dispose();
        }
        cheatSheetWindows.clear();
        for (CheatSheetData data : SaveManager.settingsSaveFile.data.cheatSheets) {
            CheatSheetWindow window = CheatSheetWindow.createCheatSheet(data);
            if (window != null) cheatSheetWindows.put(data.title(), window);
        }
        PinManager.applyCheatSheetPins();
        for (CheatSheetWindow window : cheatSheetWindows.values()) {
            if (openWindows.contains(window.getPinTitle())) window.setVisible(true);
        }
    }

    public static void buildSearchWindows() {
        assert (SwingUtilities.isEventDispatchThread());
        // Dispose of existing windows
        HashSet<String> openWindows = new HashSet<>();
        for (StashSearchWindow window : searchWindows.values()) {
            if (window.isVisible()) openWindows.add(window.getPinTitle());
            window.dispose();
        }
        if (combinedSearchWindow != null) {
            if (combinedSearchWindow.isVisible()) openWindows.add(combinedSearchWindow.getPinTitle());
            combinedSearchWindow.dispose();
        }
        searchWindows.clear();
        // Build new window(s)
        StashSearchWindowMode windowMode = SaveManager.settingsSaveFile.data.stashSearchWindowMode;
        if (windowMode == StashSearchWindowMode.COMBINED) {
            combinedSearchWindow = new StashSearchWindow(SaveManager.settingsSaveFile.data.stashSearchData);
        } else if (windowMode == StashSearchWindowMode.SEPARATE) {
            for (StashSearchGroupData group : SaveManager.settingsSaveFile.data.stashSearchData) {
                StashSearchWindow window = new StashSearchWindow(group);
                searchWindows.put(group.getPinTitle(), window);
            }
        }
        // Apply pins and visibility
        PinManager.applySearchWindowPins();
        if (windowMode == StashSearchWindowMode.COMBINED) {
            if (openWindows.contains(combinedSearchWindow.getPinTitle())) combinedSearchWindow.setVisible(true);
        } else if (windowMode == StashSearchWindowMode.SEPARATE) {
            for (StashSearchWindow window : searchWindows.values()) {
                if (openWindows.contains(window.getPinTitle())) window.setVisible(true);
            }
        }
    }

    public static void checkMenubarVisibility(Point point) {
        // FIXME (OPTIMIZE) : buffered bounds should be cached since this function is called frequently.
        if (!SaveManager.settingsSaveFile.data.enableMenuBar) return;
        if (menubarExpanded) {
            if (!TradeUtil.getBufferedBounds(menubarDialog.getBounds()).contains(point)) {
                menubarExpanded = false;
                updateMenubarVisibility();
            }
        } else {
            if (TradeUtil.getBufferedBounds(menubarIcon.getBounds()).contains(point)) {
                menubarExpanded = true;
                updateMenubarVisibility();
            }
        }
    }

    private static void updateMenubarVisibility() {
        SwingUtilities.invokeLater(() -> {
            if (!SaveManager.settingsSaveFile.data.enableMenuBar) {
                menubarDialog.setVisible(false);
                menubarIcon.setVisible(false);
            } else if (menubarExpanded) {
                menubarDialog.setVisible(true);
                menubarIcon.setVisible(false);
            } else {
                menubarIcon.setVisible(true);
                menubarDialog.setVisible(false);
            }
        });
    }

}
