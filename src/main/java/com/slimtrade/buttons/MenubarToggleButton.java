package main.java.com.slimtrade.buttons;

import java.awt.Color;

import javax.swing.JButton;

import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.TradeUtility;
import main.java.com.slimtrade.dialog.BasicDialog;

public class MenubarToggleButton extends BasicDialog{

	private static final long serialVersionUID = 1L;
	private int size = 20;
	
	public MenubarToggleButton(){
		this.setBounds(0, TradeUtility.screenSize.height-size, size, size);
		this.setBackground(Color.RED);
		JButton showMenubarButton = new JButton();
		this.add(showMenubarButton);
		
		showMenubarButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	FrameManager.menubarToggle.setVisible(false);
		    	FrameManager.menubar.setVisible(true);
		    }
		});
	}
	
}
