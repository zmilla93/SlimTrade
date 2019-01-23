package main.java.com.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BasicIcon extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 1;
	public static int width = 20;
	public static int height = 20;
	public Color bgColor;

	public BasicIcon(String imgPath, int width, int height){
		BasicIcon.width = width;
		BasicIcon.height = height;
		buildIcon();
		this.setCustomIcon(imgPath);
	}
	
	public BasicIcon(String imgPath){
		buildIcon();
		this.setCustomIcon(imgPath);
	}
	
	private void buildIcon(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(this.bgColor);
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
