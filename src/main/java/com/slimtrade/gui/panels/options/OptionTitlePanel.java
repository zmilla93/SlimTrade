package main.java.com.slimtrade.gui.panels.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import main.java.com.slimtrade.gui.dialogs.OptionsWindow;

public class OptionTitlePanel extends JPanel{

	private static final long serialVersionUID = 1L;

	private int width = OptionsWindow.contentWidth;
	public int height = 25;
	
	public OptionTitlePanel(String title){
		
		this.setLayout(new BorderLayout());
		Dimension size = new Dimension(width, height);
		
		this.setBackground(Color.GREEN);
		this.setMinimumSize(size);
		this.setMaximumSize(size);
		this.setPreferredSize(size);
		
		JPanel innerPanel = new JPanel(new BorderLayout());
		innerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		innerPanel.add(titleLabel, BorderLayout.CENTER);
		this.add(innerPanel, BorderLayout.CENTER);
	}
	
}
