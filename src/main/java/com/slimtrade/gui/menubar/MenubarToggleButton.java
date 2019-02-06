package main.java.com.slimtrade.gui.menubar;

import java.awt.Color;
import java.awt.event.MouseEvent;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class MenubarToggleButton extends BasicDialog{

	private static final long serialVersionUID = 1L;
	private int size = 20;
	private boolean visible;
	
	public MenubarToggleButton(){
		this.setBounds(0, TradeUtility.screenSize.height-size, size, size);
//		this.setBounds(0, 0, size, size);
		this.setBackground(Color.RED);
		IconButton showMenubarButton = new IconButton("/resources/icons/arrow_up_black.png", size);
		this.add(showMenubarButton);
		
		showMenubarButton.addMouseListener(new AdvancedMouseAdapter() {
		    public void click(MouseEvent e) {
		    	FrameManager.menubarToggle.hideDialog();;
		    	FrameManager.menubar.showDialog();;
		    }
		});
	}
	
	public void showDialog(){
		this.setVisible(true);
		this.visible = true;
	}
	
	public void hideDialog(){
		this.setVisible(false);
		this.visible = false;
	}
	
	public void refreshVisibility(){
		this.setVisible(visible);
	}
	
}
