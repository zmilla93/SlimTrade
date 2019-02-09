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
	
	private int widthBuffer = 20;
	private int heightBuffer = 20;

	public AbstractContentPanel(){
		this.setLayout(new GridBagLayout());
		this.setVisible(false);
		//TODO : Border
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}
	
	protected void autoResize() {
		Dimension size = this.getPreferredSize();
		size.width = size.width + widthBuffer;
		size.height = size.height + heightBuffer;
		this.setMaximumSize(size);
	}
	
	protected void addRow(Component c, GridBagConstraints gc){
		this.add(c, gc);
		gc.gridy++;
	}
	
}
