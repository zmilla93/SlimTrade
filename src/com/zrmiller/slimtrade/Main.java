package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {	
	
	public static void main(String[] args) {
		//Creates Robot
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		Overlay overlay = new Overlay();
		
		//EXAMPLE AUDIO CODE
//		File ping = new File("audio/ping.wav");
//		try {
//			AudioManager.playSound(ping);
//		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	

}
