package com.slimtrade.gui.managers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class SystemTrayManager implements ActionListener {

    // Tray
    private final SystemTray tray;
    private TrayIcon trayIcon;
    private PopupMenu popupMenu = new PopupMenu();

    // Buttons
    private final MenuItem optionsButton = new MenuItem("Options");
    private final MenuItem historyButton = new MenuItem("History");
    private final MenuItem exitButton = new MenuItem("Exit SlimTrade");

    public SystemTrayManager() {
        tray = SystemTray.getSystemTray();
        Image img;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/icons/slim-icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        trayIcon = new TrayIcon(img);
        trayIcon = new TrayIcon(img.getScaledInstance(trayIcon.getSize().width, trayIcon.getSize().height, Image.SCALE_SMOOTH));
        trayIcon.setPopupMenu(popupMenu);
        trayIcon.addActionListener(this);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        addListeners();
        showDefault();
    }

    private void addListeners() {
        optionsButton.addActionListener(e -> FrameManager.optionsWindow.setVisible(true));
        historyButton.addActionListener(e -> FrameManager.historyWindow.setVisible(true));
        exitButton.addActionListener(e -> System.exit(0));
    }

    public void showSimple() {
        popupMenu.removeAll();
        popupMenu.add(exitButton);
    }

    public void showDefault() {
        popupMenu.removeAll();
        popupMenu.add(optionsButton);
        popupMenu.add(historyButton);
        popupMenu.addSeparator();
        popupMenu.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        FrameManager.optionsWindow.setVisible(true);
    }

}
