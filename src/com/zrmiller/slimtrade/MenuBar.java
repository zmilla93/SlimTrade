package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MenuBar extends JPanel{

	int buttonSize = 20;
	Dimension dim = new Dimension(buttonSize, buttonSize);
	int buttonCount = 3;
	
	JButton plusButton = new JButton();
	JButton exitButton = new JButton();
	JButton b1 = new JButton();
	JButton b2 = new JButton();
	JButton b3 = new JButton();
	
	public MenuBar(){
		//Panel
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setSize(buttonSize*buttonCount,buttonSize);
		this.setBackground(Color.red);
		
		//
		b1.setPreferredSize(dim);
		b1.setMaximumSize(dim);
		this.add(b1);
		
		//Plus Button
		plusButton.setPreferredSize(dim);
		plusButton.setMaximumSize(dim);
		System.out.println(this.getClass().getResource("/plus.png"));
		plusButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/plus.png")).getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
		this.add(plusButton);
		
		//Exit Button
		exitButton.setPreferredSize(dim);
		exitButton.setMaximumSize(dim);
		exitButton.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/close.png")).getImage().getScaledInstance(buttonSize, buttonSize, Image.SCALE_SMOOTH)));
		System.out.println();
		this.add(exitButton);
		exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {System.exit(0);}
		});
	}
	
}
