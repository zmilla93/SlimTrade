package com.zrmiller.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.panels.BasicPanel;

public class CharacterWindow extends BasicMenuWindow{

	private static final long serialVersionUID = 1L;
	private static int width = 200;
	private static int height = 100;
	private int rowHeight = 25;
	private Dimension prefDimension = new Dimension(width, rowHeight);
	
	
	public CharacterWindow(){
		super("Character", width, height);
		int totalHeight = rowHeight*5;
		this.setVisible(false);
		this.setLayout(Overlay.flowCenter);
		this.setBounds(Overlay.screenWidth/2-width/2, Overlay.screenHeight/2-rowHeight/2, width, totalHeight);
		this.setPreferredSize(new Dimension(width, totalHeight));
		BasicPanel leaguePanel = new BasicPanel();
		leaguePanel.setBackground(Color.green);
		this.add(leaguePanel);
		
		BasicPanel characterPanel = new BasicPanel();
		characterPanel.setBackground(Color.yellow);
		this.add(characterPanel);
		
	}
	
}
