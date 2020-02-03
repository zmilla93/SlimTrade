package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.SaveConstants.Audio;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.menubar.MenubarDialog;

public class DefaultManager {

	public DefaultManager(){
		App.saveManager.putDefaultInt(0, "stashOverlay", "x");
		App.saveManager.putDefaultInt(100, "stashOverlay", "y");
		App.saveManager.putDefaultInt(600, "stashOverlay", "width");
		App.saveManager.putDefaultInt(600, "stashOverlay", "height");
		
//		App.saveManager.putStringDefault("I'm busy, want me to message you back in a little bit?", "macros", "in", "preset", "callback", "left");
//		App.saveManager.putStringDefault("one sec", "macros", "in", "preset", "wait", "left");
//		App.saveManager.putStringDefault("one minute", "macros", "in", "preset", "wait", "right");
		App.saveManager.putStringDefault("thanks", "macros", "in", "preset", "thank", "left");
		App.saveManager.putStringDefault("", "macros", "in", "preset", "thank", "right");

		// Thank Button
//		App.saveManager.putDefaultBool(true, "macroButtons", "thankButton", "enabled");
//		App.saveManager.putDefaultBool(false, "macroButtons", "thankButton", "secondaryEnabled");
//		App.saveManager.putStringDefault("thanks", "macroButtons", "thankButton", "textLMB");
//		App.saveManager.putStringDefault("", "macroButtons", "thankButton", "textRMB");

		// Overlay
		App.saveManager.putDefaultInt(0, "overlayManager", "menubar", "x");
		App.saveManager.putDefaultInt(TradeUtility.screenSize.height - MenubarDialog.HEIGHT, "overlayManager", "menubar", "y");
		App.saveManager.putDefaultInt(1220, "overlayManager", "messageManager", "x");
		App.saveManager.putDefaultInt(0, "overlayManager", "messageManager", "y");
		App.saveManager.putStringDefault("Bottom Left", "overlayManager", "menubar", "buttonLocation");

		//Sound
		//TODO : Revisit if more click sounds are added
//		SoundComponent.BUTTON_CLICK.setSound(Sound.valueOf(App.saveManager.getEnumValue(Sound.class, Audio.UIClick.TYPE)));
//		SoundComponent.BUTTON_CLICK.setSound(Sound.valueOf(App.saveManager.getEnumValue(Sound.class, true, Audio.UIClick.TYPE)));
		SoundComponent.BUTTON_CLICK.setSound(Sound.CLICK1);
		SoundComponent.BUTTON_CLICK.setVolume(TradeUtility.getAudioVolume(App.saveManager.getDefaultInt(0, 100, 50, Audio.UIClick.VOLUME)));

		SoundComponent.INCOMING_MESSAGE.setSound(Sound.valueOf(App.saveManager.getEnumValue(Sound.class, Audio.IncomingTrade.TYPE)));
		SoundComponent.INCOMING_MESSAGE.setVolume(TradeUtility.getAudioVolume(App.saveManager.getDefaultInt(0, 100, 50, Audio.IncomingTrade.VOLUME)));
		
		SoundComponent.OUTGOING_MESSAGE.setSound(Sound.valueOf(App.saveManager.getEnumValue(Sound.class, Audio.OutgoingTrade.TYPE)));
		SoundComponent.OUTGOING_MESSAGE.setVolume(TradeUtility.getAudioVolume(App.saveManager.getDefaultInt(0, 100, 50, Audio.OutgoingTrade.VOLUME)));
		
		SoundComponent.SCANNER_MESSAGE.setSound(Sound.valueOf(App.saveManager.getEnumValue(Sound.class, Audio.ChatScanner.TYPE)));
		SoundComponent.SCANNER_MESSAGE.setVolume(TradeUtility.getAudioVolume(App.saveManager.getDefaultInt(0, 100, 50, Audio.ChatScanner.VOLUME)));

		App.saveManager.saveToDisk();
	}

}
