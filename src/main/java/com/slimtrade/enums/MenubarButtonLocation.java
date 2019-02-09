package main.java.com.slimtrade.enums;

import main.java.com.slimtrade.gui.menubar.MenubarButton;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;

public enum MenubarButtonLocation {

	NW("Top Left", 0, 0),
	NE("Top Right", MenubarButton.WIDTH-MenubarButton.HEIGHT, 0),
	SW("Bottom Left", 0, MenubarDialog.HEIGHT-MenubarButton.HEIGHT),
	SE("Bottom Right", MenubarButton.WIDTH-MenubarButton.HEIGHT, MenubarDialog.HEIGHT-MenubarButton.HEIGHT);
	
	String displayText;
	int modX;
	int modY;
	
	MenubarButtonLocation(String text, int modX, int modY){
		this.displayText = text;
		this.modX = modX;
		this.modY = modY;
	}
	
	public String getText(){
		return displayText;
	}
	
	public int getModX(){
		return modX;
	}
	
	public int getModY(){
		return modY;
	}
	
}
