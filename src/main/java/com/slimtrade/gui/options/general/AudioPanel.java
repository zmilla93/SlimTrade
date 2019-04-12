package main.java.com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.SaveConstants.Audio;
import main.java.com.slimtrade.core.audio.Sound;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.improved.ColorUpdateListener;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.options.ISaveable;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class AudioPanel extends ContainerPanel implements ISaveable, ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	private final AudioRow uiRow = new AudioRow("UI Button");
	private final AudioRow incomingRow = new AudioRow("Incoming Trade");
	private final AudioRow outgoingRow = new AudioRow("Outgoing Trade");
	private final AudioRow scannerRow = new AudioRow("Chat Scanner");

	public AudioPanel() {
		uiRow.addSound(Sound.CLICK1);
		incomingRow.addSound(Sound.PING1);
		incomingRow.addSound(Sound.PING2);
		outgoingRow.addSound(Sound.PING1);
		outgoingRow.addSound(Sound.PING2);
		scannerRow.addSound(Sound.PING1);
		scannerRow.addSound(Sound.PING2);
	
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		container.add(uiRow, gc);
		gc.gridy++;
		container.add(incomingRow, gc);
		gc.gridy++;
		container.add(outgoingRow, gc);
		gc.gridy++;
		container.add(scannerRow, gc);
		gc.gridy++;
		load();
		
		this.updateColor();
		Main.eventManager.addListener(this);
		
	}

	public void save() {
		Main.saveManager.putObject(uiRow.getSound().getName(), Audio.UIClick.TYPE);
		Main.saveManager.putObject(uiRow.getVolume(), Audio.UIClick.VOLUME);
		SoundComponent.BUTTON_CLICK.setSound(uiRow.getSound());
		SoundComponent.BUTTON_CLICK.setVolume(TradeUtility.getAudioVolume(uiRow.getVolume()));
		
		Main.saveManager.putObject(incomingRow.getSound().getName(), Audio.IncomingTrade.TYPE);
		Main.saveManager.putObject(incomingRow.getVolume(), Audio.IncomingTrade.VOLUME);
		SoundComponent.INCOMING_MESSAGE.setSound(incomingRow.getSound());
		SoundComponent.INCOMING_MESSAGE.setVolume(TradeUtility.getAudioVolume(incomingRow.getVolume()));
		
		Main.saveManager.putObject(outgoingRow.getSound().getName(), Audio.OutgoingTrade.TYPE);
		Main.saveManager.putObject(outgoingRow.getVolume(), Audio.OutgoingTrade.VOLUME);
		SoundComponent.OUTGOING_MESSAGE.setSound(outgoingRow.getSound());
		SoundComponent.OUTGOING_MESSAGE.setVolume(TradeUtility.getAudioVolume(outgoingRow.getVolume()));
		
		Main.saveManager.putObject(scannerRow.getSound().getName(), Audio.ChatScanner.TYPE);
		Main.saveManager.putObject(scannerRow.getVolume(), Audio.ChatScanner.VOLUME);
		SoundComponent.SCANNER_MESSAGE.setSound(scannerRow.getSound());
		SoundComponent.SCANNER_MESSAGE.setVolume(TradeUtility.getAudioVolume(scannerRow.getVolume()));

	}

	public void load() {
		uiRow.setValue(SoundComponent.BUTTON_CLICK.getSound(), TradeUtility.getAudioPercent(SoundComponent.BUTTON_CLICK.getVolume()));
		incomingRow.setValue(SoundComponent.INCOMING_MESSAGE.getSound(), TradeUtility.getAudioPercent(SoundComponent.INCOMING_MESSAGE.getVolume()));
		outgoingRow.setValue(SoundComponent.OUTGOING_MESSAGE.getSound(), TradeUtility.getAudioPercent(SoundComponent.OUTGOING_MESSAGE.getVolume()));
		scannerRow.setValue(SoundComponent.SCANNER_MESSAGE.getSound(), TradeUtility.getAudioPercent(SoundComponent.SCANNER_MESSAGE.getVolume()));
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
	}

}
