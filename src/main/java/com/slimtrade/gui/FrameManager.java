package com.slimtrade.gui;

import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.HideableDialog;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.TrayButton;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.history.HistoryWindow;
import com.slimtrade.gui.menubar.MenubarDialog;
import com.slimtrade.gui.menubar.MenubarExpandButton;
import com.slimtrade.gui.messaging.MessageDialogManager;
import com.slimtrade.gui.options.OptionsWindow;
import com.slimtrade.gui.options.ignore.ItemIgnorePanel;
import com.slimtrade.gui.overlay.OverlayManager;
import com.slimtrade.gui.scanner.ChatScannerWindow;
import com.slimtrade.gui.stash.StashWindow;
import com.slimtrade.gui.stash.helper.StashHelperContainer;

import javax.swing.*;
import java.awt.*;

public class FrameManager {

    public static int gapSmall = 4;
    public static int gapLarge = 14;
    public static GridBagLayout gridBag;

    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static MenubarDialog menubar;
    public static MenubarExpandButton menubarToggle;
    public static MessageDialogManager messageManager;
    public static StashHelperContainer stashHelperContainer;
    public static StashWindow stashOverlayWindow;
    public static OverlayManager overlayManager;
    public static ChatScannerWindow chatScannerWindow;
    private static TrayButton tray;

    //Ignore Items
    public static IgnoreItemWindow ignoreItemWindow;
    public static ItemIgnorePanel itemIgnorePanel;
    public static AddRemovePanel ignoreItemAddRemovePanel;

    private static HideableDialog[] menuHideFrames;
    private static HideableDialog[] forceFrames;
    private static HideableDialog[] showHideDialogs;

    public static WindowState windowState = WindowState.NORMAL;

    public FrameManager() {
        UIManager.put("ScrollBar.width", 12);
        UIManager.put("ScrollBar.height", 12);
        FrameManager.gridBag = new GridBagLayout();

        // TODO : TEMP Image testing window
//        ImageTestingWindow imageTestingWindow = new ImageTestingWindow();
//        imageTestingWindow.setVisible(true);

        stashHelperContainer = new StashHelperContainer();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();

        // Menu Bar
        menubar = new MenubarDialog();
        menubarToggle = new MenubarExpandButton();
        menubar.init();
        menubarToggle.updateLocation();

        messageManager = new MessageDialogManager();

        chatScannerWindow = new ChatScannerWindow();
        ignoreItemWindow = new IgnoreItemWindow();
        stashOverlayWindow = new StashWindow();

        overlayManager = new OverlayManager();

        stashHelperContainer.setShow(true);


        //TODO : ADD NEW MESSAGE MANAGER
//        showHideDialogs = new HideableDialog[]{stashHelperContainer, historyWindow, menubar, menubarToggle, chatScannerWindow, ignoreItemWindow};
        showHideDialogs = new HideableDialog[]{stashHelperContainer, historyWindow, menubar, menubarToggle};

        //TODO : ADD NEW MESSAGE MANAGER
        forceFrames = new HideableDialog[]{stashHelperContainer, historyWindow, menubar, menubarToggle, ignoreItemWindow};
        menuHideFrames = new HideableDialog[]{optionsWindow, historyWindow, chatScannerWindow};
        menubar.setShow(true);

        tray = new TrayButton();
    }

    public static void hideMenuFrames() {
        for (HideableDialog d : menuHideFrames) {
            d.setShow(false);
        }
    }

    public static void hideAllFrames() {
        for (HideableDialog d : menuHideFrames) {
            d.setShow(false);
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

    public static void centerFrame(JDialog window) {
        window.setLocation((TradeUtility.screenSize.width / 2) - (window.getWidth() / 2), (TradeUtility.screenSize.height / 2) - (window.getHeight() / 2));
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

    public static void showOptionsWindow() {
        optionsWindow.setVisible(true);
        optionsWindow.setAlwaysOnTop(true);
        optionsWindow.setAlwaysOnTop(false);
    }

}
