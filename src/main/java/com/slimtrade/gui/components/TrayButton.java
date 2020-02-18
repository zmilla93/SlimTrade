package com.slimtrade.gui.components;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.SaveSystem.OverlaySaveFile;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.enums.WindowState;

public class TrayButton {

    public TrayButton() {

        if (!SystemTray.isSupported()) {
            return;
        }

        final SystemTray tray = SystemTray.getSystemTray();
        final TrayIcon trayIcon = new TrayIcon(PreloadedImage.TAG.getImage());
        final PopupMenu popup = new PopupMenu();

        final MenuItem optionsButton = new MenuItem("Options");
        final MenuItem resetUIButton = new MenuItem("Reset UI to Defaults");
        final MenuItem exitButton = new MenuItem("Exit " + References.APP_NAME);

        popup.add(optionsButton);
        popup.add(resetUIButton);
        popup.addSeparator();
        popup.add(exitButton);
        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("SlimTrade");

        // Reset UI Button
        resetUIButton.addActionListener(e -> {
            App.saveManager.overlaySaveFile = new OverlaySaveFile();
            FrameManager.overlayManager.resetToDefault();
            FrameManager.menubar.updateLocation();
            FrameManager.menubar.reorder();
            FrameManager.messageManager.setAnchorPoint(new Point(App.saveManager.overlaySaveFile.messageX, App.saveManager.overlaySaveFile.messageY));
            FrameManager.messageManager.refreshPanelLocations();
            FrameManager.centerFrame(FrameManager.optionsWindow);
            FrameManager.centerFrame(FrameManager.historyWindow);
            FrameManager.centerFrame(FrameManager.chatScannerWindow);
            App.saveManager.saveOverlayToDisk();
        });

        optionsButton.addActionListener(e -> {
            FrameManager.centerFrame(FrameManager.optionsWindow);
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.optionsWindow.setShow(true);
        });

        // Exit Button
        exitButton.addActionListener(e -> System.exit(0));

        // Tray Button Itself
        //TODO : if in nonnormal window state, reset stash/overlay
        trayIcon.addActionListener(e -> {
            FrameManager.centerFrame(FrameManager.optionsWindow);
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.optionsWindow.setShow(true);
        });

        // Add Tray Button
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

}
