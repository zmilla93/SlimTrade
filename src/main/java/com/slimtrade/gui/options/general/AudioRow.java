package com.slimtrade.gui.options.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomSliderUI;
import com.slimtrade.gui.buttons.IconButton;

public class AudioRow extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 200;
	private final int HEIGHT = 25;
	private final int BUTTON_SIZE = HEIGHT - 5;
	private final int LABEL_WIDTH = 120;

//	JSlider slider = new JSlider();
	JSlider slider = new JSlider();
	CustomCombo<Sound> soundCombo = new CustomCombo<Sound>();
	JLabel label = new JLabel();

	public AudioRow(String title) {
        slider.setUI(new CustomSliderUI(slider));
		// Sample Button
		IconButton sampleButton = new IconButton("icons/play1.png", BUTTON_SIZE);

		// Label
		JPanel labelPanel = new JPanel(new GridBagLayout());
		label.setText(title);
		labelPanel.setPreferredSize(new Dimension(LABEL_WIDTH, HEIGHT));

		// Volume Slider
		Dimension sliderSize = slider.getPreferredSize();
		sliderSize.height = HEIGHT;
		slider.setMinimum(0);
		slider.setMaximum(100);
		slider.setMajorTickSpacing(25);
		slider.setMinorTickSpacing(5);
		slider.setSnapToTicks(true);
		slider.setFocusable(false);
		slider.setPaintTicks(true);

		// Opacity
		labelPanel.setOpaque(false);
		this.setOpaque(false);
		slider.setOpaque(false);

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

		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// System.out.println(slider.getValue());
				//
				// System.out.println(f-70);
				// System.out.println(getVolume());
				// float vol = TradeUtility.getAudioVolume(slider.getValue());
				// System.out.println(TradeUtility.getAudioPercent(vol));
			}
		});

		sampleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sound sound = (Sound) soundCombo.getSelectedItem();
				float volume = TradeUtility.getAudioVolume(slider.getValue());
				AudioManager.playRaw(sound, volume);
			}
		});
		
		this.updateColor();
		App.eventManager.addListener(this);
		
	}

	public void addSound(Sound sound) {
		soundCombo.addItem(sound);
	}

	public void setValue(Sound sound, int volume) {
		soundCombo.setSelectedItem(sound);
		// System.out.println(volume);
		slider.setValue(volume);
	}

	public int getVolume() {
		// int range=40;
		// float f = (float)
		// ((AudioManager.RANGE/100.0)*(float)(slider.getValue()));
		// return f-AudioManager.RANGE+AudioManager.MAX_VOLUME;
		return slider.getValue();
	}

	public Sound getSound() {
		Sound s = (Sound) soundCombo.getSelectedItem();
		return s;
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
		label.setForeground(ColorManager.TEXT);
	}

}
