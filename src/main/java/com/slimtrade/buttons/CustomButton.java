package main.java.com.slimtrade.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.ColorManager;
import main.java.com.slimtrade.datatypes.ButtonState;

public class CustomButton extends JPanel{

	private static final long serialVersionUID = 1L;
	public ButtonState state = ButtonState.INACTIVE;
	private int width = 100;
	private int height = 20;

	public CustomButton(String text){
		buildButton(text);
	}
	
	public CustomButton(String text, int width, int height){
		this.width = width;
		this.height = height;
		buildButton(text);
	}
	
	private void buildButton(String text){
		JLabel buttonName = new JLabel(text);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(width, height));
//		this.setMaximumSize(new Dimension(width, height));
		this.add(buttonName, BorderLayout.CENTER);
		buttonName.setHorizontalAlignment(JLabel.CENTER);
		updateColor();
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			updateColor(0);
		}});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseExited(java.awt.event.MouseEvent evt) {
			updateColor();
		}});
	}
	
	public void updateColor(int ... i){
		if(i.length>0){
			this.setBackground(ColorManager.GenericWindow.buttonBG_hover);
			this.setBorder(ColorManager.GenericWindow.buttonBorder_hover);
		}else{
			this.setBackground(ColorManager.GenericWindow.buttonBG);
			this.setBorder(ColorManager.GenericWindow.buttonBorder);
		}
		this.revalidate();
		this.repaint();
	}
	
	public void setState(ButtonState state){
		this.state = state;
		updateColor();
	}
	

	
}
