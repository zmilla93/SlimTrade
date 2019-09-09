package com.slimtrade.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ContainerPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	public JPanel container = new JPanel();
	private static final int defaultBorderSize = 15;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.black);
	
	public ContainerPanel(){
		this(defaultBorderSize);
	}
	
	private ContainerPanel(int borderSize){
		
//		this.setBackground(ColorManager.BACKGROUND);
//		container.setOpaque(false);
		container.setOpaque(false);
//		container.setBorder(BorderFactory.createLineBorder(Color.RED));
		this.setBorder(defaultBorder);
		this.setLayout(new BorderLayout());
		this.add(new BufferPanel(0, borderSize), BorderLayout.NORTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.WEST);
		this.add(new BufferPanel(0, borderSize), BorderLayout.SOUTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.EAST);
		this.add(container, BorderLayout.CENTER);
		
		//TODO : Resolve this?
//		this.updateColor();
//		Main.eventManager.addListener(this);
	}
		
}
