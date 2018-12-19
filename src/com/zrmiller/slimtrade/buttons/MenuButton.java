package com.zrmiller.slimtrade.buttons;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;

public class MenuButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	public static int width = 140;
	public static int height= 22;
	private JPanel togglePanel;
	
	public MenuButton(){
		this.setSize(width, height);
	}
	
	public MenuButton(String text){
		this.setText(text);
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public MenuButton(String text, JPanel panel){
		this.setText(text);
		this.setPreferredSize(new Dimension(width, height));
		this.togglePanel = panel;
	}
	
}
