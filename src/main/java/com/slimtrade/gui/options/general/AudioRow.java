package com.slimtrade.gui.options.general;

import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.basic.CustomSlider;
import com.slimtrade.gui.basic.CustomSliderUI;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.DefaultIcons;

import javax.swing.*;
import java.awt.*;

public class AudioRow extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 200;
	private final int HEIGHT = 25;
	private final int BUTTON_SIZE = HEIGHT - 5;
	private final int LABEL_WIDTH = 120;

	private JPanel labelPanel;
	private JLabel label = new CustomLabel();

	private JSlider slider = new CustomSlider();
	private CustomCombo<Sound> soundCombo = new CustomCombo<Sound>();



	public AudioRow(String title) {

		// Sample Button
		IconButton sampleButton = new IconButton(DefaultIcons.PLAY, BUTTON_SIZE);

		// Label
		labelPanel = new JPanel(new GridBagLayout());
		label.setText(title);
		labelPanel.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));

		// Layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		labelPanel.add(label);
		this.add(sampleButton, gc);
		gc.gridx++;
		this.add(labelPanel, gc);
		gc.gridx++;
		this.add(slider, gc);
		gc.gridx++;
		this.add(soundCombo, gc);

		sampleButton.addActionListener(e -> {
            Sound sound = (Sound) soundCombo.getSelectedItem();
            float volume = TradeUtility.getAudioVolume(slider.getValue());
            AudioManager.playRaw(sound, volume);
        });
	}

	public void addSound(Sound sound) {
		soundCombo.addItem(sound);
	}

	public void setValue(Sound sound, int volume) {
		soundCombo.setSelectedItem(sound);
		slider.setValue(volume);
	}

	public int getVolume() {
		return slider.getValue();
	}

	public Sound getSound() {
		Sound s = (Sound) soundCombo.getSelectedItem();
		return s;
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
		labelPanel.setBackground(ColorManager.BACKGROUND);
	}

}
