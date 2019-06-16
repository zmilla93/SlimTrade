package main.java.com.slimtrade.enums;

public enum ColorThemeType {
	
	LIGHT_THEME("Light"),
	DARK_THEME("Dark");
	
	
	private String name;
	
	private ColorThemeType(String name){
		this.name = name;
	}
	
	public String toString(){
		return this.name;
	}
	
}
