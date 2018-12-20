package com.zrmiller.slimtrade.buttons;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class BasicButton extends JButton{

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private Color color;
	private Color colorHover;
	private BorderFactory border;
	private BorderFactory borderHover;
	
	public BasicButton(){
		createButton();
	}
	
	public BasicButton(int width, int height){
		this.width = width;
		this.height = height;
		createButton();
	}
	
	private void createButton(){
		this.setPreferredSize(new Dimension(width, height));
		this.setBorderPainted(false);
	}
	
}
