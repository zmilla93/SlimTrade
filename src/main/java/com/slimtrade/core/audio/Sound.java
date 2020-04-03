package com.slimtrade.core.audio;

public enum Sound {
	
	PING1("Ping 1", "audio/ping1.wav"),
	PING2("Ping 2", "audio/ping2.wav"),
	BLIP1("Blip 1", "audio/blip1.wav"),
	BLIP2("Blip 2", "audio/blip2.wav"),
//	BLIP2("Blip 2", "audio/clicknew.wav"),
//	CLICK1("Click 1", "audio/click3.wav"),
	;
	
	private String name;
	private String path;
	
	Sound(String name, String path){
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name.toUpperCase().replaceAll("\\s+", "");
	}

	public String getPath() {
		return path;
	}

	public String toString(){
		return name;
	}
	
}
