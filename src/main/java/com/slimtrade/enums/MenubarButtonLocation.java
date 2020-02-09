package com.slimtrade.enums;

import com.slimtrade.gui.menubar.MenubarButton;
import com.slimtrade.gui.menubar.MenubarDialog;

public enum MenubarButtonLocation {
    NW("Top Left"),
    NE("Top Right"),
	SW("Bottom Left"),
	SE("Bottom Right");
	
	String name;
	
	MenubarButtonLocation(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
