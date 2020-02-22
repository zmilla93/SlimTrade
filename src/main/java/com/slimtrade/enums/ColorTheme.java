package com.slimtrade.enums;

public enum ColorTheme {

	SOLARIZED_LIGHT("Solarized Light"),
	DARK_THEME("Dark"),
	MONOKAI("Monokai"),
	ALT2("alt2");

	
	private String name;
	
	private ColorTheme(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
