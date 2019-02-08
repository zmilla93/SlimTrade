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

	private static int buttonCount = 9;
	private static int spacerCount = 2;
	private static int spacerHeight = (int) (MenubarButton.HEIGHT * 0.8);

	public static final int TOTAL_WIDTH = MenubarButton.WIDTH;
	public static final int TOTAL_HEIGHT = MenubarButton.HEIGHT * buttonCount + spacerHeight * spacerCount;

	private MenubarButton optionsButton;
	private MenubarButton historyButton;
	private MenubarButton stashButton;
	private MenubarButton characterButton;
	private MenubarButton testButton;
	private MenubarButton clearButton;
	private MenubarButton refreshButton;
	private MenubarButton quitButton;
	private MenubarButton minimizeButton;
	private boolean visible = false;
	private boolean order = false;
//	private ArrayList<Component> componentList = new ArrayList<Component>();
	
	private ExpandDirection expandDirection = ExpandDirection.DOWN;

	public MenubarDialog() {
		//TODO : Modify constructor
		Container container = this.getContentPane();
		
		this.setBounds(0, TradeUtility.screenSize.height - TOTAL_HEIGHT, MenubarButton.WIDTH, TOTAL_HEIGHT);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		optionsButton = new MenubarButton("");
		container.add(optionsButton);
		historyButton = new MenubarButton("");
		container.add(historyButton);
		stashButton = new MenubarButton("");
		container.add(stashButton);
		characterButton = new MenubarButton("");
		container.add(characterButton);
		testButton = new MenubarButton("");
		container.add(testButton);
		clearButton = new MenubarButton("");
		container.add(clearButton);
		refreshButton = new MenubarButton("");
		container.add(refreshButton);
		container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
		quitButton = new MenubarButton("");
		container.add(quitButton);
		container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
		minimizeButton = new MenubarButton("");
		container.add(minimizeButton);
		
//		for(Component c : container.getComponents()){
//			componentList.add(c);
//		}
		
		this.refreshButtonText();

		// OPTIONS
		optionsButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (FrameManager.optionsWindow.isVisible()) {
					FrameManager.optionsWindow.setVisible(false);
				} else {
					FrameManager.hideMenuFrames();
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
					FrameManager.hideMenuFrames();
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
					FrameManager.hideMenuFrames();
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
					FrameManager.hideMenuFrames();
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
				FrameManager.menubarToggle.showDialog();
				FrameManager.menubar.hideDialog();
				FrameManager.menubarToggle.refresh();
			}
		});
	}
	
	public void showDialog(){
		this.setVisible(true);
		this.visible = true;
	}
	
	public void hideDialog(){
		this.setVisible(false);
		this.visible = false;
	}	
	
	public void refreshVisibility(){
		this.setVisible(visible);
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
	
	public void updateLocation(){
		this.setLocation(Main.saveManager.getInt("overlayManager", "menubar", "x"), Main.saveManager.getInt("overlayManager", "menubar", "y"));
		
	}
	
//	public void setOrder(ExpandDirection dir){
//		Container container = this.getContentPane();
//		switch(dir){
//		case DOWN:
//			for(Component c : componentList){
//				container.add(c);
//			}
//			break;
//		case UP:
//			for(Component c : componentList){
//				container.add(c, 0);
//			}
//			break;
//		default:
//			break;
//		}
//		this.revalidate();
//		this.repaint();
//	}
	
//	public void reorder(ExpandDirection dir){
//		if(dir == this.expandDirection){
//			return;
//		}else{
//			this.expandDirection = dir;
//			Container container = this.getContentPane();
//			ArrayList<Component> cList = new ArrayList<Component>();
//			for(Component c : container.getComponents()){
//				container.add(c, 0);
//			}
//		}		
//		this.revalidate();
//		this.repaint();
//	}
	
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
			ArrayList<Component> cList = new ArrayList<Component>();
			for(Component c : container.getComponents()){
				container.add(c, 0);
			}
		}		
		this.revalidate();
		this.repaint();
	}

}
