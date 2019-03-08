package main.java.com.slimtrade.core.audio;

public class AudioManager {

	public static final int MIN_VOLUME = -30;
	public static final int MAX_VOLUME = 6;
	public static final int RANGE = Math.abs(MIN_VOLUME) + MAX_VOLUME;

	public AudioManager() {

	}

	public static void play(SoundComponent component) {
		if (component.getVolume() <= AudioManager.MIN_VOLUME) {
			return;
		}
		AudioRunner runner = new AudioRunner(component.getSound(), component.getVolume());
		Thread thread = new Thread(runner);
		thread.start();
	}

	public static void playRaw(Sound sound, float volume) {
		if (volume <= AudioManager.MIN_VOLUME) {
			return;
		}
		AudioRunner runner = new AudioRunner(sound, volume);
		Thread thread = new Thread(runner);
		thread.start();
	}

}
