package com.slimtrade.enums;

public enum ColorTheme {
	
	LIGHT_THEME("Light"),
	DARK_THEME("Dark"),
	ALT1("alt1"),
	ALT2("alt2");

	
	private String name;
	
	private ColorTheme(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
