package main.java.com.slimtrade.panels.options;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class OptionContentPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public OptionContentPanel(int width, int height){
		this.setBackground(Color.YELLOW);
		Dimension size = new Dimension(width, height);
		this.setPreferredSize(size);
//		this.setMinimumSize(new Dimension(width, 100));
		this.setMaximumSize(size);
		this.setVisible(false);
		this.revalidate();
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
	}

}
