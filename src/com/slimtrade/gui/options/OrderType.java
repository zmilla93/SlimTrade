package com.slimtrade.gui.options;

public enum OrderType {
	NEW_FIRST("Newest First", 0),
	NEW_LAST("Newest Last", 0),
	;
	
	String name;
	int i;
	
	OrderType(String name, int i){
		this.name = name;
		this.i = 0;
	}
	
	public String toString(){
		return this.name;
	}
	
}
