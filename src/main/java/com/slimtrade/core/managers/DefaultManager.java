package main.java.com.slimtrade.core.managers;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.audio.Sound;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;

public class DefaultManager {

	public DefaultManager(){
		Main.saveManager.putIntDefault(0, "stashOverlay", "x");
		Main.saveManager.putIntDefault(100, "stashOverlay", "y");
		Main.saveManager.putIntDefault(600, "stashOverlay", "width");
		Main.saveManager.putIntDefault(600, "stashOverlay", "height");
		
		Main.saveManager.putStringDefault("I'm busy, want me to message you back in a little bit?", "macros", "in", "preset", "callback", "left");
		Main.saveManager.putStringDefault("one sec", "macros", "in", "preset", "wait", "left");
		Main.saveManager.putStringDefault("one minute", "macros", "in", "preset", "wait", "right");
		Main.saveManager.putStringDefault("thanks", "macros", "in", "preset", "thank", "left");
		Main.saveManager.putStringDefault("", "macros", "in", "preset", "thank", "right");

		// Thank Button
		Main.saveManager.putBoolDefault(true, "macroButtons", "thankButton", "enabled");
		Main.saveManager.putBoolDefault(false, "macroButtons", "thankButton", "secondaryEnabled");
		Main.saveManager.putStringDefault("thanks", "macroButtons", "thankButton", "textLMB");
		Main.saveManager.putStringDefault("", "macroButtons", "thankButton", "textRMB");

		// Overlay
		Main.saveManager.putIntDefault(0, "overlayManager", "menubar", "x");
		Main.saveManager.putIntDefault(TradeUtility.screenSize.height - MenubarDialog.HEIGHT, "overlayManager", "menubar", "y");
		Main.saveManager.putIntDefault(1220, "overlayManager", "messageManager", "x");
		Main.saveManager.putIntDefault(0, "overlayManager", "messageManager", "y");

		Main.saveManager.putStringDefault("Bottom Left", "overlayManager", "menubar", "buttonLocation");

		//Sound
		Main.saveManager.putStringDefault(Sound.CLICK1.toString(), "options", "audio", "incomingTrade", "type");
		Main.saveManager.putIntDefault(50, "options", "audio", "incomingTrade", "volume");
		

		Sound s = Sound.PING1;
		float vol = 0;
		try{
			s = Sound.valueOf(Main.saveManager.getString("options", "audio", "incomingTrade", "type"));
			vol = TradeUtility.getAudioVolume(Main.saveManager.getInt("options", "audio", "incomingTrade", "volume"));
			
		}catch(IllegalArgumentException | NullPointerException e){
			System.out.println("Invalid sound, deleting...");
			Main.saveManager.deleteObject("options", "audio", "incomingTrade");
		}
		SoundComponent.INCOMING_MESSAGE.setSound(s);
		SoundComponent.INCOMING_MESSAGE.setVolume(vol);
		System.out.println(MessageType.values());

		Main.saveManager.saveToDisk();
	}
	
}
