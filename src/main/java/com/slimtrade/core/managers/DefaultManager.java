package main.java.com.slimtrade.core.managers;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.audio.Sound;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;

public class DefaultManager {

	public DefaultManager(){
		Main.saveManager.putDefaultInt(0, "stashOverlay", "x");
		Main.saveManager.putDefaultInt(100, "stashOverlay", "y");
		Main.saveManager.putDefaultInt(600, "stashOverlay", "width");
		Main.saveManager.putDefaultInt(600, "stashOverlay", "height");
		
//		Main.saveManager.putStringDefault("I'm busy, want me to message you back in a little bit?", "macros", "in", "preset", "callback", "left");
//		Main.saveManager.putStringDefault("one sec", "macros", "in", "preset", "wait", "left");
//		Main.saveManager.putStringDefault("one minute", "macros", "in", "preset", "wait", "right");
		Main.saveManager.putStringDefault("thanks", "macros", "in", "preset", "thank", "left");
		Main.saveManager.putStringDefault("", "macros", "in", "preset", "thank", "right");

		// Thank Button
//		Main.saveManager.putDefaultBool(true, "macroButtons", "thankButton", "enabled");
//		Main.saveManager.putDefaultBool(false, "macroButtons", "thankButton", "secondaryEnabled");
//		Main.saveManager.putStringDefault("thanks", "macroButtons", "thankButton", "textLMB");
//		Main.saveManager.putStringDefault("", "macroButtons", "thankButton", "textRMB");

		// Overlay
		Main.saveManager.putDefaultInt(0, "overlayManager", "menubar", "x");
		Main.saveManager.putDefaultInt(TradeUtility.screenSize.height - MenubarDialog.HEIGHT, "overlayManager", "menubar", "y");
		Main.saveManager.putDefaultInt(1220, "overlayManager", "messageManager", "x");
		Main.saveManager.putDefaultInt(0, "overlayManager", "messageManager", "y");
		Main.saveManager.putStringDefault("Bottom Left", "overlayManager", "menubar", "buttonLocation");

		//Sound
		try{
			SoundComponent.BUTTON_CLICK.setSound(Sound.valueOf(Main.saveManager.getEnumValue(Sound.class, true, "audio", "buttonClick", "type")));
		}catch(NullPointerException e){
			SoundComponent.BUTTON_CLICK.setSound(Sound.CLICK1);
		}
		SoundComponent.BUTTON_CLICK.setVolume(TradeUtility.getAudioVolume(Main.saveManager.getDefaultInt(0, 100, 50, "audio", "buttonClick", "volume")));

		SoundComponent.INCOMING_MESSAGE.setSound(Sound.valueOf(Main.saveManager.getEnumValue(Sound.class, "audio", "incomingTrade", "type")));
		SoundComponent.INCOMING_MESSAGE.setVolume(TradeUtility.getAudioVolume(Main.saveManager.getDefaultInt(0, 100, 50, "audio", "incomingTrade", "volume")));
		
		SoundComponent.OUTGOING_MESSAGE.setSound(Sound.valueOf(Main.saveManager.getEnumValue(Sound.class, "audio", "outgoingTrade", "type")));
		SoundComponent.OUTGOING_MESSAGE.setVolume(TradeUtility.getAudioVolume(Main.saveManager.getDefaultInt(0, 100, 50, "audio", "outgoingTrade", "volume")));
		
		SoundComponent.SCANNER_MESSAGE.setSound(Sound.valueOf(Main.saveManager.getEnumValue(Sound.class, "audio", "scannerMessage", "type")));
		SoundComponent.SCANNER_MESSAGE.setVolume(TradeUtility.getAudioVolume(Main.saveManager.getDefaultInt(0, 100, 50, "audio", "scannerMessage", "volume")));

		Main.saveManager.saveToDisk();
	}

}
