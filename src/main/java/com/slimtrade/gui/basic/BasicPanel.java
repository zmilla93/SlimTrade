package com.slimtrade.gui.basic;

import javax.swing.*;
import java.awt.*;

public class BasicPanel extends JPanel{
	
	//TODO : Consider removing
	private static final long serialVersionUID = 1L;
	private static int rowWidth;
	private static int rowHeight;
	private static Color color = Color.red;
	
	public BasicPanel(){
		this.setPreferredSize(new Dimension(rowWidth, rowHeight));
		this.setBackground(new Color(1.0F, 1.0F, 1.0F, 0.0F));
	}
	
	public BasicPanel(int width, int height){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(1.0F, 1.0F, 1.0F, 0.0F));
	}
	
	public BasicPanel(int width, int height, Color color){
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(color);
	}
	
	public BasicPanel(int width, int height, LayoutManager layout){
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(new Color(1.0F, 1.0F, 1.0F, 0.0F));
	}
	
	public BasicPanel(int width, int height, Color color, LayoutManager layout){
		this.setLayout(layout);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(color);
	}
	
	public static void setPanelSize(int width, int height){
		BasicPanel.rowWidth = width;
		BasicPanel.rowHeight = height;
	}
	
	public static void setPanelColor(Color color){
		BasicPanel.color = color;
	}
	
}
