package main.java.com.slimtrade.core.audio;

public enum Sound {
	
	PING("/resources/audio/ping.wav"),
	CLICK("/resources/audio/click.wav"),
	;
	
	private String path;
	
	Sound(String path){
		this.path = path;
	}
	
	public String getPath(){
		return path;
	}
	
}
