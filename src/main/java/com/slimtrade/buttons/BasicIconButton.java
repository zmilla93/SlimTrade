package main.java.com.slimtrade.buttons;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class BasicIconButton extends JPanel{

	private static final long serialVersionUID = 1L;
	
	//Static
	public static double imgScaling = 0.9;
	public static int width = 20;
	public static int height = 20;
	public static int borderThickness = 1;
	
	public Color bgColor;
	public Color bgColor_hover;
	public Color borderColor;
	public Color borderColor_hover;
	
	private Border border = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	private Border border_hover = BorderFactory.createLineBorder(Color.BLACK);
	
	private int curWidth;
	private int curHeight;

	public BasicIconButton(String imgPath, int w, int h){
//		BasicIconButton.width = width;
//		BasicIconButton.height = height;
		curWidth = w;
		curHeight = h;
		buildButton(w, h);
		this.setCustomIcon(imgPath);
	}
	
	public BasicIconButton(String imgPath){
		curWidth = width;
		curHeight = height;
		buildButton(width, height);
		this.setCustomIcon(imgPath);
	}
	
	private void buildButton(int w, int h){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(w, h));
		this.setBackground(this.bgColor);
		this.setBorder(border);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent e) {
				applyColorHover();
			}
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				applyColorDefault();
			}
		});		
		
	}
	
	public void updateColorPresets(){
		for(Object l : this.listenerList.getListenerList()){
			this.removeMouseListener((MouseListener) l);
		}
		this.buildButton(curWidth, curHeight);
	}
	
	public void setColorPresets(Color colorDefault, Color colorHover){
		this.bgColor = colorDefault;
		this.bgColor_hover = colorHover;
	}
	
	public void applyColorDefault(){
		this.setBackground(this.bgColor);
		this.setBorder(this.border);
	}
	
	private void applyColorHover(){
		this.setBackground(this.bgColor_hover);
		this.setBorder(this.border_hover);
	}
	
	public void setBorderPresets(Border border, Border border_hover){
		this.border = border;
		this.border_hover = border_hover;
	}
	
	public void setBorderPresetDefault(Border border){
		this.border = border;
	}
	
	public void setBorderPresetHover(Border border){
		this.border_hover = border;
	}
	
	public void setCustomIcon(String imgPath){
		JLabel closeIcon = new JLabel();
		double imgWidth = curWidth*imgScaling;
		double imgHeight = curHeight*imgScaling;
		closeIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource(imgPath)).getImage().getScaledInstance((int)(imgWidth), (int)(imgHeight), Image.SCALE_SMOOTH)));
		closeIcon.setBounds(0, 0, (int)imgWidth, (int)imgHeight);
		closeIcon.setPreferredSize(new Dimension((int)imgWidth, (int)imgHeight));
		closeIcon.setHorizontalAlignment(SwingConstants.CENTER);
		closeIcon.setVerticalAlignment(SwingConstants.CENTER);
		this.add(closeIcon, BorderLayout.CENTER);
	}
	
}
