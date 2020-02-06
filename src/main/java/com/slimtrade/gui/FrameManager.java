package com.slimtrade.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

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
import com.slimtrade.gui.scanner.ChatScannerWindow;
import com.slimtrade.gui.stash.StashWindow;
import com.slimtrade.gui.stash.helper.StashHelperContainer;
import com.slimtrade.gui.windows.OverlayManager;

public class FrameManager {

    public static GridBagLayout gridbag;

    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static MenubarDialog menubar;
    public static MenubarExpandButton menubarToggle;
    public static MessageDialogManager messageManager;
    public static StashHelperContainer stashHelperContainer;
    public static StashWindow stashOverlayWindow;
    // public static REMOVE_CharacterWindow characterWindow;
    public static OverlayManager overlayManager;
    // public static REMOVE_StashTabWindow stashTabWindow;
    public static ChatScannerWindow chatScannerWindow;

    //Ignore Items
    public static IgnoreItemWindow ignoreItemWindow;
    public static ItemIgnorePanel itemIgnorePanel;
    public static AddRemovePanel ignoreItemAddRemovePanel;


    private static HideableDialog[] menuFrames;
    private static HideableDialog[] menuHideFrames;
    private static HideableDialog[] forceFrames;
    private static HideableDialog[] showHideDialogs;

    public static WindowState windowState = WindowState.NORMAL;

    public FrameManager() {
//		SetupWindow setupWindow = new SetupWindow();
//		setupWindow.setVisible(true);
        FrameManager.gridbag = new GridBagLayout();

        stashHelperContainer = new StashHelperContainer();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        menubar = new MenubarDialog();
        menubarToggle = new MenubarExpandButton();
        messageManager = new MessageDialogManager();
        overlayManager = new OverlayManager();
        chatScannerWindow = new ChatScannerWindow();
        ignoreItemWindow = new IgnoreItemWindow();
        // TODO : temp
        stashHelperContainer.updateBounds();
        stashOverlayWindow = new StashWindow();
        // TODO : ????
        stashOverlayWindow.load();

        stashHelperContainer.setShow(true);
        menubar.updateLocation();
        menubarToggle.updateLocation();
        menubar.reorder();
        // menubar.showDialog();

        // TODO : Cleanup

        //TODO : ADD NEW MESSAGE MANAGER
//        showHideDialogs = new HideableDialog[]{stashHelperContainer, optionsWindow, historyWindow, menubar, menubarToggle, chatScannerWindow, ignoreItemWindow};
        showHideDialogs = new HideableDialog[]{stashHelperContainer, historyWindow, menubar, menubarToggle, chatScannerWindow, ignoreItemWindow};

        //TODO : ADD NEW MESSAGE MANAGER
        forceFrames = new HideableDialog[]{stashHelperContainer, historyWindow, menubar, menubarToggle, ignoreItemWindow};

//		menuFrames = new HideableDialog[] { optionsWindow, historyWindow, chatScannerWindow };
        menuFrames = new HideableDialog[]{historyWindow};
        menuHideFrames = new HideableDialog[]{optionsWindow, historyWindow, chatScannerWindow};

//		messageManager.setShow(true);
        // optionsWindow.setShow(true);
        menubar.setShow(true);

        TrayButton tray = new TrayButton();
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
        FrameManager.overlayManager.hideDialog();
    }

    public static void showVisibleFrames() {

        switch (windowState) {
            case STASH_OVERLAY:
                break;
            case LAYOUT_MANAGER:
                overlayManager.forceToFront();
                break;
            case NORMAL:
                System.out.println("vis1");
                for (HideableDialog d : showHideDialogs) {
                    System.out.println("DIG:" + d);
                    d.setVisible(d.visible);
                }
                System.out.println("vis2");
                for (HideableDialog d : MessageDialogManager.getDialogList()) {
                    d.setVisible(d.visible);
                }
                System.out.println("vis3");
                break;
        }

    }

    public static void centerFrame(JDialog window) {
        window.setLocation((TradeUtility.screenSize.width / 2) - (window.getWidth() / 2), (TradeUtility.screenSize.height / 2) - (window.getHeight() / 2));
    }

    // TODO : This throws jnativehook nullPointerException during launch if
    // mouse is clicked
    public static void forceAllToTop() {
        for (HideableDialog h : MessageDialogManager.getDialogList()) {
            if (h.isVisible()) {
                h.setAlwaysOnTop(false);
                h.setAlwaysOnTop(true);
            }
        }
        if(FrameManager.windowState == WindowState.NORMAL){
            for (HideableDialog h : forceFrames) {
                if (h != null && h.isVisible()) {
                    h.setAlwaysOnTop(false);
                    h.setAlwaysOnTop(true);
                }
            }
        }


        // menubarToggle.forceToTop();
        // menubar.forceToTop();
        // messageManager.forceToTop();
        // stashHelperContainer.forceToTop();
        // PoeInterface.focus();
    }

//	public static void linkToggle(JButton b, Component c2) {
//		b.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				boolean vis = !c2.isVisible();
//				c2.setVisible(vis);
//			}
//		});
//	}

}
