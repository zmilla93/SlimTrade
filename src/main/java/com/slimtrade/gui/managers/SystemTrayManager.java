package com.slimtrade.gui.managers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class SystemTrayManager {

    private static final PopupMenu popupMenu = new PopupMenu();

    // Buttons
    private static final MenuItem optionsButton = new MenuItem("Options");
    private static final MenuItem historyButton = new MenuItem("History");
    private static final MenuItem chatScannerButton = new MenuItem("Chat Scanner");
    private static final MenuItem exitButton = new MenuItem("Exit SlimTrade");

    public static void init() {
        // Tray
        SystemTray tray = SystemTray.getSystemTray();
        Image img;
        try {
            img = ImageIO.read(Objects.requireNonNull(SystemTrayManager.class.getResource("/icons/chaos-icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        TrayIcon trayIcon = new TrayIcon(img);   // First assignment is to get icon size, second assignment scales the image accordingly
        trayIcon = new TrayIcon(img.getScaledInstance(trayIcon.getSize().width, trayIcon.getSize().height, Image.SCALE_SMOOTH));
        trayIcon.setToolTip("SlimTrade");
        trayIcon.setPopupMenu(popupMenu);
        trayIcon.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        addListeners();
        showSimple();
    }

    private static void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        chatScannerButton.addActionListener(e -> FrameManager.chatScannerWindow.setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public static void showSimple() {
        popupMenu.removeAll();
        popupMenu.add(exitButton);
    }

    public static void showDefault() {
        popupMenu.removeAll();
        popupMenu.add(optionsButton);
        popupMenu.add(historyButton);
        popupMenu.add(chatScannerButton);
        popupMenu.addSeparator();
        popupMenu.add(exitButton);
    }

}
