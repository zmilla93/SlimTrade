package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class BasicPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private static int rowWidth;
	private static int rowHeight;
	private static Color color = Color.red;
	
	public BasicPanel(){
		this.setPreferredSize(new Dimension(rowWidth, rowHeight));
	}
	
	public BasicPanel(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
	}
	
	public BasicPanel(int width, int height, Color color){
		this.setPreferredSize(new Dimension(width, height));
		if(color == null){
			this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		}else{
			this.setBackground(color);
		}
		
	}
	
	public static void setPanelSize(int width, int height){
		BasicPanel.rowWidth = width;
		BasicPanel.rowHeight = height;
	}
	
	public static void setPanelColor(Color color){
		BasicPanel.color = color;
	}
	
}
