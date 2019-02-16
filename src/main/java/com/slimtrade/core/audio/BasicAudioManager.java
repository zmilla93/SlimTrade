package main.java.com.slimtrade.core.audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class BasicAudioManager extends Thread {
	
	URL pingURL = this.getClass().getResource("/resources/ping.wav");
	String type = "";
	
	public BasicAudioManager() {
		
	}
	
	public void play(String t){
		type = t;
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		System.out.println("Playing audio...");
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			AudioInputStream ping = AudioSystem.getAudioInputStream(pingURL);
			clip.open(ping);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
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
