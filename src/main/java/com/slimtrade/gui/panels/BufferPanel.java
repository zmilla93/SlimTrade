package com.slimtrade.gui.panels;

import javax.swing.*;
import java.awt.*;

public class BufferPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public BufferPanel(int width, int height){
		Dimension size = new Dimension(width, height);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setBackground(new Color(1.0F, 1.0F, 1.0F, 0.0F));
		this.setOpaque(false);
	}
	
}
