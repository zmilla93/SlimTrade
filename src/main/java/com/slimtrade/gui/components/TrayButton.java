package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.saving.OverlaySaveFile;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.enums.WindowState;

import java.awt.*;

public class TrayButton {

    private final SystemTray tray = SystemTray.getSystemTray();
    private final TrayIcon trayIcon = new TrayIcon(DefaultIcons.TAG.getImage());
    private final PopupMenu popup = new PopupMenu();

    private final MenuItem historyButton = new MenuItem("History");
    private final MenuItem chatScannerButton = new MenuItem("Chat Scanner");
    private final MenuItem optionsButton = new MenuItem("Options");
    private final MenuItem resetUIButton = new MenuItem("Reset UI to Defaults");
    private final MenuItem exitButton = new MenuItem("Exit " + References.APP_NAME);

    public TrayButton() {

        if (!SystemTray.isSupported()) {
            return;
        }

//        popup.add(historyButton);
//        popup.add(chatScannerButton);
//        popup.add(optionsButton);
//        popup.addSeparator();
//        popup.add(resetUIButton);
//        popup.addSeparator();
        popup.add(exitButton);
        trayIcon.setPopupMenu(popup);
        trayIcon.setToolTip("SlimTrade");

        // Reset UI Button
        resetUIButton.addActionListener(e -> {
            App.saveManager.pinSaveFile.optionsPin.pinned = false;
            App.saveManager.pinSaveFile.historyPin.pinned = false;
            App.saveManager.pinSaveFile.chatScannerPin.pinned = false;
            App.saveManager.savePinsToDisk();
            FrameManager.optionsWindow.load();
            FrameManager.historyWindow.load();
            FrameManager.chatScannerWindow.load();
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
            ColorManager.updateAllColors(ColorManager.getCurrentColorTheme());
        });

        historyButton.addActionListener(e -> {
            FrameManager.centerFrame(FrameManager.historyWindow);
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.historyWindow.setShow(true);
        });

        chatScannerButton.addActionListener(e -> {
            FrameManager.centerFrame(FrameManager.chatScannerWindow);
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.chatScannerWindow.setShow(true);
        });

        optionsButton.addActionListener(e -> {
            FrameManager.centerFrame(FrameManager.optionsWindow);
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.optionsWindow.setShow(true);
        });

        // Exit Button
        exitButton.addActionListener(e -> System.exit(0));

    }

    public void addToTray() {
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void removeFromTray() {
        tray.remove(trayIcon);
    }

    public void addAdditionalOptions() {
        popup.removeAll();
        popup.add(historyButton);
        popup.add(chatScannerButton);
        popup.add(optionsButton);
        popup.addSeparator();
        popup.add(resetUIButton);
        popup.addSeparator();
        popup.add(exitButton);

        trayIcon.addActionListener(e -> {
            FrameManager.hideMenuFrames();
            FrameManager.windowState = WindowState.NORMAL;
            FrameManager.optionsWindow.setShow(true);
        });

    }

}
