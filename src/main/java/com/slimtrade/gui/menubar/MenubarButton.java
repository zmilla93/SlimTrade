package main.java.com.slimtrade.gui.menubar;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MenubarButton extends JButton{
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 140;
	public static int HEIGHT= 22;
	
	public MenubarButton(){
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(false);
	}
	
	public MenubarButton(String text){
		this.setText(text);
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setFocusable(false);
	}

}
