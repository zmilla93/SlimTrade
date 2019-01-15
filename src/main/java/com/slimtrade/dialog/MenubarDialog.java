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

import main.java.com.slimtrade.FrameManager;
import main.java.com.slimtrade.Main;
import main.java.com.slimtrade.TradeOffer;
import main.java.com.slimtrade.TradeUtility;
import main.java.com.slimtrade.buttons.MenuButton;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.panels.BasicPanel;

public class MenubarDialog extends BasicDialog{
	
	private static final long serialVersionUID = 1L;
	
	private int buttonCount = 9;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenuButton.height*0.8);
	private int totalHeight = MenuButton.height*buttonCount+spacerHeight*spacerCount;
	
	private MenuButton optionsButton;
	private MenuButton historyButton;
	private MenuButton stashButton;
	private MenuButton characterButton;
	private MenuButton testButton;
	private MenuButton clearButton;
	private MenuButton refreshButton;
	private MenuButton quitButton;
	private MenuButton minimizeButton;
	
	public MenubarDialog(){
		//TODO:Toggle off
//		this.setVisible(false);
		this.setBounds(0,TradeUtility.screenSize.height-totalHeight, MenuButton.width, totalHeight);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		optionsButton = new MenuButton("");
		this.add(optionsButton);
		historyButton = new MenuButton("");
		this.add(historyButton);
		stashButton = new MenuButton("");
		this.add(stashButton);
		characterButton = new MenuButton("");
		this.add(characterButton);
		testButton = new MenuButton("");
		this.add(testButton);
		clearButton = new MenuButton("");
		this.add(clearButton);
		refreshButton = new MenuButton("");
		this.add(refreshButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		quitButton = new MenuButton("");
		this.add(quitButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		minimizeButton = new MenuButton("");
		this.add(minimizeButton);
		
		this.refreshButtonText();
		
		//OPTIONS
		optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(FrameManager.optionWindow.isVisible()){
		    		FrameManager.optionWindow.setVisible(false);
		    	}else{
		    		FrameManager.hideAllFrames();
		    		FrameManager.optionWindow.setVisible(true);
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
				TradeOffer t = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12)+1, rng.nextInt(12)+1, "");
				TradeOffer t2 = new TradeOffer(MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12)+1, rng.nextInt(12)+1, "");
				TradeOffer t3 = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "BLANK STASH ITEM", 3.5, "chaos", 3.5, "STASH_TAB", 0, 0, "");
				FrameManager.messageManager.addMessage(t);
				FrameManager.messageManager.addMessage(t2);
//				int i = 0;
//				for(i=0;i<20;i++){
//					FrameManager.messageManager.addMessage(t);
//				}
//				for(i=0;i<20;i++){
//					FrameManager.messageManager.removeMessage(i);
//				}
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
				Locale swede = new Locale("sv", "SE");
				Locale.setDefault(swede);
				refreshButtonText();
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
