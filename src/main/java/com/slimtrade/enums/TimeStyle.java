package main.java.com.slimtrade.enums;

public enum TimeStyle {

	H12("12 Hour", ""),
	H24("24 Hour", ""),
	;
	
	private final String name;
	private final String format;
	
	TimeStyle(String name, String format){
		this.name = name;
		this.format = name;
	}

	public String toString() {
		return name;
	}

	public String getFormat() {
		return format;
	}
	
}
