package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.buttons.IconButton_REMOVE;

public class BasicResizableWindow extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;

	private static int defaultWidth = 400;
	private static int defaultHeight = 800;
	public static int titlebarHeight = 20;
	private static int titleOffset = 5;
	
	public Container container = new JPanel();
	private JPanel titlebarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	private JPanel titlebarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
	
	public IconButton_REMOVE closeButton = new IconButton_REMOVE("/resources/icons/close.png", titlebarHeight, titlebarHeight);
	
	
	
	//INTERNAL
	
	public BasicResizableWindow(){
		super(false);
		buildDialog("");
	}
	
	public BasicResizableWindow(String title){
		super(false);
		buildDialog(title);
	}
	
	//TODO : Add support for window borders
	private void buildDialog(String title){
		this.setFocusableWindowState(true);
		this.getContentPane().setBackground(Color.RED);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setSize(defaultWidth, defaultHeight+titlebarHeight);
		this.createListeners(titlebarPanel);
		
//		Border border = BorderFactory.createLineBorder(Color.BLUE, 3);
//		this.getRootPane().setBorder(border);
//		moverPanel = menubarPanel;
		
		JLabel titleLabel = new JLabel(title);
		
//		JPanel menubarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth-titlebarHeight, titlebarHeight));
		titlebarPanel.setBackground(ColorManager.GenericWindow.titlebarBG);
		
//		JPanel menubarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlebarContainer.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		titlebarContainer.setBackground(ColorManager.GenericWindow.closeButtonBG);
		
		titlebarPanel.add(titleLabel);
		titlebarContainer.add(titlebarPanel);
		titlebarContainer.add(closeButton);
		this.add(titlebarContainer);

		createListeners(titlebarPanel);
		
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
		this.setSize(width, height+titlebarHeight);
		container.setPreferredSize(new Dimension(width, height));
		titlebarContainer.setPreferredSize(new Dimension(width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(width-titlebarHeight, titlebarHeight));
		this.revalidate();
		this.repaint();
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}
	
	
}
