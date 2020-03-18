package com.slimtrade.core.audio;

import java.io.IOException;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioRunner implements Runnable {

	private Sound sound;
	private float volume;
//	URL pingURL = this.getClass().getResource("ping.wav");
	
	public AudioRunner(Sound sound, float volume){
		this.sound = sound;
		this.volume = volume;
	}
	
	public void run() {
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(Objects.requireNonNull(this.getClass().getClassLoader().getResource(sound.getPath())));
			clip.open(stream);
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(volume);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			System.out.println("[SlimTrade] Issue retrieving audio file : " + e.getMessage());
		}
		clip.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		clip.close();
	}

}
