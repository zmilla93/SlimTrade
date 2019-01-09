package com.zrmiller.slimtrade.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.buttons.IconButton;

public class BasicWindowDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;

	private static int defaultWidth = 400;
	private static int defaultHeight = 800;
	private static int buttonSize = 20;
	private static int titleOffset = 5;
	
	public Container container = new JPanel();
	private JPanel menubarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	private JPanel menubarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
	
//	private static JPanel menubarPanel;
	
	public IconButton closeButton = new IconButton("/close.png", buttonSize, buttonSize);
	
	
	
	//INTERNAL
	
	public BasicWindowDialog(){
//		super(menubarPanel);
		buildDialog("");
	}
	
	public BasicWindowDialog(String title){
//		super(menubarPanel);
		buildDialog(title);
	}
	
	private void buildDialog(String title){
		this.getContentPane().setBackground(Color.CYAN);
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setSize(defaultWidth, defaultHeight+buttonSize);
		
		JLabel titleLabel = new JLabel(title);
		
//		JPanel menubarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
		menubarPanel.setPreferredSize(new Dimension(defaultWidth-buttonSize, buttonSize));
		menubarPanel.setBackground(Color.RED);
		
//		JPanel menubarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		menubarContainer.setPreferredSize(new Dimension(defaultWidth, buttonSize));
		menubarContainer.setBackground(Color.green);
		
		menubarPanel.add(titleLabel);
		menubarContainer.add(menubarPanel);
		menubarContainer.add(closeButton);
		this.add(menubarContainer);

		createListeners(menubarPanel);
		
		container.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		this.add(container);
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					hideWindow();
				}
			}
		});
	}
	
	public void resizeWindow(int width, int height){
		this.setSize(width, height+buttonSize);
		container.setPreferredSize(new Dimension(width, height));
		menubarContainer.setPreferredSize(new Dimension(width, buttonSize));
		menubarPanel.setPreferredSize(new Dimension(width-buttonSize, buttonSize));
		this.revalidate();
		this.repaint();
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}
	
	
}
