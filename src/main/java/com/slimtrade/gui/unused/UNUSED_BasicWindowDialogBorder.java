package main.java.com.slimtrade.gui.unused;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.ColorManager;
import main.java.com.slimtrade.gui.basic.BasicMovableDialog;
import main.java.com.slimtrade.gui.buttons.IconButton_REMOVE;

public class UNUSED_BasicWindowDialogBorder extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;

	private int defaultWidth = 400;
	private int defaultHeight = 800;
	public static int titlebarHeight = 20;
	private int titleOffset = 5;
	private int borderThickness = 1;
	
	public Container container = new JPanel();
	private JPanel titlebarContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	private JPanel titlebarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, titleOffset, 0));
	
	private JPanel borderLeft = new JPanel();
	private JPanel borderRight = new JPanel();
	private JPanel borderBottom = new JPanel();
	
	public IconButton_REMOVE closeButton = new IconButton_REMOVE("/resources/icons/close.png", titlebarHeight, titlebarHeight);
	
	
	
	//INTERNAL
	
	public UNUSED_BasicWindowDialogBorder(){
		super(false);
		buildDialog("");
	}
	
	public UNUSED_BasicWindowDialogBorder(String title){
		super(false);
		buildDialog(title);
	}
	
	//TODO : Add support for window borders
	private void buildDialog(String title){
		this.setFocusableWindowState(true);
		this.getContentPane().setBackground(Color.RED);
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setLayout(new BorderLayout());
		this.setSize(defaultWidth, defaultHeight+titlebarHeight);

		Container c = this.getContentPane();
		
		JLabel titleLabel = new JLabel(title);
		
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth-titlebarHeight, titlebarHeight));
		titlebarPanel.setBackground(ColorManager.GenericWindow.titlebarBG);
		
		titlebarContainer.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		titlebarContainer.setBackground(ColorManager.GenericWindow.closeButtonBG);
		
		titlebarPanel.add(titleLabel);
		titlebarContainer.add(titlebarPanel);
		titlebarContainer.add(closeButton);
		c.add(titlebarContainer, BorderLayout.PAGE_START);

		container.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		c.add(container, BorderLayout.CENTER);
		
		c.add(borderLeft, BorderLayout.LINE_START);
		c.add(borderRight, BorderLayout.LINE_END);
		c.add(borderBottom, BorderLayout.PAGE_END);
		
		Color color = Color.BLACK;
		
		borderLeft.setBackground(color);
		borderRight.setBackground(color);
		borderBottom.setBackground(color);
		
		createListeners(titlebarPanel);
		
		resizeWindow(defaultWidth, defaultHeight);
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					hideWindow();
				}
			}
		});
	}
	
	public void resizeWindow(int width, int height){
		this.setSize(width+(borderThickness*2), height+titlebarHeight+borderThickness);
		titlebarContainer.setPreferredSize(new Dimension(width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(width-titlebarHeight, titlebarHeight));
		container.setPreferredSize(new Dimension(width, height));
		borderLeft.setPreferredSize(new Dimension(borderThickness, height));
		borderRight.setPreferredSize(new Dimension(borderThickness, height));
		borderBottom.setPreferredSize(new Dimension(width, borderThickness));
		this.revalidate();
		this.repaint();
	}
	
	private void hideWindow(){
		this.setVisible(false);
	}
	
	
}
