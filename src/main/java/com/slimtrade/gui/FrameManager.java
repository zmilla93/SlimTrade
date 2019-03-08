package main.java.com.slimtrade.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.basic.HideableDialog;
import main.java.com.slimtrade.gui.components.TrayButton;
import main.java.com.slimtrade.gui.history.HistoryWindow;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;
import main.java.com.slimtrade.gui.menubar.MenubarExpandButton;
import main.java.com.slimtrade.gui.messaging.MessageManager;
import main.java.com.slimtrade.gui.options.OptionsWindow;
import main.java.com.slimtrade.gui.scanner.ChatScannerWindow;
import main.java.com.slimtrade.gui.stash.StashOverlayWindow;
import main.java.com.slimtrade.gui.stash.helper.StashHelperContainer;
import main.java.com.slimtrade.gui.windows.OverlayManager;

public class FrameManager {

	public static OptionsWindow optionsWindow;
	public static HistoryWindow historyWindow;
	public static MenubarDialog menubar;
	public static MenubarExpandButton menubarToggle;
	public static MessageManager messageManager;
	public static StashHelperContainer stashHelperContainer;
	public static StashOverlayWindow stashOverlayWindow;
	// public static REMOVE_CharacterWindow characterWindow;
	public static OverlayManager overlayManager;
	// public static REMOVE_StashTabWindow stashTabWindow;
	public static ChatScannerWindow chatScannerWindow;

	private static HideableDialog[] menuFrames;
	private static HideableDialog[] forceFrames;
	private static HideableDialog[] showHideDialogs;

	public FrameManager() {
		stashHelperContainer = new StashHelperContainer();
		optionsWindow = new OptionsWindow();
		historyWindow = new HistoryWindow();
		menubar = new MenubarDialog();
		menubarToggle = new MenubarExpandButton();
		messageManager = new MessageManager();
		overlayManager = new OverlayManager();
		chatScannerWindow = new ChatScannerWindow();
		// TODO : temp
		stashHelperContainer.updateBounds();
		stashOverlayWindow = new StashOverlayWindow();
		// TODO : ????
		stashOverlayWindow.load();

		stashHelperContainer.setShow(true);
		menubar.updateLocation();
		menubarToggle.updateLocation();
		menubar.reorder();
		messageManager.updateLocation();
		// menubar.showDialog();

		// TODO : Cleanup
		showHideDialogs = new HideableDialog[] { stashHelperContainer, optionsWindow, historyWindow, menubar, menubarToggle, messageManager, chatScannerWindow, };

		forceFrames = new HideableDialog[] { stashHelperContainer, historyWindow, menubar, menubarToggle, messageManager, };

		menuFrames = new HideableDialog[] { optionsWindow, historyWindow, chatScannerWindow };

		messageManager.setShow(true);
//		optionsWindow.setShow(true);
		menubar.setShow(true);

		TrayButton tray = new TrayButton();
	}

	public static void hideMenuFrames() {
		for (HideableDialog d : menuFrames) {
			d.setShow(false);
		}
	}

	public static void hideAllFrames() {
		for (HideableDialog d : showHideDialogs) {
			d.setVisible(false);
		}
	}

	public static void showVisibleFrames() {
		for (HideableDialog d : showHideDialogs) {
			d.setVisible(d.visible);
		}
	}

	public static void centerFrame(JDialog window) {
		window.setLocation((TradeUtility.screenSize.width / 2) - (window.getWidth() / 2), (TradeUtility.screenSize.height / 2) - (window.getHeight() / 2));
	}

	public static void forceAllToTop() {
		for (HideableDialog h : forceFrames) {
			h.setAlwaysOnTop(false);
			h.setAlwaysOnTop(true);
		}
		// menubarToggle.forceToTop();
		// menubar.forceToTop();
		// messageManager.forceToTop();
		// stashHelperContainer.forceToTop();
		// PoeInterface.focus();
	}
	
	public static void linkToggle(JButton b, Component c2){
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				boolean vis = !c2.isVisible();
				c2.setVisible(vis);
			}
		});
	}

}
