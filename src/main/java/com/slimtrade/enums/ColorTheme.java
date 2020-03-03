package com.slimtrade.enums;

public enum ColorTheme {

	SOLARIZED_LIGHT("Solarized Light"),
	SOLARIZED_DARK("Solarized Dark"),
	STORMY("Stormy"),
	VAAL("Vaal"),
	MONOKAI("Monokai"),
//	MONOKAI_ALT_1("Monokai Alt"),
//	ALT2("alt2"),
	;

	
	private String name;
	
	private ColorTheme(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
