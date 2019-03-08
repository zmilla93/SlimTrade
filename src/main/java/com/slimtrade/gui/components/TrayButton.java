package main.java.com.slimtrade.gui.components;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.enums.PreloadedImage;

public class TrayButton {

	public TrayButton() {

		if (!SystemTray.isSupported()) {
			return;
		}

		final SystemTray tray = SystemTray.getSystemTray();
		final TrayIcon trayIcon = new TrayIcon(PreloadedImage.DISK.getImage());
		final PopupMenu popup = new PopupMenu();

		final MenuItem optionsButton = new MenuItem("Options");
		final MenuItem exitButton = new MenuItem("Exit");

		popup.add(optionsButton);
		popup.addSeparator();
		popup.add(exitButton);
		trayIcon.setPopupMenu(popup);
		trayIcon.setToolTip("SlimTrade");

		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!FrameManager.optionsWindow.isVisible()) {
					FrameManager.hideMenuFrames();
					FrameManager.optionsWindow.setShow(true);
				}
			}
		});

		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
