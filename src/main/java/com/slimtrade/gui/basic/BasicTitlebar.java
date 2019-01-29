package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.FrameManager;

public class BasicTitlebar extends JPanel{

	private static final long serialVersionUID = 1L;
	private int defaultHeight = 20;
	private int width = 0;
	private int height = 0;
	private int offsetX = 0;
	private int offsetY = 0;
	private String title = "";
	private JPanel parent;
	
	private JPanel panel;
	private JLabel label;
	private JButton button;
	private Color color = Color.LIGHT_GRAY;
	
	public BasicTitlebar(String title, JPanel parent, int width){
		this.parent = parent;
		this.width = width;
		this.height = defaultHeight;
		this.title = title;
		createTitlepanel();
	}
	
	public BasicTitlebar(String title, JPanel parent, int width, int height){
		this.parent = parent;
		this.width = width;
		this.height = height;
		this.title = title;
		createTitlepanel();
	}
	
	private void createTitlepanel(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(width, height));
		panel = new JPanel();
		this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.setPreferredSize(new Dimension(width-height, height));
		label = new JLabel(title);
		//label.setPreferredSize(new Dimension(panelWidth, height));
		button = new JButton();
		button.setPreferredSize(new Dimension(height, height));
		BasicPanel.setPanelSize(5, height);
		panel.add(new BasicPanel(5, height, color));
		panel.add(label);
		panel.setBackground(color);
		this.add(panel);
		this.add(button);
		
		button.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	FrameManager.hideAllFrames();
		    }
		});
		
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent e) {
		    	offsetX = e.getXOnScreen()-getPosX();
		    	offsetY = e.getYOnScreen()-getPosY();
		    }
		});
		
		panel.addMouseMotionListener(new java.awt.event.MouseAdapter() {
		    public void mouseDragged(java.awt.event.MouseEvent e) {
		    	moveBox(e.getXOnScreen()-offsetX, e.getYOnScreen()-offsetY);
		    }
		});
		
	}
	
	private int getPosX(){
		return parent.getX();
	}
	
	private int getPosY(){
		return parent.getY();
	}
	
	private void moveBox(int posX, int posY){
		parent.setLocation(posX, posY);
	}
	
}
