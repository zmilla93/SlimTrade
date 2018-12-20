package com.zrmiller.slimtrade.windows;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.buttons.MenuButton;
import com.zrmiller.slimtrade.panels.BasicPanel;

public class MenubarWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MenuButton quitButton;
	private int buttonCount = 6;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenuButton.height*0.8);
	private int totalHeight = MenuButton.height*buttonCount+spacerHeight*spacerCount;
	
	public MenubarWindow(){
		//TODO:Toggle off
//		this.setVisible(false);
		this.setBounds(0, Overlay.screenHeight-totalHeight, MenuButton.width, totalHeight);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
		
		MenuButton optionsButton = new MenuButton("Options");
		this.add(optionsButton);
		MenuButton historyButton = new MenuButton("History");
		this.add(historyButton);
		MenuButton stashButton = new MenuButton("Stash");
		this.add(stashButton);
		MenuButton characterButton = new MenuButton("Character");
		this.add(characterButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		quitButton = new MenuButton("Quit");
		this.add(quitButton);
		this.add(new BasicPanel(MenuButton.width, spacerHeight));
		MenuButton minimizeButton = new MenuButton("Minimize");
		this.add(minimizeButton);
		
		//OPTIONS
		optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.optionPanel.isVisible()){
		    		Overlay.optionPanel.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.optionPanel.setVisible(true);
		    	}
		    }
		});
		
		//STASH
		stashButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.stashWindow.isVisible()){
		    		Overlay.stashWindow.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.stashWindow.setVisible(true);
		    	}
		    }
		});
		
		//HISTORY
		historyButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.historyPanel.isVisible()){
		    		Overlay.historyPanel.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.historyPanel.setVisible(true);
		    	}
		    }
		});
		
		//CHARACTER
		characterButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(Overlay.characterPanel.isVisible()){
		    		Overlay.characterPanel.setVisible(false);
		    	}else{
		    		Overlay.hideAllTempFrames();
		    		Overlay.characterPanel.setVisible(true);
		    	}
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
		    	Overlay.menubar.setVisible(false);
		    	Overlay.menubarShowButton.setVisible(true);
		    	
		    }
		});
		
	}
	
}
