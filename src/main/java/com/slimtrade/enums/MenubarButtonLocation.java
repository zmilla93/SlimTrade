package com.slimtrade.enums;

import com.slimtrade.gui.menubar.MenubarButton;
import com.slimtrade.gui.menubar.MenubarDialog;

public enum MenubarButtonLocation {
    NW("Top Left", 0, 0),
    NE("Top Right", MenubarButton.WIDTH-MenubarButton.HEIGHT, 0),
	SW("Bottom Left", 0, MenubarDialog.HEIGHT-MenubarButton.HEIGHT),
	SE("Bottom Right", MenubarButton.WIDTH-MenubarButton.HEIGHT, MenubarDialog.HEIGHT-MenubarButton.HEIGHT),
	;
	
	String name;
	int modX;
	int modY;
	
	MenubarButtonLocation(String name, int modX, int modY){
		this.name = name;
		this.modX = modX;
		this.modY = modY;
	}
	
	public String toString(){
		return this.name;
	}
	
	public int getModX(){
		return modX;
	}
	
	public int getModY(){
		return modY;
	}
	
}
