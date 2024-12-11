package github.zmilla93.gui.options.audio;

import github.zmilla93.core.audio.Sound;
import github.zmilla93.core.audio.SoundComponent;
import github.zmilla93.core.data.PriceThresholdData;
import github.zmilla93.core.enums.CurrencyType;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.enums.SliderRange;
import github.zmilla93.core.enums.SpinnerRange;
import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.*;

import javax.swing.*;
import java.awt.*;

public class AudioThresholdRowControls extends AddRemovePanel<PriceThresholdData> {

    private final JButton previewButton = new IconButton(DefaultIcon.PLAY);

    private final JSpinner quantitySpinner = new RangeSpinner(SpinnerRange.PRICE_THRESHOLD);
    private final JComboBox<CurrencyType> currencyTypeCombo = new LimitCombo<>();
    private final AudioComboBox soundCombo = new AudioComboBox();
    private final JSlider volumeSlider = new RangeSlider(SliderRange.AUDIO_VOLUME);
    private final JLabel volumeLabel = new JLabel();

    public AudioThresholdRowControls() {
        for (CurrencyType currency : CurrencyType.getCommonCurrencyTypes()) currencyTypeCombo.addItem(currency);
        currencyTypeCombo.setSelectedItem(CurrencyType.getCurrencyType("Chaos Orb"));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(deleteButton, gc);
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
        add(volumeLabel, gc);
        gc.gridx++;

        addListeners();
        updateVolumeLabel();
    }

    private void addListeners() {
        previewButton.addActionListener(e -> AudioManager.playSoundPercent((Sound) soundCombo.getSelectedItem(), volumeSlider.getValue()));
        volumeSlider.addChangeListener(e -> updateVolumeLabel());
    }

    private void updateVolumeLabel() {
        volumeLabel.setText(ZUtil.getVolumeText(volumeSlider.getValue()));
    }

    public void refreshCombo() {
        soundCombo.refreshSoundList();
    }

    @Override
    public void setData(PriceThresholdData data) {
        currencyTypeCombo.setSelectedItem(CurrencyType.getCurrencyType(data.currencyType.ID));
        quantitySpinner.setValue(data.quantity);
        soundCombo.setSelectedItem(data.soundComponent.sound);
        volumeSlider.setValue(data.soundComponent.volume);
    }

    @Override
    public PriceThresholdData getData() {
        CurrencyType currency = (CurrencyType) currencyTypeCombo.getSelectedItem();
        int quantity = (int) quantitySpinner.getValue();
        Sound sound = (Sound) soundCombo.getSelectedItem();
        int volume = volumeSlider.getValue();
        return new PriceThresholdData(currency, quantity, new SoundComponent(sound, volume));
    }

}
