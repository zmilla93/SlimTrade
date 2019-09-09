package com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ContentPanel_REMOVE extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int bufferWidth = 20;
	private int bufferHeight = 20;
	protected GridBagConstraints gc;
//	protected Insets inset = new Insets(0,0,0,0);
	
	public ContentPanel_REMOVE(){
		build(true);
	}
	
	public ContentPanel_REMOVE(boolean vis){
		build(vis);
	}
	
	private void build(boolean visible){
		this.setLayout(new GridBagLayout());
		this.setVisible(visible);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gc = new GridBagConstraints();
//		gc.insets = inset;
		gc.gridx = 0;
		gc.gridy = 0;
	}
	
	//TODO : Recursive resize?
	public void autoResize() {
//		for()
		
		this.setPreferredSize(null);
		Dimension size = this.getPreferredSize();
		size.width = size.width + bufferWidth;
		size.height = size.height + bufferHeight;
//		this.setMinimumSize(size);
		this.setPreferredSize(size);
//		this.setMaximumSize(size);
		this.revalidate();
		this.repaint();
	}
	
	public void addRow(Component c, GridBagConstraints gc){
		this.add(c, gc);
		gc.gridy++;
	}
	
	public void setBuffer(int width, int height){
		int w = width==-1 ? bufferWidth : width;
		int h = height==-1 ? bufferHeight : height;
		this.bufferWidth = w;
		this.bufferHeight = h;
	}
	
}
