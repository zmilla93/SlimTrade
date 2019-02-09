package main.java.com.slimtrade.enums;

public enum SoundType {

	PING1("Ping #1"), PING2("Ping #2"), CLICK1("Click #1"), CLICK2("Click #2");
	
	private String name;
	
	SoundType(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}
