package com.zrmiller.slimtrade.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.Overlay;

public class IconButton extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 1;
	public static int width;
	public static int height;
	public static int borderThickness = 1;
	public Color bgColor;
	public Color bgColor_hover;
	public Color borderColor;
	public Color borderColor_hover;
	private Border border = BorderFactory.createEmptyBorder(1, 1, 1, 1);
//	private Border border = BorderFactory.createEmptyBorder();
	private Border border_hover = BorderFactory.createLineBorder(Color.BLACK);

	public IconButton(String imgPath, int width, int height){
		IconButton.width = width;
		IconButton.height = height;
		buildButton();
		this.setCustomIcon(imgPath);
	}
	
	public IconButton(String imgPath){
		buildButton();
		this.setCustomIcon(imgPath);
	}
	
	private void buildButton(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(this.bgColor);
		this.setBorder(border);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				setColorHover();
			}
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				setColorDefault();
			}
		});
	}
	
	public void updateBorderPreset(){
		for(Object l : this.listenerList.getListenerList()){
			this.removeMouseListener((MouseListener) l);
		}
		this.buildButton();
	}
	
	public void setColorDefault(){
		this.setBackground(this.bgColor);
		this.setBorder(this.border);
	}
	
	private void setColorHover(){
		this.setBackground(this.bgColor_hover);
		this.setBorder(this.border_hover);
	}
	
	public void setBorderPresets(Border border, Border border_hover){
		this.border = border;
		this.border_hover = border_hover;
		setColorDefault();
	}
	
	public void setBorderPresetDefault(Border border){
		this.border = border;
		setColorDefault();
	}
	
	public void setBorderPresetHover(Border border){
		this.border_hover = border;
		setColorDefault();
	}
	
	public void setCustomIcon(String imgPath){
		JLabel closeIcon = new JLabel();
		double imgWidth = width*imgScaling;
		double imgHeight = height*imgScaling;
		closeIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource(imgPath)).getImage().getScaledInstance((int)(imgWidth), (int)(imgHeight), Image.SCALE_SMOOTH)));
		closeIcon.setBounds(0, 0, (int)imgWidth, (int)imgHeight);
		closeIcon.setPreferredSize(new Dimension((int)imgWidth, (int)imgHeight));
		this.add(closeIcon);
	}
	
}
