package main.java.com.slimtrade.gui.options.audio;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.audio.Sound;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.options.Saveable;

public class AudioPanel extends ContentPanel implements Saveable {

	private static final long serialVersionUID = 1L;
	private final int BUFFER_HEIGHT = 10;

	private final AudioRow buttonRow = new AudioRow("UI Button");
	private final AudioRow incomingRow = new AudioRow("Incoming Trade");
	private final AudioRow outgoingRow = new AudioRow("Outgoing Trade");
	private final AudioRow scannerRow = new AudioRow("Chat Scanner");

	public AudioPanel() {
		super(false);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		this.addRow(buttonRow, gc);
		this.addRow(incomingRow, gc);
		this.addRow(outgoingRow, gc);
		this.addRow(scannerRow, gc);
		this.autoResize();
		load();
	}

	public void save() {
		Main.saveManager.putObject(incomingRow.getSound().getName(), "options", "audio", "incomingTrade", "type");
		Main.saveManager.putObject(incomingRow.getVolume(), "options", "audio", "incomingTrade", "volume");
		SoundComponent.INCOMING_MESSAGE.setSound(incomingRow.getSound());
		SoundComponent.INCOMING_MESSAGE.setVolume(TradeUtility.getAudioVolume(incomingRow.getVolume()));
	}

	//TODO : This doesn't work...
	public void load() {
		incomingRow.setValue(SoundComponent.INCOMING_MESSAGE.getSound(), TradeUtility.getAudioPercent(SoundComponent.INCOMING_MESSAGE.getVolume()));
	}

}
