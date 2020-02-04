package com.slimtrade.enums;

public enum ColorTheme {
	
	LIGHT_THEME("Light"),
	DARK_THEME("Dark");
	
	
	private String name;
	
	private ColorTheme(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
