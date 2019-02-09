package main.java.com.slimtrade.gui;

import java.awt.Dimension;
import java.awt.Point;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;
import main.java.com.slimtrade.gui.menubar.MenubarExpandButton;
import main.java.com.slimtrade.gui.messaging.ItemHighlighter;
import main.java.com.slimtrade.gui.messaging.MessageManager;
import main.java.com.slimtrade.gui.messaging.StashHelperContainer;
import main.java.com.slimtrade.gui.options.OptionsWindow;
import main.java.com.slimtrade.gui.stashtab.StashTabWindow;
import main.java.com.slimtrade.gui.windows.CharacterWindow;
import main.java.com.slimtrade.gui.windows.ChatScannerWindow;
import main.java.com.slimtrade.gui.windows.HistoryWindow;
import main.java.com.slimtrade.gui.windows.OverlayManager;
import main.java.com.slimtrade.gui.windows.StashGridOverlay;

public class FrameManager {

	public static OptionsWindow optionsWindow;
	public static HistoryWindow historyWindow;
	public static MenubarDialog menubar;
	public static MenubarExpandButton menubarToggle;
	public static MessageManager messageManager;
	public static StashHelperContainer stashHelperContainer;
	public static StashGridOverlay stashOverlay;
	public static CharacterWindow characterWindow;
	public static OverlayManager overlayManager;
	public static StashTabWindow stashTabWindow;
	public static ChatScannerWindow chatScannerWindow;

	public FrameManager() {

		optionsWindow = new OptionsWindow();
		historyWindow = new HistoryWindow();
		menubar = new MenubarDialog();
		menubarToggle = new MenubarExpandButton();
		messageManager = new MessageManager();
		stashHelperContainer = new StashHelperContainer();
		characterWindow = new CharacterWindow();
		overlayManager = new OverlayManager();
		stashTabWindow = new StashTabWindow();
		chatScannerWindow = new ChatScannerWindow();
		//TODO : temp
//		stashTabWindow.setVisible(true);
//		chatScannerWindow.setVisible(true);
		
//		ColorManager.setMessageTheme();
		
		if (Main.fileManager.hasStashData) {
			int[] stashData = Main.fileManager.getStashData();
			stashOverlay = new StashGridOverlay(new Point(stashData[0], stashData[1]), stashData[2], stashData[3]);
			StashGridOverlay.setDefaultGridPos(new Point(stashData[4], stashData[5]));
			StashGridOverlay.setDefaultGridSize(new Dimension(stashData[6], stashData[7]));
			ItemHighlighter.saveGridInfo(stashData[4], stashData[5], (double) (stashData[6] / 12.0), (double) (stashData[7] / 12.0));
			stashHelperContainer.updateBounds();
		} else {
			stashOverlay = new StashGridOverlay(new Point(0, 0), 400, 400);
			stashHelperContainer.updateBounds(0, 0, 0);
		}

		if (Main.fileManager.hasCharacterData) {
			String[] chracterData = new String[2];
			chracterData = Main.fileManager.getCharacterData();
			characterWindow.setCharacter(chracterData[0], chracterData[1]);
		}

		// LOAD DATA

		menubar.updateLocation();
		menubarToggle.updateLocation();
		menubar.reorder();
		messageManager.updateLocation();
		menubar.showDialog();

	}

	public static void hideMenuFrames() {
		historyWindow.setVisible(false);
		stashTabWindow.setVisible(false);
		chatScannerWindow.setVisible(false);
		characterWindow.setVisible(false);
		optionsWindow.setVisible(false);
		
	}

	public static void centerFrame(BasicDialog window) {
		window.setLocation((TradeUtility.screenSize.width / 2) - (window.getWidth() / 2), (TradeUtility.screenSize.height / 2) - (window.getHeight() / 2));
	}

	public static void forceAllToTop() {
		if (menubarToggle.isVisible()) {

		}
		if (menubar.isVisible()) {

		}
		menubarToggle.forceToTop();
		menubar.forceToTop();
		messageManager.forceToTop();
		stashHelperContainer.forceToTop();
		// PoeInterface.focus();
	}

}
