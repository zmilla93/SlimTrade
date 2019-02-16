package main.java.com.slimtrade.core.audio;

public class AudioManager {

	public AudioManager() {
		
	}
	
	public static void play(Sound sound){
		AudioRunner runner = new AudioRunner(sound);
		Thread thread = new Thread(runner);
		thread.start();
	}
	
}
