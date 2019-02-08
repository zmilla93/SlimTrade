package main.java.com.slimtrade.core;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineEvent.Type;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioManager {

	/*
	 * Much of this code is taken directly from StackOverflow
	 * 		https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
	 * 
	 * Volume
	 * 		https://stackoverflow.com/questions/953598/audio-volume-control-increase-or-decrease-in-java
	 */
	
	//TODO : Clean this class up
	public static void playSound(File clipFile) throws IOException, UnsupportedAudioFileException, LineUnavailableException, InterruptedException {
		new Thread(new Runnable(){
				public void run(){
					class AudioListener implements LineListener {
					    private boolean done = false;
					    @Override public synchronized void update(LineEvent event) {
					      Type eventType = event.getType();
					      if (eventType == Type.STOP || eventType == Type.CLOSE) {
					        done = true;
					        notifyAll();
					      }
					    }
					    public synchronized void waitUntilDone() throws InterruptedException {
					      while (!done) { wait(); }
					    }
					  }
					  AudioListener listener = new AudioListener();
					  AudioInputStream audioInputStream = null;
					try {
						audioInputStream = AudioSystem.getAudioInputStream(clipFile);
					} catch (UnsupportedAudioFileException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					  try {
					    Clip clip = null;
						try {
							clip = AudioSystem.getClip();
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    clip.addLineListener(listener);
					    clip.open(audioInputStream);
					    FloatControl volume= (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); 
					    volume.setValue(-20f);
					    try {
					      clip.start();
					      listener.waitUntilDone();
					    } catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {
					      clip.close();
					    }
					  } catch (LineUnavailableException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
					    try {
							audioInputStream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					  }
				}
			}
		).start();

	}
	
}
