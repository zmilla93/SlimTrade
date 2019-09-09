package com.slimtrade.gui.enums;

public enum ExpandDirection {

	DOWN("Downwards"), UP("Upwards");
	
	private final String name;
	
	ExpandDirection(String name){
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}
