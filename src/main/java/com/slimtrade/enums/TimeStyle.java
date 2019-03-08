package main.java.com.slimtrade.enums;

public enum TimeStyle {

	H12("12 Hour", "h:mm a"),
	H24("24 Hour", "HH:mm"),
	;
	
	private final String name;
	private final String format;
	
	TimeStyle(String name, String format){
		this.name = name;
		this.format = format;
	}

	public String toString() {
		return this.name;
	}

	public String getFormat() {
		return this.format;
	}
	
}
