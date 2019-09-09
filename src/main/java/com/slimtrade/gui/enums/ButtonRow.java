package com.slimtrade.gui.enums;

public enum ButtonRow {

	TOP("Top Row"),
	BOTTOM("Bottom Row"),
	;
	
	private String name;
	
	ButtonRow(String name){
		this.name = name;
	}
	
	public String toString(){
		return name;
	}
}
