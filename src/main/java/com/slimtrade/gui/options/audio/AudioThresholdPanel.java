package com.slimtrade.gui.options.audio;

import com.slimtrade.core.data.PriceThresholdData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AudioThresholdPanel extends JPanel implements ISavable {

    private final JButton newThresholdButton = new JButton("New Price Threshold");
    private final AddRemoveContainer<AudioThresholdRowControls> container = new AddRemoveContainer<>();

    public AudioThresholdPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;

        add(new JLabel("Incoming trades will play the sound of the highest threshold they are greater than or equal to."), gc);
        gc.gridy++;
        add(newThresholdButton, gc);
        gc.gridy++;
        add(container, gc);
        addListeners();
    }

    private void addListeners() {
        newThresholdButton.addActionListener(e -> container.add(new AudioThresholdRowControls(container)));
    }

    public void refreshCombos() {
        for (Component c : container.getComponents()) {
            if (c instanceof AudioThresholdRowControls) {
                ((AudioThresholdRowControls) c).refreshCombo();
            }
        }
        load();
    }

    @Override
    public void save() {
        ArrayList<PriceThresholdData> priceThresholds = new ArrayList<>();
        for (AudioThresholdRowControls row : container.getComponentsTyped()) {
            priceThresholds.add(row.getData());
        }
        SaveManager.settingsSaveFile.data.priceThresholds = priceThresholds;
        SaveManager.settingsSaveFile.data.buildThresholdMap();
    }

    @Override
    public void load() {
        container.removeAll();
        for (PriceThresholdData data : SaveManager.settingsSaveFile.data.priceThresholds) {
            AudioThresholdRowControls row = new AudioThresholdRowControls(container);
            row.setData(data);
            container.add(row);
        }
        SaveManager.settingsSaveFile.data.buildThresholdMap();
    }
}
