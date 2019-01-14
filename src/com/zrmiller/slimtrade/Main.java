package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.util.Locale;
import java.util.ResourceBundle;

import com.zrmiller.slimtrade.debug.Debugger;

public class Main {	
	
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		Locale swede = new Locale("sv", "SE");
//		Locale.setDefault(swede);
		
		debug.log("Default Localization : " + Locale.getDefault());
		
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		FrameManager frameManager = new FrameManager();
		System.out.println("закос_ютубер");
		
		//Locale Testing
		
	
		//EXAMPLE AUDIO CODE
//		File ping = new File("audio/ping.wav");
//		try {
//			AudioManager.playSound(ping);
//		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
//			e.printStackTrace();
//		}
		
	}
	
	

}
