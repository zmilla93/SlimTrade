package com.slimtrade.enums;

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
