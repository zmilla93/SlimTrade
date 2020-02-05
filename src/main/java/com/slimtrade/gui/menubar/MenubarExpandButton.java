package com.slimtrade.gui.menubar;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.slimtrade.App;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.buttons.IconButton;

public class MenubarExpandButton extends BasicDialog{

	private static final long serialVersionUID = 1L;
	private int size = MenubarButton.HEIGHT;
//	private boolean visible;
	
	public MenubarExpandButton(){
		this.setBounds(0, TradeUtility.screenSize.height-size, size, size);
		this.getContentPane().setBackground(Color.RED);
		IconButton showMenubarButton = new IconButton("icons/menu1.png", size);
//		IconButton
		showMenubarButton.setBackground(Color.GREEN);
		this.add(showMenubarButton);
		
		showMenubarButton.addMouseListener(new AdvancedMouseAdapter() {
		    public void click(MouseEvent e) {
		    	FrameManager.menubar.setShow(true);
		    	FrameManager.menubarToggle.setShow(false);
		    }
		});
		
		showMenubarButton.addMouseListener(new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				FrameManager.menubar.setShow(true);
		    	FrameManager.menubarToggle.setShow(false);
			}
		});
	}
	
	public void updateLocation(){
		int x = App.saveManager.saveFile.menubarX;
		int y = App.saveManager.saveFile.menubarY;
		MenubarButtonLocation loc = App.saveManager.saveFile.menubarButtonLocation == null ? MenubarButtonLocation.NW : App.saveManager.saveFile.menubarButtonLocation;
		
		int modX = 0;
		int modY = 0;
		switch(loc){
		case NW:
			modX = MenubarButtonLocation.NW.getModX();
			modY = MenubarButtonLocation.NW.getModY();
			break;
		case NE:
			modX = MenubarButtonLocation.NE.getModX();
			modY = MenubarButtonLocation.NE.getModY();
			break;
		case SW:
			modX = MenubarButtonLocation.SW.getModX();
			modY = MenubarButtonLocation.SW.getModY();
			break;
		case SE:
			modX = MenubarButtonLocation.SE.getModX();
			modY = MenubarButtonLocation.SE.getModY();
			break;
		}
		this.setLocation(x+modX, y+modY);
		this.revalidate();
		this.repaint();
	}
	
}
