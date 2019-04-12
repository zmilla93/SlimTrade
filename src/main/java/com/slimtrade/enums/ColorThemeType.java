package main.java.com.slimtrade.enums;

public enum ColorThemeType {

	DARK_THEME("Dark"),
	LIGHT_THEME("Light");
	
	private String name;
	
	private ColorThemeType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
