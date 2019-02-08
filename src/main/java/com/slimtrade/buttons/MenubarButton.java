package main.java.com.slimtrade.buttons;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenubarButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	public static int width = 140;
	public static int height= 22;
	
	public MenubarButton(){
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(false);
	}
	
	public MenubarButton(String text){
		this.setText(text);
		this.setPreferredSize(new Dimension(width, height));
		this.setFocusable(false);
	}

}
