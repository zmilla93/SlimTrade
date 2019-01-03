package com.zrmiller.slimtrade;

import java.awt.AWTException;

import javax.swing.JFrame;

public class Main {	
	
	public static void main(String[] args) {
		//Creates Robot
		
		JFrame p = new JFrame("WOOO");
		p.setSize(500,500);
		p.setVisible(true);
		
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
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
