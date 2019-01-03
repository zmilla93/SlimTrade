package com.zrmiller.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.buttons.IconButton;

public class BasicWindowDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;
	
	private int defaultWidth = 400;
	private int defaultHeight = 800;
	private int buttonSize = 20;
	private static int titleOffset = 5;
	
	private static JPanel menubarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
	
	//INTERNAL
	
	public BasicWindowDialog(){
		super(menubarPanel);
		buildDialog("");
	}
	
	public BasicWindowDialog(String title){
		super(menubarPanel);
		buildDialog(title);
	}
	
	private void buildDialog(String title){
		
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setSize(new Dimension(defaultWidth, defaultHeight));
		
		JPanel menubarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0 , 0));
		menubarContainer.setPreferredSize(new Dimension(defaultWidth, buttonSize));
		menubarContainer.setBackground(Color.LIGHT_GRAY);
		JLabel titleLabel = new JLabel(title);
		menubarPanel.add(titleLabel);

		menubarPanel.setPreferredSize(new Dimension(defaultWidth-buttonSize, buttonSize));
		menubarPanel.setBackground(Color.GRAY);
		
		IconButton closeButton = new IconButton("/close.png", buttonSize, buttonSize);
		
		menubarContainer.add(menubarPanel);
		menubarContainer.add(closeButton);
		this.add(menubarContainer);
		moverPanel = menubarPanel;
		
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					hideWindow();
					//TEMP
					System.exit(0);
				}
			}
		});
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}
	
	
}
