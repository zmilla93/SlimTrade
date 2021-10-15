package com.slimtrade.gui;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.saving.elements.PinElement;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.basic.HideableDialog;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.TrayButton;
import com.slimtrade.gui.dialogs.BetrayalWindow;
import com.slimtrade.gui.dialogs.ForceFocusDialog;
import com.slimtrade.gui.dialogs.IgnoreItemWindow;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.history.HistoryWindow;
import com.slimtrade.gui.menubar.MenubarDialog;
import com.slimtrade.gui.menubar.MenubarExpandDialog;
import com.slimtrade.gui.messaging.MessageDialogManager;
import com.slimtrade.gui.options.OptionsWindow;
import com.slimtrade.gui.options.cheatsheet.CheatSheetData;
import com.slimtrade.gui.options.cheatsheet.CheatSheetWindow;
import com.slimtrade.gui.options.ignore.ItemIgnorePanel;
import com.slimtrade.gui.overlay.OverlayManager;
import com.slimtrade.gui.popups.PatchNotesWindow;
import com.slimtrade.gui.scanner.ChatScannerWindow;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.stash.StashWindow;
import com.slimtrade.gui.stash.helper.StashHelperContainer;
import com.slimtrade.gui.stashsearch.StashSearchWindow;
import com.slimtrade.gui.tutorial.TutorialWindow;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.slimtrade.core.utility.TradeUtility.screenSize;

public class FrameManager {

    public static int gapSmall = 4;
    public static int gapLarge = 14;
//    public static GridBagLayout gridBag;

    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static TutorialWindow tutorialWindow;
    public static MenubarDialog menubar;
    public static MenubarExpandDialog menubarToggle;
    public static MessageDialogManager messageManager;
    public static SetupWindow setupWindow;
    public static StashHelperContainer stashHelperContainer;
    public static StashWindow stashOverlayWindow;
    public static OverlayManager overlayManager;
    public static ChatScannerWindow chatScannerWindow;
    public static TrayButton trayButton;
    public static BetrayalWindow betrayalWindow;
    public static StashSearchWindow stashSearchWindow;
    public static PatchNotesWindow patchNotesWindow;
    //    public static ArrayList<CheatSheetData> cheatSheetData = new ArrayList<>();
    public static CopyOnWriteArrayList<CheatSheetWindow> cheatSheetWindows;

    //Ignore Items
    public static IgnoreItemWindow ignoreItemWindow;
    public static ItemIgnorePanel itemIgnorePanel;
    public static AddRemovePanel ignoreItemAddRemovePanel;

    // Force Focus
    public static ForceFocusDialog forceFocusDialog;

    private static HideableDialog[] menuHideFrames;
    private static HideableDialog[] forceFrames;
    private static HideableDialog[] showHideDialogs;
    private static AbstractWindow[] pinWindows;


    public static WindowState windowState = WindowState.NORMAL;
    public static WindowState lastWindowState = WindowState.NORMAL;

    public FrameManager() {
        assert(SwingUtilities.isEventDispatchThread());
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.height", 12);
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(20000);

        // TODO : try and remove
        cheatSheetWindows = new CopyOnWriteArrayList<>();

        trayButton = new TrayButton();
        trayButton.addToTray();

        stashHelperContainer = new StashHelperContainer();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();

        // Menu Bar
        menubar = new MenubarDialog();
        menubarToggle = new MenubarExpandDialog();
        menubar.init();
        menubarToggle.updateLocation();

        messageManager = new MessageDialogManager();

        chatScannerWindow = new ChatScannerWindow();
        ignoreItemWindow = new IgnoreItemWindow();
        stashOverlayWindow = new StashWindow();

        overlayManager = new OverlayManager();

        stashHelperContainer.setShow(true);
        stashHelperContainer.updateLocation();

        betrayalWindow = new BetrayalWindow();
//        centerFrame(betrayalWindow);

        stashSearchWindow = new StashSearchWindow();
        forceFocusDialog = new ForceFocusDialog();

        showHideDialogs = new HideableDialog[]{stashHelperContainer, menubar, menubarToggle, stashSearchWindow};
        forceFrames = new HideableDialog[]{stashHelperContainer, menubar, menubarToggle, ignoreItemWindow, stashSearchWindow};
        menuHideFrames = new HideableDialog[]{optionsWindow, historyWindow, chatScannerWindow, betrayalWindow};
        pinWindows = new AbstractWindow[]{optionsWindow, historyWindow, chatScannerWindow, stashSearchWindow, betrayalWindow};

        loadWindowPins();

    }

    public static void generateCheatSheets() {
        assert(SwingUtilities.isEventDispatchThread());
        for (CheatSheetData data : App.saveManager.settingsSaveFile.cheatSheetData) {
            if (data == null || !TradeUtility.isValidImagePath(data.fileName)) {
                continue;
            }
            CheatSheetWindow w = new CheatSheetWindow(data);
            if (!w.isValid()) {
                continue;
            }
            FrameManager.cheatSheetWindows.add(w);
            FrameManager.centerFrame(w);
            for (PinElement p : App.saveManager.pinSaveFile.cheatSheetPins) {
                if (p.name.equals(data.fileName)) {
                    w.applyPinElement(p);
                }
            }
        }
    }

    public static void disposeCheatSheets() {
        assert(SwingUtilities.isEventDispatchThread());
        for (CheatSheetWindow w : FrameManager.cheatSheetWindows) {
            w.dispose();
            FrameManager.cheatSheetWindows.remove(w);
        }
    }

    public static void saveWindowPins() {
        assert(SwingUtilities.isEventDispatchThread());
        App.saveManager.pinSaveFile.windowPins.clear();
        for (AbstractWindow w : pinWindows) {
            PinElement pin = w.getPinElement();
            pin.name = w.getTitle();
            App.saveManager.pinSaveFile.windowPins.add(pin);
        }
        App.saveManager.savePinsToDisk();
    }

    public static void loadWindowPins() {
        assert(SwingUtilities.isEventDispatchThread());
        App.saveManager.loadPinsFromDisk();
        for (AbstractWindow w : pinWindows) {
            FrameManager.centerFrame(w);
            for (PinElement pin : App.saveManager.pinSaveFile.windowPins) {
                if (w.getTitle().equals(pin.name)) {
                    w.applyPinElement(pin);
                    break;
                }
            }
        }
    }

    public static void hideMenuFrames() {
        assert(SwingUtilities.isEventDispatchThread());
        for (HideableDialog d : menuHideFrames) {
            d.setVisible(false);
        }
    }

    public static void hideAllFrames() {
        SwingUtilities.invokeLater(() -> {
            for (HideableDialog d : menuHideFrames) {
                d.setVisible(false);
            }
            for (HideableDialog d : showHideDialogs) {
                d.setVisible(false);
            }
            for (HideableDialog d : cheatSheetWindows) {
                d.setVisible(false);
            }
            FrameManager.messageManager.hideAll();
            FrameManager.stashOverlayWindow.setVisible(false);
            FrameManager.overlayManager.hideAll();
        });
    }

    public static void showVisibleFrames() {
        SwingUtilities.invokeLater(() -> {
            switch (windowState) {
                case STASH_OVERLAY:
                    break;
                case LAYOUT_MANAGER:
                    overlayManager.allToFront();
                    break;
                case NORMAL:
                    for (HideableDialog d : showHideDialogs) {
                        d.setVisible(d.visible);
                    }
                    for (HideableDialog d : FrameManager.cheatSheetWindows) {
                        d.setVisible(d.visible);
                    }
                    FrameManager.messageManager.showAll();
                    break;
            }
        });

    }

    public static void centerFrame(Window window) {
        assert(SwingUtilities.isEventDispatchThread());
        int x = (screenSize.width / 2) - (window.getWidth() / 2);
        if (x < 0) x = 0;
        int y = (screenSize.height / 2) - (window.getHeight() / 2);
        if (y < 0) y = 0;
        window.setLocation(x, y);
    }

    public static void forceAllToTop() {
        SwingUtilities.invokeLater(() -> {
            FrameManager.messageManager.forceAllToTop();
            if (FrameManager.windowState == WindowState.NORMAL) {
                for (HideableDialog h : forceFrames) {
                    if (h != null && h.isVisible()) {
                        h.setAlwaysOnTop(false);
                        h.setAlwaysOnTop(true);
                    }
                }
            }
        });

    }

    public static void refreshMenuFrames() {
        SwingUtilities.invokeLater(() -> {
            for (HideableDialog h : menuHideFrames) {
                h.refreshVisibility();
            }
        });

    }

    public static void showOptionsWindow() {
        assert(SwingUtilities.isEventDispatchThread());
        optionsWindow.setVisible(true);
        optionsWindow.setAlwaysOnTop(true);
        optionsWindow.setAlwaysOnTop(false);
    }

    public static void showTutorialWindow() {
        assert(SwingUtilities.isEventDispatchThread());
        if (tutorialWindow == null) {
            tutorialWindow = new TutorialWindow();
            ColorManager.recursiveColor(tutorialWindow);
        }
        tutorialWindow.setVisible(true);
        tutorialWindow.setAlwaysOnTop(true);
    }

    public static void destroyTutorialWindow() {
        assert(SwingUtilities.isEventDispatchThread());
        tutorialWindow.dispose();
        tutorialWindow = null;
    }

    public static void fitWindowToScreen(Window w) {
        assert(SwingUtilities.isEventDispatchThread());
        boolean move = false;
        int width = w.getWidth();
        int height = w.getHeight();
        if (width > screenSize.width) {
            width = screenSize.width;
            move = true;
        }
        if (height > screenSize.height) {
            height = screenSize.height;
            move = true;
        }
        if (move) {
            w.setSize(new Dimension(width, height));
        }
    }

}
