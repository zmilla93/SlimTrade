package github.zmilla93.gui.managers;

import github.zmilla93.App;
import github.zmilla93.core.data.CheatSheetData;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.enums.MenubarStyle;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.POEInterface;
import github.zmilla93.gui.chatscanner.ChatScannerWindow;
import github.zmilla93.gui.development.DesignerConfigWindow;
import github.zmilla93.gui.development.StashAlignmentDesignerWindow;
import github.zmilla93.gui.listening.IDefaultSizeAndLocation;
import github.zmilla93.gui.menubar.MenubarButtonDialog;
import github.zmilla93.gui.menubar.MenubarDialog;
import github.zmilla93.gui.options.ignore.ItemIgnoreWindow;
import github.zmilla93.gui.options.searching.StashSearchGroupData;
import github.zmilla93.gui.options.searching.StashSearchWindow;
import github.zmilla93.gui.options.searching.StashSearchWindowMode;
import github.zmilla93.gui.overlays.MenubarOverlay;
import github.zmilla93.gui.overlays.MessageOverlay;
import github.zmilla93.gui.overlays.OverlayInfoDialog;
import github.zmilla93.gui.pinning.IPinnable;
import github.zmilla93.gui.pinning.PinManager;
import github.zmilla93.gui.setup.SetupWindow;
import github.zmilla93.gui.stash.StashHelperContainer;
import github.zmilla93.gui.stash.StashHelperContainerPoe1;
import github.zmilla93.gui.stash.StashHelperContainerPoe2;
import github.zmilla93.gui.windows.*;
import github.zmilla93.gui.windows.test.MessageTestWindow;
import github.zmilla93.modules.theme.testing.UIColorKeyViewer;
import github.zmilla93.modules.theme.testing.UIManagerInspectorWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
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
    public static KalguurHelperWindow kalguurHelperWindow;
    public static TutorialWindow tutorialWindow;
    public static StashAlignmentDesignerWindow stashAlignmentDesignerWindow;
    public static DesignerConfigWindow designerConfigWindow;
    public static PatchNotesWindow patchNotesWindow;
    public static HashMap<String, CheatSheetWindow> cheatSheetWindows = new HashMap<>();
    public static HashMap<String, StashSearchWindow> searchWindows = new HashMap<>();
    public static StashSearchWindow combinedSearchWindow;
    public static StashAlignmentPreviewWindow stashAlignmentPreviewWindow;
    public static SetupWindow setupWindow;
    //        public static UpdateProgressWindow updateProgressWindow;
    public static RoadmapWindow roadMapWindow;
    public static MoverDialog testMoverDialog;

    // Debug Windows
    public static MessageTestWindow debugMessageWindow;
    public static UIManagerInspectorWindow uiManagerInspectorWindow;
    public static UIColorKeyViewer uiColorKeyViewer;

    // Overlays
    public static DummyWindow dummyWindow;
    public static OverlayInfoDialog overlayInfoWindow;
    public static MessageOverlay messageOverlay;
    public static MenubarOverlay menubarOverlay;
    public static StashGridWindow stashGridWindow;
    public static StashHelperContainer stashHelperContainerPoe1;
    public static StashHelperContainer stashHelperContainerPoe2;

    // Menu Bar
    public static MenubarButtonDialog menuBarIcon;
    public static MenubarDialog menuBarDialog;

    private static final HashMap<AppState, Window[]> windowMap = new HashMap<>();
    private static final HashMap<AppState, Boolean[]> windowVisibilityMap = new HashMap<>();
    private static IDefaultSizeAndLocation[] defaultSizeAndLocationWindows;

    private static boolean menuBarExpanded = false;
    private static boolean initialized = false;

    public static void createGUI() {
        /// App Windows
        setupWindow = new SetupWindow();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        chatScannerWindow = new ChatScannerWindow();
        itemIgnoreWindow = new ItemIgnoreWindow();
        kalguurHelperWindow = new KalguurHelperWindow();
        stashGridWindow = new StashGridWindow();
        tutorialWindow = new TutorialWindow();
        patchNotesWindow = new PatchNotesWindow();
        roadMapWindow = new RoadmapWindow();
        dummyWindow = new DummyWindow(); // Omitted from visibility list
        // FIXME : Remove debug window
        debugWindow = new DebugWindow();
//        testMoverDialog = new MoverDialog();
//        testMoverDialog.setSize(400, 400);
//        testMoverDialog.setVisible(true);
        /// Menu Bar
        menuBarIcon = new MenubarButtonDialog();
        menuBarDialog = new MenubarDialog();
        /// Overlays
        menubarOverlay = new MenubarOverlay();
        messageManager = new MessageManager();
        stashHelperContainerPoe1 = new StashHelperContainerPoe1();
        stashHelperContainerPoe2 = new StashHelperContainerPoe2();
        stashAlignmentPreviewWindow = new StashAlignmentPreviewWindow();
        overlayInfoWindow = new OverlayInfoDialog();
        messageOverlay = new MessageOverlay();
        /// Dynamic windows
        rebuildCheatSheetWindows();
        rebuildSearchWindows();
        /// Debug Windows
        if (App.debug) {
            stashAlignmentDesignerWindow = new StashAlignmentDesignerWindow();
            designerConfigWindow = new DesignerConfigWindow();
        }
        if (App.themeDebugWindows) {
            uiManagerInspectorWindow = new UIManagerInspectorWindow();
            uiColorKeyViewer = new UIColorKeyViewer();
            debugMessageWindow = new MessageTestWindow();
        }

        /// Organize windows into groups
        // FIXME : Add all windows
        // FIXME: Check CheatSheet windows and StashSearch windows.
        // Arrays of windows that need to be shown/hidden during state changes
        Window[] runningWindows = new Window[]{messageManager, optionsWindow, historyWindow, chatScannerWindow, menuBarIcon, menuBarDialog, stashHelperContainerPoe1, stashHelperContainerPoe2, tutorialWindow, patchNotesWindow, roadMapWindow};
        Window[] stashWindows = new Window[]{stashGridWindow};
        Window[] setupWindows = new Window[]{setupWindow};
        Window[] overlayWindows = new Window[]{overlayInfoWindow, messageOverlay, menubarOverlay};
        defaultSizeAndLocationWindows = new IDefaultSizeAndLocation[]{optionsWindow, historyWindow, chatScannerWindow, tutorialWindow, patchNotesWindow, setupWindow, overlayInfoWindow, roadMapWindow};
        // Matching boolean array so running remember previous visibility.
        Boolean[] runningWindowsVisibility = new Boolean[runningWindows.length];
        // Throw the data into maps for ease of use
        windowMap.put(AppState.RUNNING, runningWindows);
        windowMap.put(AppState.EDIT_OVERLAY, overlayWindows);
        windowMap.put(AppState.EDIT_STASH, stashWindows);
        windowMap.put(AppState.SETUP, setupWindows);
        windowVisibilityMap.put(AppState.RUNNING, runningWindowsVisibility);
        applyAllDefaultSizesAndLocations();

        /// Finish
        LaunchPopupManager.INSTANCE.init();
        initialized = true;
    }

    public static boolean hasBeenInitialized() {
        return initialized;
    }

    public static void showAppFrames() {
        // FIXME: Show proper windows
        if (App.showOptionsOnLaunch) optionsWindow.setVisible(true);
        if (App.showHistoryOnLaunch) historyWindow.setVisible(true);
        messageManager.setVisible(true);
//        roadMapWindow.setVisible(true);
        updateMenubarVisibility();
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

    public static <T extends Window> Window showLazyDebugWindow(Window window, Class<T> classType) {
        if (window == null) {
            try {
                window = classType.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException ignore) {
            }
        }
        if (window == null) return null;
        window.setVisible(true);
        return window;
    }

    public static void rebuildCheatSheetWindows() {
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

    public static void rebuildSearchWindows() {
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

    public static void checkMenubarExpanded(Point point) {
        if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.DISABLED) return;
        if (menuBarExpanded) {
            if (!menuBarDialog.getBufferedBounds().contains(point)) {
                menuBarExpanded = false;
                updateMenubarVisibility();
            }
        } else {
            if (menuBarIcon.getBufferedBounds().contains(point)) {
                menuBarExpanded = true;
                updateMenubarVisibility();
            }
        }
    }

    public static void updateMenubarVisibility() {
        SwingUtilities.invokeLater(() -> {
            boolean shouldHide = false;
            if (SaveManager.settingsSaveFile.data.menubarStyle == MenubarStyle.DISABLED) shouldHide = true;
            else if (SaveManager.settingsSaveFile.data.hideWhenPOENotFocused && !POEInterface.isGameFocused(true))
                shouldHide = true;
            if (shouldHide) {
                menuBarDialog.setVisible(false);
                menuBarIcon.setVisible(false);
                return;
            }
            boolean expanded = menuBarExpanded || SaveManager.settingsSaveFile.data.menubarAlwaysExpanded;
            if (expanded) {
                menuBarDialog.setVisible(true);
                menuBarIcon.setVisible(false);
            } else {
                menuBarIcon.setVisible(true);
                menuBarDialog.setVisible(false);
            }
        });
    }

    // FIXME : Should check for duplicate message, since there are situations this could get called twice
    public static void displayUpdateAvailable(String tag) {
        SwingUtilities.invokeLater(() -> {
            FrameManager.optionsWindow.showUpdateButton();
            FrameManager.messageManager.addUpdateMessage(true, tag);
        });
    }

    public static void requestRestoreUIDefaults() {
        assert SwingUtilities.isEventDispatchThread();
        if (App.getState() == AppState.SETUP) {
            restoreUIDefaults();
            return;
        }
        int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to reset the UI to its default state?\n" +
                        "This will clear all pins and reset the size and location of most windows.",
                "Reset SlimTrade UI", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            restoreUIDefaults();
        }
    }

    private static void restoreUIDefaults() {
        assert SwingUtilities.isEventDispatchThread();
        if (App.getState() == AppState.SETUP) {
            FrameManager.setupWindow.applyDefaultSizeAndLocation();
            return;
        }
        for (IDefaultSizeAndLocation window : defaultSizeAndLocationWindows) {
            if (window instanceof IPinnable) {
                ((IPinnable) window).unpin();
            }
            window.applyDefaultSizeAndLocation();
        }
        for (CheatSheetWindow window : cheatSheetWindows.values()) {
            window.unpin();
            window.applyDefaultSizeAndLocation();
        }
        if (combinedSearchWindow != null) {
            combinedSearchWindow.unpin();
            combinedSearchWindow.applyDefaultSizeAndLocation();
        }
        for (StashSearchWindow window : searchWindows.values()) {
            window.unpin();
            window.applyDefaultSizeAndLocation();
        }
        overlayInfoWindow.restoreDefaults();
        SaveManager.overlaySaveFile.saveToDisk();
    }

    public static void applyAllDefaultSizesAndLocations() {
        for (IDefaultSizeAndLocation window : defaultSizeAndLocationWindows)
            window.applyDefaultSizeAndLocation();
    }

}
