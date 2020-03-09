package com.slimtrade.gui;

import com.slimtrade.App;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.HideableDialog;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.TrayButton;
import com.slimtrade.gui.dialogs.BetrayalWindow;
import com.slimtrade.gui.dialogs.IgnoreItemWindow;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.history.HistoryWindow;
import com.slimtrade.gui.menubar.MenubarDialog;
import com.slimtrade.gui.menubar.MenubarExpandDialog;
import com.slimtrade.gui.messaging.MessageDialogManager;
import com.slimtrade.gui.options.OptionsWindow;
import com.slimtrade.gui.options.ignore.ItemIgnorePanel;
import com.slimtrade.gui.overlay.OverlayManager;
import com.slimtrade.gui.scanner.ChatScannerWindow;
import com.slimtrade.gui.setup.SetupWindow;
import com.slimtrade.gui.stash.StashWindow;
import com.slimtrade.gui.stash.helper.StashHelperContainer;
import com.slimtrade.gui.stash_search.StashSearchWindow;
import com.slimtrade.gui.tutorial.TutorialWindow;

import javax.swing.*;
import java.awt.*;

public class FrameManager {

    public static int gapSmall = 4;
    public static int gapLarge = 14;
    public static GridBagLayout gridBag;

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

    //Ignore Items
    public static IgnoreItemWindow ignoreItemWindow;
    public static ItemIgnorePanel itemIgnorePanel;
    public static AddRemovePanel ignoreItemAddRemovePanel;


    private static HideableDialog[] menuHideFrames;
    private static HideableDialog[] forceFrames;
    private static HideableDialog[] showHideDialogs;


    public static WindowState windowState = WindowState.NORMAL;
    public static WindowState lastWindowState = WindowState.NORMAL;

    public FrameManager() {
        FrameManager.gridBag = new GridBagLayout();
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.height", 12);
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(20000);

//        stashSearchWindow = new StashSearchWindow();
//        stashSearchWindow.setShow(true);

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
        centerFrame(betrayalWindow);

        showHideDialogs = new HideableDialog[]{stashHelperContainer, menubar, menubarToggle};
        forceFrames = new HideableDialog[]{stashHelperContainer, menubar, menubarToggle, ignoreItemWindow};
        menuHideFrames = new HideableDialog[]{optionsWindow, historyWindow, chatScannerWindow, betrayalWindow};

    }

    public static void hideMenuFrames() {
        for (HideableDialog d : menuHideFrames) {
            d.setVisible(false);
        }
    }

    public static void hideAllFrames() {
        for (HideableDialog d : menuHideFrames) {
            d.setVisible(false);
        }
        for (HideableDialog d : showHideDialogs) {
            d.setVisible(false);
        }
        for (HideableDialog d : MessageDialogManager.getDialogList()) {
            d.setVisible(false);
        }
        FrameManager.overlayManager.hideAll();
    }

    public static void showVisibleFrames() {

        switch (windowState) {
            case STASH_OVERLAY:
                break;
            case LAYOUT_MANAGER:
                overlayManager.allToFront();
                break;
            case NORMAL:
//                System.out.println("VIS1");
                for (HideableDialog d : showHideDialogs) {
//                    System.out.println("DIG:" + d);
                    d.setVisible(d.visible);
                }
//                System.out.println("VIS2");
                for (HideableDialog d : MessageDialogManager.getDialogList()) {
                    d.setVisible(d.visible);
                }
//                System.out.println("VIS3");
                break;
        }

    }

    public static void centerFrame(Window window) {
        int x = (TradeUtility.screenSize.width / 2) - (window.getWidth() / 2);
        if (x < 0) x = 0;
        int y = (TradeUtility.screenSize.height / 2) - (window.getHeight() / 2);
        if (y < 0) y = 0;
        window.setLocation(x, y);
    }

    public static void forceAllToTop() {
        for (HideableDialog h : MessageDialogManager.getDialogList()) {
            if (h.isVisible()) {
                h.setAlwaysOnTop(false);
                h.setAlwaysOnTop(true);
            }
        }
        if (FrameManager.windowState == WindowState.NORMAL) {
            for (HideableDialog h : forceFrames) {
                if (h != null && h.isVisible()) {
                    h.setAlwaysOnTop(false);
                    h.setAlwaysOnTop(true);
                }
            }
        }
    }

    public static void refreshMenuFrames() {
        for (HideableDialog h : menuHideFrames) {
            h.refreshVisibility();
        }
    }

    public static void showOptionsWindow() {
        optionsWindow.setVisible(true);
        optionsWindow.setAlwaysOnTop(true);
        optionsWindow.setAlwaysOnTop(false);
    }

    public static void showTutorialWindow() {
        if (tutorialWindow == null) {
            tutorialWindow = new TutorialWindow();
            App.eventManager.recursiveColor(tutorialWindow);
        }
        tutorialWindow.setVisible(true);
        tutorialWindow.setAlwaysOnTop(true);
    }

    public static void destroyTutorialWindow() {
        tutorialWindow.dispose();
        tutorialWindow = null;
    }

}
