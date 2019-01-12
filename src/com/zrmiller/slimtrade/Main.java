package com.zrmiller.slimtrade;

import java.awt.AWTException;

import javax.swing.JDialog;

import com.zrmiller.slimtrade.debug.Debugger;

public class Main {	
	
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		FrameManager frameManager = new FrameManager();		
		
		//EXAMPLE AUDIO CODE
//		File ping = new File("audio/ping.wav");
//		try {
//			AudioManager.playSound(ping);
//		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	

}
