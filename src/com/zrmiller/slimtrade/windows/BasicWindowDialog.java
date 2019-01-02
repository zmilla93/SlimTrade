package com.zrmiller.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.buttons.BasicIconButton;

public class BasicWindowDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;
	
	private int defaultWidth = 400;
	private int defaultHeight = 800;
	private int buttonSize = 20;
	private static JPanel menubarPanel = new JPanel();
	
	//INTERNAL
	
	public BasicWindowDialog(){
		super(menubarPanel);
		
		this.setLayout(Overlay.flowLeft);
		this.setSize(new Dimension(defaultWidth, defaultHeight));
		
		JPanel menubarContainer = new JPanel();
		menubarContainer.setLayout(Overlay.flowLeft);
		menubarContainer.setPreferredSize(new Dimension(defaultWidth, buttonSize));
		menubarContainer.setBackground(Color.LIGHT_GRAY);

		menubarPanel.setPreferredSize(new Dimension(defaultWidth-buttonSize, buttonSize));
		menubarPanel.setBackground(Color.GRAY);
		
		BasicIconButton closeButton = new BasicIconButton("/close.png", buttonSize, buttonSize);
		
		menubarContainer.add(menubarPanel);
		menubarContainer.add(closeButton);
		this.add(menubarContainer);
		moverPanel = menubarPanel;
		
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					hideWindow();
				}
			}
		});
		
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}
	
	
}
