package com.slimtrade.enums;

public enum DateStyle {

	DDMM("Day/Month", "dd/MM"),
	DDMMYY("Day/Month/Year", "dd/MM/YY"),
	MMDD("Month/Day", "MM/dd"),
	MMDDYY("Month/Day/Year", "MM/dd/YY"),
	;
	
	private final String name;
	private final String format;
	
	DateStyle(String name, String format){
		this.name = name;
		this.format = format;
	}
	
	public String toString(){
		return this.name;
	}
	
	public String getFormat(){
		return this.format;
	}
	
}
