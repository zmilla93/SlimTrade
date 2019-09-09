package com.slimtrade.core.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioRunner implements Runnable {

	private Sound sound;
	private float volume;
//	URL pingURL = this.getClass().getResource("/resources/ping.wav");
	
	public AudioRunner(Sound sound, float volume){
		this.sound = sound;
		this.volume = volume;
	}
	
	public void run() {
		System.out.println("Playing audio...");
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream stream = AudioSystem.getAudioInputStream(this.getClass().getResource(sound.getPath()));
			clip.open(stream);
			FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			volumeControl.setValue(volume);
			System.out.println("GAIN CONTROL : " + volumeControl.getValue()  + ":::" +  volumeControl.getMinimum() + " - " + volumeControl.getMaximum());
			
			
//			volume.setValue(6f);
			
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			System.out.println(e.getMessage());
			System.err.println("[ERROR] Issue retriving audio file");
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
