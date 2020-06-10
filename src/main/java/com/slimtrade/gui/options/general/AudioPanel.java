package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.elements.SoundElement;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import java.awt.*;

public class AudioPanel extends ContainerPanel implements ISaveable, IColorable {

    private static final long serialVersionUID = 1L;

    //	private final AudioRow buttonRow = new AudioRow("UI Button");
    private final AudioRow incomingRow = new AudioRow("Incoming Trade");
    private final AudioRow outgoingRow = new AudioRow("Outgoing Trade");
    private final AudioRow scannerRow = new AudioRow("Chat Scanner");
    private final AudioRow joinedRow = new AudioRow("Player Joined Area");

    public AudioPanel() {

        for (Sound s : Sound.values()) {
            incomingRow.addSound(s);
            outgoingRow.addSound(s);
            scannerRow.addSound(s);
            joinedRow.addSound(s);
        }

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

//		container.add(buttonRow, gc);
//		gc.gridy++;
        container.add(incomingRow, gc);
        gc.gridy++;
        container.add(outgoingRow, gc);
        gc.gridy++;
        container.add(scannerRow, gc);
        gc.gridy++;
        container.add(joinedRow, gc);
        gc.gridy++;
    }

    public void save() {
        App.saveManager.saveFile.incomingMessageSound = new SoundElement(incomingRow.getSound(), incomingRow.getVolume());
        App.saveManager.saveFile.outgoingMessageSound = new SoundElement(outgoingRow.getSound(), outgoingRow.getVolume());
        App.saveManager.saveFile.scannerMessageSound = new SoundElement(scannerRow.getSound(), scannerRow.getVolume());
        App.saveManager.saveFile.playerJoinedSound = new SoundElement(joinedRow.getSound(), joinedRow.getVolume());
//        App.saveManager.saveFile.buttonSound = new SoundElement(buttonRow.getSound(), buttonRow.getVolume());
    }

    public void load() {
        incomingRow.setValue(App.saveManager.saveFile.incomingMessageSound.sound, App.saveManager.saveFile.incomingMessageSound.volume);
        outgoingRow.setValue(App.saveManager.saveFile.outgoingMessageSound.sound, App.saveManager.saveFile.outgoingMessageSound.volume);
        scannerRow.setValue(App.saveManager.saveFile.scannerMessageSound.sound, App.saveManager.saveFile.scannerMessageSound.volume);
        joinedRow.setValue(App.saveManager.saveFile.playerJoinedSound.sound, App.saveManager.saveFile.playerJoinedSound.volume);
//        buttonRow.setValue(App.saveManager.saveFile.buttonSound.sound, App.saveManager.saveFile.buttonSound.volume);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
    }

}
