package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JPanel;

public class BasicPanel extends JPanel{
	
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
