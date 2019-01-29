package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.ColorManager;
import main.java.com.slimtrade.gui.buttons.IconButton_REMOVE;

public class BasicWindowDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;

	public static int defaultWidth = 400;
	public static int defaultHeight = 800;
	public static int titlebarHeight = 20;
	private static int titleOffset = 5;
	
	public Container container = new JPanel();
	public JPanel titlebarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	public JPanel titlebarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
	public IconButton_REMOVE closeButton = new IconButton_REMOVE("/resources/icons/close.png", titlebarHeight, titlebarHeight);
	
	public BasicWindowDialog(){
		super(false);
		buildDialog("");
	}
	
	public BasicWindowDialog(String title){
		super(false);
		buildDialog(title);
	}
	
	//TODO : Add support for window borders
	//TODO : Center titlebar text
	private void buildDialog(String title){
		this.setFocusableWindowState(true);
		this.getContentPane().setBackground(Color.RED);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setSize(defaultWidth, defaultHeight+titlebarHeight);
		
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth-titlebarHeight, titlebarHeight));
		titlebarPanel.setBackground(ColorManager.GenericWindow.titlebarBG);
		
		titlebarContainer.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		titlebarContainer.setBackground(ColorManager.GenericWindow.closeButtonBG);
		
		JLabel titleLabel = new JLabel(title);
		titlebarPanel.add(titleLabel);
		
		titlebarContainer.add(titlebarPanel);
		titlebarContainer.add(closeButton);
		this.getContentPane().add(titlebarContainer);

		container.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		this.getContentPane().add(container);
		createListeners(titlebarPanel);
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
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
