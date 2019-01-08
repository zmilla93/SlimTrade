package com.zrmiller.slimtrade;

import java.awt.AWTException;

import com.zrmiller.slimtrade.dialog.BasicMovableDialog;

public class Main {	
	
	public static void main(String[] args) {
		//Creates Robot
		
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		FrameManager frameManager = new FrameManager();
		

//		Overlay overlay = new Overlay();
		
		
		//EXAMPLE AUDIO CODE
//		File ping = new File("audio/ping.wav");
//		try {
//			AudioManager.playSound(ping);
//		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	

}
