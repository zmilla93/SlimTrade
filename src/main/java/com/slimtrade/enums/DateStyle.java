package main.java.com.slimtrade.enums;

public enum DateStyle {

	DDMM("Day/Month", ""),
	DDMMYY("Day/Month/Year", ""),
	MMDD("Month/Day", ""),
	MMDDYY("Month/Day/Year", ""),
	;
	
	String name;
	String format;
	
	DateStyle(String name, String format){
		this.name = name;
		this.format = format;
	}
	
	public String toString(){
		return this.name;
	}
	
}
