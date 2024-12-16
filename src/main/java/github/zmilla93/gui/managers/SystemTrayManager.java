package github.zmilla93.gui.managers;

import github.zmilla93.App;
import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.modules.updater.ZLogger;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SystemTrayManager {

    private static final PopupMenu popupMenu = new PopupMenu();

    // Buttons
    private static final MenuItem optionsButton = new MenuItem("Options");
    private static final MenuItem historyButton = new MenuItem("History");
    private static final MenuItem chatScannerButton = new MenuItem("Chat Scanner");
    private static final MenuItem resetUIButton = new MenuItem("Reset UI Position");
    private static final MenuItem exitButton = new MenuItem("Exit SlimTrade");

    public static void init() {
        if (!SystemTray.isSupported()) return;
        // Tray
        SystemTray tray = SystemTray.getSystemTray();
        Image img = new ImageIcon(Objects.requireNonNull(SystemTrayManager.class.getResource(DefaultIcon.CHAOS_ORB.path()))).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        TrayIcon trayIcon = new TrayIcon(img);
        trayIcon.setImageAutoSize(true);
        trayIcon.setToolTip(App.getAppInfo().fullName);
        trayIcon.setPopupMenu(popupMenu);
        trayIcon.addActionListener(e -> {
            if (App.getState() == AppState.RUNNING) FrameManager.optionsWindow.setVisible(true);
        });
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            ZLogger.err("Failed to set tray icon.");
            ZLogger.log(e.getStackTrace());
        }
        addListeners();
        showSimple();
    }

    private static void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        chatScannerButton.addActionListener(e -> FrameManager.chatScannerWindow.setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));
        resetUIButton.addActionListener(e -> FrameManager.requestRestoreUIDefaults());
    }

    public static void showSimple() {
        if (!SystemTray.isSupported()) return;
        popupMenu.removeAll();
        popupMenu.add(resetUIButton);
        popupMenu.addSeparator();
        popupMenu.add(exitButton);
    }

    public static void showDefault() {
        if (!SystemTray.isSupported()) return;
        popupMenu.removeAll();
        popupMenu.add(optionsButton);
        popupMenu.add(historyButton);
        popupMenu.add(chatScannerButton);
        popupMenu.addSeparator();
        popupMenu.add(resetUIButton);
        popupMenu.addSeparator();
        popupMenu.add(exitButton);
    }

}
