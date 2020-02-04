package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.SoundElement;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

public class AudioPanel extends ContainerPanel implements ISaveable, ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	private final AudioRow buttonRow = new AudioRow("UI Button");
	private final AudioRow incomingRow = new AudioRow("Incoming Trade");
	private final AudioRow outgoingRow = new AudioRow("Outgoing Trade");
	private final AudioRow scannerRow = new AudioRow("Chat Scanner");

	public AudioPanel() {
		buttonRow.addSound(Sound.CLICK1);
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

		container.add(buttonRow, gc);
		gc.gridy++;
		container.add(incomingRow, gc);
		gc.gridy++;
		container.add(outgoingRow, gc);
		gc.gridy++;
		container.add(scannerRow, gc);
		gc.gridy++;
		load();
		
		this.updateColor();
		App.eventManager.addListener(this);
		
	}

	public void save() {
//        SoundElement incomingSound = new SoundElement(incomingRow.getSound(), incomingRow.getVolume());
        App.saveManager.saveFile.incomingMessageSound = new SoundElement(incomingRow.getSound(), incomingRow.getVolume());
        App.saveManager.saveFile.outgoingMessageSound = new SoundElement(outgoingRow.getSound(), outgoingRow.getVolume());
        App.saveManager.saveFile.scannerMessageSound = new SoundElement(scannerRow.getSound(), scannerRow.getVolume());
        App.saveManager.saveFile.buttonSound = new SoundElement(buttonRow.getSound(), buttonRow.getVolume());

//		App.saveManager.putObject(buttonRow.getSound().getName(), SaveConstants.Audio.UIClick.TYPE);
//		App.saveManager.putObject(buttonRow.getVolume(), SaveConstants.Audio.UIClick.VOLUME);
//		SoundComponentOLD.BUTTON_CLICK.setSound(buttonRow.getSound());
//		SoundComponentOLD.BUTTON_CLICK.setVolume(TradeUtility.getAudioVolume(buttonRow.getVolume()));
//
//
//		App.saveManager.putObject(incomingRow.getSound().getName(), SaveConstants.Audio.IncomingTrade.TYPE);
//		App.saveManager.putObject(incomingRow.getVolume(), SaveConstants.Audio.IncomingTrade.VOLUME);
//		SoundComponentOLD.INCOMING_MESSAGE.setSound(incomingRow.getSound());
//		SoundComponentOLD.INCOMING_MESSAGE.setVolume(TradeUtility.getAudioVolume(incomingRow.getVolume()));
//
//		App.saveManager.putObject(outgoingRow.getSound().getName(), SaveConstants.Audio.OutgoingTrade.TYPE);
//		App.saveManager.putObject(outgoingRow.getVolume(), SaveConstants.Audio.OutgoingTrade.VOLUME);
//		SoundComponentOLD.OUTGOING_MESSAGE.setSound(outgoingRow.getSound());
//		SoundComponentOLD.OUTGOING_MESSAGE.setVolume(TradeUtility.getAudioVolume(outgoingRow.getVolume()));
//
//		App.saveManager.putObject(scannerRow.getSound().getName(), SaveConstants.Audio.ChatScanner.TYPE);
//		App.saveManager.putObject(scannerRow.getVolume(), SaveConstants.Audio.ChatScanner.VOLUME);
//		SoundComponentOLD.SCANNER_MESSAGE.setSound(scannerRow.getSound());
//		SoundComponentOLD.SCANNER_MESSAGE.setVolume(TradeUtility.getAudioVolume(scannerRow.getVolume()));
	}

	public void load() {
        incomingRow.setValue(App.saveManager.saveFile.incomingMessageSound.sound, App.saveManager.saveFile.incomingMessageSound.volume);
        outgoingRow.setValue(App.saveManager.saveFile.outgoingMessageSound.sound, App.saveManager.saveFile.outgoingMessageSound.volume);
        scannerRow.setValue(App.saveManager.saveFile.scannerMessageSound.sound, App.saveManager.saveFile.scannerMessageSound.volume);
        buttonRow.setValue(App.saveManager.saveFile.buttonSound.sound, App.saveManager.saveFile.buttonSound.volume);
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
	}

}
