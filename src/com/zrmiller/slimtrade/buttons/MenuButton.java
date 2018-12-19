package com.zrmiller.slimtrade.buttons;

import java.awt.Dimension;

import javax.swing.JButton;

public class MenuButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	public static int width = 140;
	public static int height= 22;
	
	public MenuButton(){
		this.setSize(width, height);
	}
	
	public static void t2(){
		
	}
	
	public MenuButton(String l){
		this.setText(l);
		this.setPreferredSize(new Dimension(width, height));
	}
	
}
