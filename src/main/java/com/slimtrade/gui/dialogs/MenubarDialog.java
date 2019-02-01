package main.java.com.slimtrade.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.ResourceBundle;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.basic.BasicPanel;
import main.java.com.slimtrade.gui.buttons.MenubarButton;

public class MenubarDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;

	private static int buttonCount = 9;
	private static int spacerCount = 2;
	private static int spacerHeight = (int) (MenubarButton.height * 0.8);

	public static final int TOTAL_WIDTH = MenubarButton.width;
	public static final int TOTAL_HEIGHT = MenubarButton.height * buttonCount + spacerHeight * spacerCount;

	private MenubarButton optionsButton;
	private MenubarButton historyButton;
	private MenubarButton stashButton;
	private MenubarButton characterButton;
	private MenubarButton testButton;
	private MenubarButton clearButton;
	private MenubarButton refreshButton;
	private MenubarButton quitButton;
	private MenubarButton minimizeButton;

	public MenubarDialog() {
		this.setBounds(0, TradeUtility.screenSize.height - TOTAL_HEIGHT, MenubarButton.width, TOTAL_HEIGHT);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		optionsButton = new MenubarButton("");
		this.add(optionsButton);
		historyButton = new MenubarButton("");
		this.add(historyButton);
		stashButton = new MenubarButton("");
		this.add(stashButton);
		characterButton = new MenubarButton("");
		this.add(characterButton);
		testButton = new MenubarButton("");
		this.add(testButton);
		clearButton = new MenubarButton("");
		this.add(clearButton);
		refreshButton = new MenubarButton("");
		this.add(refreshButton);
		this.add(new BasicPanel(MenubarButton.width, spacerHeight));
		quitButton = new MenubarButton("");
		this.add(quitButton);
		this.add(new BasicPanel(MenubarButton.width, spacerHeight));
		minimizeButton = new MenubarButton("");
		this.add(minimizeButton);

		this.refreshButtonText();

		// OPTIONS
		optionsButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (FrameManager.optionsWindow.isVisible()) {
					FrameManager.optionsWindow.setVisible(false);
				} else {
					FrameManager.hideAllFrames();
					FrameManager.optionsWindow.setVisible(true);
				}
			}
		});

		// STASH
		stashButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (FrameManager.stashOverlay.isVisible()) {
					FrameManager.stashOverlay.setVisible(false);
				} else {
					FrameManager.hideAllFrames();
					FrameManager.stashOverlay.setVisible(true);
				}
			}
		});

		// HISTORY
		historyButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (FrameManager.historyWindow.isVisible()) {
					FrameManager.historyWindow.setVisible(false);
				} else {
					FrameManager.hideAllFrames();
					FrameManager.historyWindow.setVisible(true);
				}
			}
		});

		// CHARACTER
		characterButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (FrameManager.characterWindow.isVisible()) {
					FrameManager.characterWindow.setVisible(false);
				} else {
					FrameManager.hideAllFrames();
					FrameManager.characterWindow.setVisible(true);
				}
			}
		});

		// TEST
		testButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				Random rng = new Random();
				TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
				TradeOffer t2 = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
				FrameManager.messageManager.addMessage(t);
				FrameManager.messageManager.addMessage(t2);
			}
		});

		clearButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
//				MessagePanel.setDefaultHeight(60);
//				FrameManager.messageManager.rebuild();
				Main.debug.clearLog();
			}
		});

		refreshButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				FrameManager.forceAllToTop();
				// Locale swede = new Locale("sv", "SE");
				// Locale.setDefault(swede);
				// refreshButtonText();
			}
		});

		// QUIT PROGRAM
		quitButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				try {
					FileOutputStream out = new FileOutputStream("userPreferences.txt");
					ObjectOutputStream userPref = new ObjectOutputStream(out);
					String test1 = "Hello 1";
					String test2 = "Hello 2";
					String test3 = "Hello 3";
					userPref.writeObject(test1);
					userPref.writeObject(test2);
					userPref.writeObject(test3);
					userPref.close();
				} catch (IOException err) {
					err.printStackTrace();
				}
				System.exit(0);
			}
		});

		minimizeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				FrameManager.menubar.setVisible(false);
				FrameManager.menubarToggle.setVisible(true);
			}
		});
	}

	private void refreshButtonText() {
		ResourceBundle lang = ResourceBundle.getBundle("lang");
		optionsButton.setText(lang.getString("optionsButton"));
		historyButton.setText(lang.getString("historyButton"));
		stashButton.setText(lang.getString("stashButton"));
		characterButton.setText(lang.getString("characterButton"));
		testButton.setText(lang.getString("testButton"));
		clearButton.setText(lang.getString("clearDebugButton"));
		refreshButton.setText(lang.getString("refreshButton"));
		quitButton.setText(lang.getString("quitButton"));
		minimizeButton.setText(lang.getString("minimizeButton"));
	}

}
