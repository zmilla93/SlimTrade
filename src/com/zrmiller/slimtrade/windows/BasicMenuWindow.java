package com.zrmiller.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.ColorManager;
import com.zrmiller.slimtrade.buttons.BasicIconButton;
import com.zrmiller.slimtrade.panels.BasicPanel;

public class BasicMenuWindow extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	//TITLEBAR
	private JPanel titlebar;
	private JPanel titlebarPanel;
	private JLabel titlebarLabel;
	public static int titlebarHeight = 20;
	private String title = "";
	private BasicIconButton closeButton;
	//CONTENT PANEL
	public JPanel container;
	private int containerWidth;
	private int containerHeight;
	//THIS
	private int minimumWidth = 100;
	private int minimumHeight = titlebarHeight*2;
	private JPanel visibilityPanel;
	//MOVING
	private int offsetX = 0;
	private int offsetY = 0;
	//RESIZING
	private int snapSize = 1;
	
	//TEMP
	//TODO : move/remove these
	private Color defaultTitlebarColor = Color.LIGHT_GRAY;
	private Color clear = new Color(1.0f,1.0f,1.0f,0.0f);
	//
	
	public BasicMenuWindow(int containerWidth, int containerHeight){
		this.titlebarHeight = 0;
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		buildMenu();
	}
	
	public BasicMenuWindow(String title, int containerWidth, int containerHeight){
		this.setBackground(Color.RED);
		this.title = title;
		this.containerWidth = containerWidth;
		this.containerHeight = containerHeight;
		buildMenu();
	}
	
	private void buildMenu(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.setPreferredSize(new Dimension(containerWidth, containerHeight+titlebarHeight));
		this.setMinimumSize(new Dimension(minimumWidth, minimumHeight));
		this.setBounds(0, 0, containerWidth, containerHeight+titlebarHeight);
		this.setBackground(clear);
		buildTitlebar();
		this.add(titlebar);
		container = new JPanel();
		container.setPreferredSize(new Dimension(containerWidth, containerHeight));
		container.setBackground(Color.GRAY);
		this.add(container);
		visibilityPanel = this;
	}
	
	private void buildTitlebar(){
		titlebar = new JPanel();
		titlebar.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlebar.setPreferredSize(new Dimension(containerWidth, titlebarHeight));
		titlebarPanel = new JPanel();
		titlebarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		titlebarPanel.setPreferredSize(new Dimension(containerWidth-titlebarHeight, titlebarHeight));
		titlebarLabel = new JLabel(title);
//		BasicButton.width = this.titlebarHeight;
//		BasicButton.height = this.titlebarHeight;
		closeButton = new BasicIconButton("/close.png", this.titlebarHeight, this.titlebarHeight);
		closeButton.setPreferredSize(new Dimension(titlebarHeight, titlebarHeight));
		titlebarPanel.add(new BasicPanel(5, titlebarHeight, ColorManager.CLEAR));
		titlebarPanel.add(titlebarLabel);
		titlebarPanel.setBackground(defaultTitlebarColor);
		titlebar.add(titlebarPanel);
		titlebar.add(closeButton);
		
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	visibilityPanel.setVisible(false);
		    }
		});
		
		titlebarPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	offsetX = e.getXOnScreen()-getPosX();
		    	offsetY = e.getYOnScreen()-getPosY();
		    }
		});
		
		titlebarPanel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	moveBox(e.getXOnScreen()-offsetX, e.getYOnScreen()-offsetY);
		    	onMenubarRelease();
		    }
		});
	}
	
	private void onMenubarRelease(){
		
	}
	
	public void setSnapSize(int size){
		this.snapSize = size;
	}
	
	public int getTitlebarHeight(){
		return this.titlebar.getHeight();
	}
	
	public void resizeWindow(int width, int height){
		if(width<this.getMinimumSize().getWidth() || height < this.getMinimumSize().getHeight()){
			return;
		}
//		int w = width > 0 ? width : container.getWidth();
//		int h = height > 0 ? height : container.getHeight();
		int w = width;
		int h = height;
		while((double)(w%snapSize) != 0){
			w--;
		}
		while(h%snapSize != 0){
			h--;
		}
		Dimension size = new Dimension(w, h);
//		this.setSize(size);
		this.setSize(new Dimension(w, h+titlebar.getHeight()));
		this.setPreferredSize(new Dimension(w, h+titlebar.getHeight()));
		titlebar.setPreferredSize(new Dimension(w, titlebar.getHeight()));
		titlebarPanel.setPreferredSize(new Dimension(w-titlebar.getHeight(), titlebar.getHeight()));
//		closeButton.setPreferredSize(new Dimension(titlebar.getHeight(), titlebar.getHeight()));
		container.setPreferredSize(new Dimension(w, h));
		this.setVisible(false);
		this.setVisible(true);
	}
	
	private int getPosX(){
		return this.getX();
	}
	
	private int getPosY(){
		return this.getY();
	}
	
	private void moveBox(int posX, int posY){
		this.setLocation(posX, posY);
	}
	
	public BasicIconButton getCloseButton(){
		return this.closeButton;
	}
	
	
}
