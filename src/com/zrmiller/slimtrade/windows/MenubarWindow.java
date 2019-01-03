package com.zrmiller.slimtrade.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.TradeUtility;
import com.zrmiller.slimtrade.buttons.MenuButton;
import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.panels.BasicPanel;

public class MenubarWindow extends BasicDialog{
	
	private static final long serialVersionUID = 1L;
	
	private int buttonCount = 7;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenuButton.height*0.8);
	private int totalHeight = MenuButton.height*buttonCount+spacerHeight*spacerCount;
	
	private MenuButton optionsButton;
	private MenuButton historyButton;
	private MenuButton stashButton;
	private MenuButton characterButton;
	private MenuButton testButton;
	private MenuButton quitButton;
	private MenuButton minimizeButton;
	
	public MenubarWindow(){
		//TODO:Toggle off
//		this.setVisible(false);
		
		this.setBounds(0,TradeUtility.screenSize.height-totalHeight, MenuButton.width, totalHeight);
		
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
		
		optionsButton = new MenuButton("Options");
		this.add(optionsButton);
		historyButton = new MenuButton("History");
		this.add(historyButton);
		stashButton = new MenuButton("Stash");
		this.add(stashButton);
		characterButton = new MenuButton("Character");
		this.add(characterButton);
		testButton = new MenuButton("TEST");
		this.add(testButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		quitButton = new MenuButton("Quit");
		this.add(quitButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		minimizeButton = new MenuButton("Minimize");
		this.add(minimizeButton);
		
		//OPTIONS
		optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.optionWindow.isVisible()){
		    		Overlay.optionWindow.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.optionWindow.setVisible(true);
		    	}
		    }
		});
		
		//STASH
		stashButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.stashGridOverlay.isVisible()){
		    		Overlay.stashGridOverlay.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.stashGridOverlay.setVisible(true);
		    	}
		    }
		});
		
		//HISTORY
		historyButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.historyWindow.isVisible()){
		    		Overlay.historyWindow.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.historyWindow.setVisible(true);
		    	}
		    }
		});
		
		//CHARACTER
		characterButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.characterWindow.isVisible()){
		    		Overlay.characterWindow.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.characterWindow.setVisible(true);
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
				Overlay.messageManager.addMessage(t);
				Overlay.messageManager.addMessage(t2);
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
		    	Overlay.menubarWindow.setVisible(false);
		    	Overlay.menubarShowButton.setVisible(true);
		    	
		    }
		});
		
	}
	
	public void updateCharacterButton(String s){
		characterButton.setText(s);
	}
	
}
