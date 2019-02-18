package main.java.com.slimtrade.gui;

import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.history.HistoryWindow;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;
import main.java.com.slimtrade.gui.menubar.MenubarExpandButton;
import main.java.com.slimtrade.gui.messaging.MessageManager;
import main.java.com.slimtrade.gui.options.OptionsWindow;
import main.java.com.slimtrade.gui.stash.StashOverlayWindow;
import main.java.com.slimtrade.gui.stash.StashTabWindow;
import main.java.com.slimtrade.gui.stash.helper.StashHelperContainer;
import main.java.com.slimtrade.gui.windows.CharacterWindow;
import main.java.com.slimtrade.gui.windows.ChatScannerWindow;
import main.java.com.slimtrade.gui.windows.OverlayManager;

public class FrameManager {

	public static OptionsWindow optionsWindow;
	public static HistoryWindow historyWindow;
	public static MenubarDialog menubar;
	public static MenubarExpandButton menubarToggle;
	public static MessageManager messageManager;
	public static StashHelperContainer stashHelperContainer;
	public static StashOverlayWindow stashOverlayWindow;
	public static CharacterWindow characterWindow;
	public static OverlayManager overlayManager;
	public static StashTabWindow stashTabWindow;
	public static ChatScannerWindow chatScannerWindow;

	public FrameManager() {
		stashHelperContainer = new StashHelperContainer();
		optionsWindow = new OptionsWindow();
		historyWindow = new HistoryWindow();
		menubar = new MenubarDialog();
		menubarToggle = new MenubarExpandButton();
		messageManager = new MessageManager();
		
		
		
		characterWindow = new CharacterWindow();
		overlayManager = new OverlayManager();
		stashTabWindow = new StashTabWindow();
		chatScannerWindow = new ChatScannerWindow();
		//TODO : temp
//		stashTabWindow.setVisible(true);
//		chatScannerWindow.setVisible(true);
		stashHelperContainer.updateBounds();
		stashOverlayWindow = new StashOverlayWindow();
		stashOverlayWindow.load();
//		stashOverlayWindow.setVisible(true);
	
	
		menubar.updateLocation();
		menubarToggle.updateLocation();
		menubar.reorder();
		messageManager.updateLocation();
		menubar.showDialog();
		
		historyWindow.setVisible(true);
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
