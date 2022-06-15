package com.slimtrade.gui.options.audio;

import com.slimtrade.App;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.enums.CommonCurrency;
import com.slimtrade.core.enums.SliderRange;
import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.*;

import javax.swing.*;
import java.awt.*;

public class AudioThresholdRow extends AddRemovePanel {

    private final JSpinner quantitySpinner = new RangeSpinner(SpinnerRange.PRICE_THRESHOLD);
    private final JComboBox<CommonCurrency> currencyTypeCombo = new LimitCombo<>();
    private final JComboBox<Sound> soundCombo = new JComboBox<>();
    private final JSlider volumeSlider = new RangeSlider(SliderRange.AUDIO_VOLUME);
    private final JButton previewButton = new IconButton("/icons/default/playx64.png");
    private final JButton removeButton = new IconButton("/icons/default/closex64.png");

    public AudioThresholdRow(AddRemoveContainer parent) {
        super(parent);
        refreshCombo();
        for (CommonCurrency currency : CommonCurrency.values()) currencyTypeCombo.addItem(currency);

        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(removeButton, gc);
        gc.gridx++;
        add(quantitySpinner, gc);
        gc.gridx++;
        add(currencyTypeCombo, gc);
        gc.gridx++;
        add(soundCombo, gc);
        gc.gridx++;
        add(previewButton, gc);
        gc.gridx++;
        add(volumeSlider, gc);
        gc.gridx++;

        addListeners();
    }

    private void addListeners() {
        previewButton.addActionListener(e -> App.audioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue()));
        removeButton.addActionListener(e -> removeFromParent());
    }

    private void refreshCombo() {
        soundCombo.removeAllItems();
        for (Sound sound : App.audioManager.getSoundFiles()) {
            soundCombo.addItem(sound);
        }
    }

    public void setData(PriceThresholdData data) {
        currencyTypeCombo.setSelectedItem(data.currencyType);
        quantitySpinner.setValue(data.quantity);
        soundCombo.setSelectedItem(data.soundComponent.sound);
        volumeSlider.setValue(data.soundComponent.volume);
    }

    public PriceThresholdData getData() {
        CommonCurrency currency = (CommonCurrency) currencyTypeCombo.getSelectedItem();
        int quantity = (int) quantitySpinner.getValue();
        Sound sound = (Sound) soundCombo.getSelectedItem();
        int volume = volumeSlider.getValue();
        return new PriceThresholdData(currency, quantity, new SoundComponent(sound, volume));
    }

}
