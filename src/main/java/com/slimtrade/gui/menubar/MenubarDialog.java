package main.java.com.slimtrade.gui.menubar;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.ExpandDirection;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.basic.BasicPanel;

public class MenubarDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;

	private static int buttonCount = 6;
	private static int spacerCount = 2;
	private static int spacerHeight = (int) (MenubarButton.HEIGHT * 0.8);

	public static final int WIDTH = MenubarButton.WIDTH;
	public static final int HEIGHT = (MenubarButton.HEIGHT * buttonCount) + (spacerHeight * spacerCount);
	
	private MenubarButton historyButton;
//	private MenubarButton stashTabButton;
	private MenubarButton chatScannerButton;
//	private MenubarButton characterButton;
	private MenubarButton testButton;
	private MenubarButton optionsButton;
	private MenubarButton quitButton;
	private MenubarButton minimizeButton;
	
	private boolean visible = false;
	private boolean order = false;
//	private ArrayList<Component> componentList = new ArrayList<Component>();
	
	private ExpandDirection expandDirection = ExpandDirection.DOWN;

	public MenubarDialog() {
		//TODO : Modify constructor of menubar buttons
		Container container = this.getContentPane();
		
		//TODO : Switch to gridbag
		this.setBounds(0, TradeUtility.screenSize.height - HEIGHT, WIDTH, HEIGHT);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		
		//TODO : Update Locale
		historyButton = new MenubarButton("");
//		stashTabButton = new MenubarButton("");
		chatScannerButton = new MenubarButton("Chat Scanner");
//		characterButton = new MenubarButton("");
		testButton = new MenubarButton("");
		optionsButton = new MenubarButton("");
		quitButton = new MenubarButton("");
		minimizeButton = new MenubarButton("");
		
		testButton.setToolTipText("This is a test.");
		
		container.add(historyButton);
//		container.add(stashTabButton);
		container.add(chatScannerButton);
//		container.add(characterButton);
		container.add(testButton);
		container.add(optionsButton);
		container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
		container.add(quitButton);
		container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
		container.add(minimizeButton);
		
		this.refreshButtonText();

		//TODO : Move button actions
		
		// HISTORY
		historyButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				boolean vis = !FrameManager.historyWindow.visible;
				FrameManager.hideMenuFrames();
				FrameManager.historyWindow.setShow(vis);
			}
		});
		
		// STASH
//		stashTabButton.addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent evt) {
//				boolean vis = !FrameManager.stashTabWindow.visible;
//				FrameManager.hideMenuFrames();
//				FrameManager.stashTabWindow.setShow(vis);
//			}
//		});
		
		// Chat Scanner
		chatScannerButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				boolean vis = !FrameManager.chatScannerWindow.visible;
				FrameManager.hideMenuFrames();
				FrameManager.chatScannerWindow.setShow(vis);
			}
		});
		
		// CHARACTER
//		characterButton.addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent evt) {
//				boolean vis = !FrameManager.characterWindow.visible;
//				FrameManager.hideMenuFrames();
//				FrameManager.characterWindow.setShow(vis);
//			}
//		});

		// TEST
		testButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				Random rng = new Random();
				TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
				TradeOffer t2 = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
				FrameManager.messageManager.addMessage(t);
				FrameManager.messageManager.addMessage(t2);
				FrameManager.historyWindow.setOrder(false);
			}
		});
		
		// OPTIONS
		optionsButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				boolean vis = !FrameManager.optionsWindow.visible;
				FrameManager.hideMenuFrames();
				FrameManager.optionsWindow.setShow(vis);
			}
		});

		// QUIT PROGRAM
		quitButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				System.exit(0);
			}
		});

		//TODO : Is there a way to avoid calling refresh here?
		minimizeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				FrameManager.menubarToggle.setShow(true);
				FrameManager.menubar.setShow(false);
			}
		});
	}

	private void refreshButtonText() {
		ResourceBundle lang = ResourceBundle.getBundle("lang");
		optionsButton.setText(lang.getString("optionsButton"));
		historyButton.setText(lang.getString("historyButton"));
//		stashTabButton.setText(lang.getString("stashButton"));
//		characterButton.setText(lang.getString("characterButton"));
		testButton.setText(lang.getString("testButton"));
//		clearButton.setText(lang.getString("clearDebugButton"));
//		refreshButton.setText(lang.getString("refreshButton"));
		quitButton.setText(lang.getString("quitButton"));
		minimizeButton.setText(lang.getString("minimizeButton"));
	}
	
	public void updateLocation(){
		this.setLocation(Main.saveManager.getInt("overlayManager", "menubar", "x"), Main.saveManager.getInt("overlayManager", "menubar", "y"));
		
	}
	
	public void reorder(){
		ExpandDirection dir;
		if(Main.saveManager.getString("overlayManager", "menubar", "buttonLocation").contains("Top")){
			dir = ExpandDirection.UP;
		}else{
			dir = ExpandDirection.DOWN;
		}
		if(dir == this.expandDirection){
			return;
		}else{
			this.expandDirection = dir;
			Container container = this.getContentPane();
//			ArrayList<Component> cList = new ArrayList<Component>();
			for(Component c : container.getComponents()){
				container.add(c, 0);
			}
		}		
		this.revalidate();
		this.repaint();
	}

}
