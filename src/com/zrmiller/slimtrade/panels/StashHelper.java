package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;

public class StashHelper extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int width = 100;
	public static int height=20;
	
	public StashHelper(){
		this.setLayout(Overlay.flowCenter);
		this.setPreferredSize(new Dimension(width, height));
		Random rand = new Random();
		int r = rand.nextInt(255)+1;
		int g = rand.nextInt(255)+1;
		int b = rand.nextInt(255)+1;
		this.setBackground(new Color(r,g,b));
	}
	
}
