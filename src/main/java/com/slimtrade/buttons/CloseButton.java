package main.java.com.slimtrade.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.ColorManager;

public class CloseButton extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 0.9;
	public static int width;
	public static int height;
	public static int borderThickness = 1;
	public static Color bgColor;
	public static Color bgColor_hover;
	public static Color borderColor;
	public static Color borderColor_hover;
	private Border border;
	private Border border_hover;

	
	public CloseButton(){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.RED);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				setCurrentBorder(border_hover);
			}
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				setCurrentBorder(border);
			}
		});
	}
	
	private void setCurrentBorder(Border border){
		this.setBorder(border);
	}
	
	public void setBorderPresets(Border border, Border border_hover){
		this.border = border;
		this.border_hover = border_hover;
		this.setCurrentBorder(border);
	}
	
	public void setCustomIcon(String path){
		JLabel closeIcon = new JLabel();
		double imgWidth = width*imgScaling;
		double imgHeight = height*imgScaling;
		closeIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance((int)(imgWidth), (int)(imgHeight), Image.SCALE_SMOOTH)));
		closeIcon.setBounds(0, 0, (int)imgWidth, (int)imgHeight);
		closeIcon.setPreferredSize(new Dimension((int)imgWidth, (int)imgHeight));
//		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(closeIcon);
	}
	
}
