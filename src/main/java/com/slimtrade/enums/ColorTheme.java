package com.slimtrade.enums;

public enum ColorTheme {

	SOLARIZED_LIGHT("Solarized Light"),
	SOLARIZED_DARK("Solarized Dark"),
	STORMY("Stormy"),
//	VAAL("Vaal"),
	MONOKAI("Monokai"),
	;

	
	private String name;
	
	private ColorTheme(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
