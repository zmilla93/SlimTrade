package main.java.com.slimtrade.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

import main.java.com.slimtrade.buttons.MenubarButton;
import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.TradeOffer;
import main.java.com.slimtrade.core.TradeUtility;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.panels.BasicPanel;

public class MenubarDialog extends BasicDialog{
	
	private static final long serialVersionUID = 1L;
	
	private int buttonCount = 9;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenubarButton.height*0.8);
	private int totalHeight = MenubarButton.height*buttonCount+spacerHeight*spacerCount;
	
	private MenubarButton optionsButton;
	private MenubarButton historyButton;
	private MenubarButton stashButton;
	private MenubarButton characterButton;
	private MenubarButton testButton;
	private MenubarButton clearButton;
	private MenubarButton refreshButton;
	private MenubarButton quitButton;
	private MenubarButton minimizeButton;
	
	public MenubarDialog(){
		this.setBounds(0,TradeUtility.screenSize.height-totalHeight, MenubarButton.width, totalHeight);
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
		
		//OPTIONS
		optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(FrameManager.optionsWindow.isVisible()){
		    		FrameManager.optionsWindow.setVisible(false);
		    	}else{
		    		FrameManager.hideAllFrames();
		    		FrameManager.optionsWindow.setVisible(true);
		    	}
		    }
		});
		
		//STASH
		stashButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(FrameManager.stashOverlay.isVisible()){
		    		FrameManager.stashOverlay.setVisible(false);
		    	}else{
		    		FrameManager.hideAllFrames();
		    		FrameManager.stashOverlay.setVisible(true);
		    	}
		    }
		});
		
		//HISTORY
		historyButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(FrameManager.historyWindow.isVisible()){
		    		FrameManager.historyWindow.setVisible(false);
		    	}else{
		    		FrameManager.hideAllFrames();
		    		FrameManager.historyWindow.setVisible(true);
		    	}
		    }
		});
		
		//CHARACTER
		characterButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(FrameManager.characterWindow.isVisible()){
		    		FrameManager.characterWindow.setVisible(false);
		    	}else{
		    		FrameManager.hideAllFrames();
		    		FrameManager.characterWindow.setVisible(true);
		    	}
		    }
		});
		
		//TEST
		testButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				Random rng = new Random();
				TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12)+1, rng.nextInt(12)+1, "", "");
				TradeOffer t2 = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12)+1, rng.nextInt(12)+1, "", "");
//				TradeOffer t3 = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "BLANK STASH ITEM", 3.5, "chaos", 3.5, "STASH_TAB", 0, 0, "");
				FrameManager.messageManager.addMessage(t);
				FrameManager.messageManager.addMessage(t2);
			}
		});
		
		
		clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				Main.debug.clearLog();
			}
		});
		
		refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				FrameManager.forceAllToTop();
//				Locale swede = new Locale("sv", "SE");
//				Locale.setDefault(swede);
//				refreshButtonText();
			}
		});
		
		//QUIT PROGRAM
		quitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
		
		minimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	FrameManager.menubar.setVisible(false);
		    	FrameManager.menubarToggle.setVisible(true);
		    }
		});
	}
	
	private void refreshButtonText(){
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
