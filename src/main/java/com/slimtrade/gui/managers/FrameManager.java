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
import com.slimtrade.gui.overlays.MenubarOverlay;
import com.slimtrade.gui.overlays.MessageOverlay;
import com.slimtrade.gui.overlays.OverlayInfoDialog;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.stash.StashHelperContainer;
import com.slimtrade.gui.stashsorting.StashSortingWindow;
import com.slimtrade.gui.windows.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class FrameManager {
    // Windows
//    public static DebugWindow debugWindow;
    public static MessageManager messageManager;
    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static ChatScannerWindow chatScannerWindow;
    public static ItemIgnoreWindow itemIgnoreWindow;
    public static StashSortingWindow stashSortingWindow;
    public static HashMap<String, CheatSheetWindow> cheatSheetWindows = new HashMap<>();
    public static SetupWindow setupWindow;

    // Overlays
    public static DummyWindow dummyWindow;
    public static OverlayInfoDialog overlayInfoWindow;
    public static MessageOverlay messageOverlay;
    public static MenubarOverlay menubarOverlay;
    public static StashWindow stashGridWindow;
    public static StashHelperContainer stashHelperContainer;

    // Menubar
    public static MenubarButtonDialog menubarIcon;
    public static MenubarDialog menubarDialog;

    private static final HashMap<AppState, Window[]> windowMap = new HashMap<>();
    private static final HashMap<AppState, Boolean[]> windowVisibilityMap = new HashMap<>();

    private float resolutionMultiplier = 1;

    private static boolean menubarExpanded = false;

    public static void init() {

        // Windows
//        TestFrame frame = new TestFrame();
        stashHelperContainer = new StashHelperContainer();
//        debugWindow = new DebugWindow();
        messageManager = new MessageManager();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        chatScannerWindow = new ChatScannerWindow();
        itemIgnoreWindow = new ItemIgnoreWindow();
        stashSortingWindow = new StashSortingWindow();
        setupWindow = new SetupWindow();

//        StashHighlighterFrame testHighlighter = new StashHighlighterFrame(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//        testHighlighter.setVisible(true);

        // Overlays
        overlayInfoWindow = new OverlayInfoDialog();
        messageOverlay = new MessageOverlay();
        menubarOverlay = new MenubarOverlay();
        stashGridWindow = new StashWindow();

        dummyWindow = new DummyWindow(); // Omitted from visibility list

        // Menubar
        menubarIcon = new MenubarButtonDialog();
        menubarDialog = new MenubarDialog();

        buildCheatSheetWindows();

        // FIXME : Add all windows
        // Group windows that need to be shown/hidden during state changes
        Window[] runningWindows = new Window[]{messageManager, optionsWindow, historyWindow, chatScannerWindow, stashSortingWindow, menubarIcon, menubarDialog};
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

    }

    public static void showSetupFrame() {

    }

    public static void showAppFrames() {
        // FIXME: temp show options
        FrameManager.optionsWindow.setVisible(true);
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
            if (window != null) {
                cheatSheetWindows.put(data.title, window);
                if (openWindows.contains(data.title)) window.setVisible(true);
            }
        }
        PinManager.applyPins();
    }

    public static void checkMenubarVisibility(Point point) {
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
        if (menubarExpanded) {
            menubarDialog.setVisible(true);
            menubarIcon.setVisible(false);
        } else {
            menubarIcon.setVisible(true);
            menubarDialog.setVisible(false);
        }
    }

    public static void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        window.setLocation(new Point(screenSize.width / 2 - windowSize.width / 2, screenSize.height / 2 - windowSize.height / 2));
    }

    public static void calculateResolutionMultiplier() {

    }

    public static int getResolutionMultiplier;

}
