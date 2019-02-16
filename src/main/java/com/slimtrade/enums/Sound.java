package main.java.com.slimtrade.enums;

public enum Sound {

	PING_1("/resources/audio/ping.wav"),
	PING_2(""),
	CLICK_1(""),
	CLICK_2(""),
	;
	
	String path;
	
	Sound(String path){
		this.path = path;
	}
}
