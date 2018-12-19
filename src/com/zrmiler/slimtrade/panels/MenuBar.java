package com.zrmiler.slimtrade.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.zrmiler.slimtrade.Main;
import com.zrmiller.slimtrade.buttons.MenuButton;

public class MenuBar extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private MenuButton quitButton;
	private int buttonCount = 5;
	private int spacerCount = 2;
	private int spacerHeight = (int)(MenuButton.height*0.8);
	private int totalHeight = MenuButton.height*buttonCount+spacerHeight*spacerCount;
	private int posY = Main.screenSize.height-totalHeight;
	
	//TODO : FIX
	
	public MenuBar(){
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0,0));
		
		MenuButton optionsButton = new MenuButton("Options");
		this.add(optionsButton);
		optionsButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	//.setVisible(true);
		    }
		});
		
		MenuButton resizeButton = new MenuButton("Resize");
		this.add(resizeButton);
		
		MenuButton characerButton = new MenuButton("Character");
		this.add(characerButton);
		
		MenuButton spacer1 = new MenuButton(" ");
		spacer1.setPreferredSize(new Dimension(MenuButton.width, spacerHeight));
		this.add(spacer1);
		
		quitButton = new MenuButton("Quit");
		this.add(quitButton);
		
		//TODO : Change to real spacer
		MenuButton spacer2 = new MenuButton(" ");
		spacer2.setPreferredSize(new Dimension(MenuButton.width, spacerHeight));
		this.add(spacer2);
		
		MenuButton minimizeButton = new MenuButton("Minimize");
		this.add(minimizeButton);
		
		this.setBounds(0, posY, MenuButton.width, totalHeight);
		this.setVisible(true);
		
	}
	
	public JButton getQuitButton(){
		return this.quitButton;
	}
	
}
