package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class AbstractContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int bufferWidth = 20;
	private int bufferHeight = 20;
	protected GridBagConstraints gc;
	
	public AbstractContentPanel(){
		this.setLayout(new GridBagLayout());
		this.setVisible(false);
		//TODO : Border
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
	}
	
	protected void autoResize() {
		Dimension size = this.getPreferredSize();
		size.width = size.width + bufferWidth;
		size.height = size.height + bufferHeight;
//		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setMaximumSize(size);
	}
	
	protected void addRow(Component c, GridBagConstraints gc){
		this.add(c, gc);
		gc.gridy++;
	}
	
	protected void setBuffer(int width, int height){
		int w = width==-1 ? bufferWidth : width;
		int h = height==-1 ? bufferHeight : height;
		this.bufferWidth = w;
		this.bufferHeight = h;
	}
	
}
