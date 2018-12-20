package com.zrmiller.slimtrade.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.buttons.MenuButton;

public class Menubar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MenuButton quitButton;
	private int buttonCount = 6;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenuButton.height*0.8);
	private int totalHeight = MenuButton.height*buttonCount+spacerHeight*spacerCount;
	
	public Menubar(){
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
