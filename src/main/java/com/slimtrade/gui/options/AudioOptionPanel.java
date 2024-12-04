package com.slimtrade.gui.options;

import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.AudioComboBox;
import com.slimtrade.gui.components.ComponentPanel;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.options.audio.AudioRowControls;
import com.slimtrade.gui.options.audio.AudioThresholdPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class AudioOptionPanel extends AbstractOptionPanel implements ISavable {

    // Custom Audio Controls
    private final JButton openFolderButton = new JButton("Open Audio Folder");
    private final JButton refreshButton = new JButton("Refresh");
    private final JLabel customAudioLabel = new JLabel();

    // Sound Settings Controls
    private final AudioRowControls incomingTradeControls;
    private final AudioRowControls outgoingTradeControls;
    private final AudioRowControls chatScannerControls;
    private final AudioRowControls kalguurControls;
    private final AudioRowControls playerJoinedAreaControls;
    private final AudioRowControls ignoredItemControls;
    private final AudioRowControls updateAlertControls;

    public AudioOptionPanel() {
        // Sound Setting Controls
        JPanel soundSettingsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        incomingTradeControls = new AudioRowControls("Incoming Trade", soundSettingsPanel, gc);
        outgoingTradeControls = new AudioRowControls("Outgoing Trade", soundSettingsPanel, gc);
        chatScannerControls = new AudioRowControls("Chat Scanner", soundSettingsPanel, gc);
        kalguurControls = new AudioRowControls("Kalguur Shipments", soundSettingsPanel, gc);
        playerJoinedAreaControls = new AudioRowControls("Player Joined Area", soundSettingsPanel, gc);
        ignoredItemControls = new AudioRowControls("Ignored Item", soundSettingsPanel, gc);
        updateAlertControls = new AudioRowControls("Update Alert", soundSettingsPanel, gc);

        // Build Panel
        addHeader("Custom Audio");
        addComponent(new JLabel("Add audio files to the audio folder, then refresh. Custom files will then be available in all audio dropdowns."));
        JLabel label = new StyledLabel("Only supports .wav files. Online file converters are available if you have different formats.").bold();
        addComponent(label);
        addComponent(new ComponentPanel(openFolderButton, refreshButton));
        addComponent(customAudioLabel);
        addVerticalStrut();

        addHeader("Sound Settings");
        addComponent(soundSettingsPanel);
        addVerticalStrut();

        addHeader("Price Thresholds");
        addComponent(new AudioThresholdPanel());

        // Finalize
        updateInfoLabel();
        addListeners();
    }

    private void addListeners() {
        openFolderButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getAudioDirectory()));
        refreshButton.addActionListener(e -> {
            AudioManager.rebuildSoundList();
            updateInfoLabel();
            AudioComboBox.refreshAllComboSoundLists();
        });
    }

    public void updateInfoLabel() {
        int count = AudioManager.getCustomSoundFileCount();
        if (count == 0) {
            customAudioLabel.setText("No custom files loaded.");
        } else if (count == 1) {
            customAudioLabel.setText("1 custom file loaded.");
        } else {
            customAudioLabel.setText(count + " custom files loaded.");
        }
    }

    @Override
    public void save() {
        AudioComboBox.purgeUnusedCombos();
        SaveManager.settingsSaveFile.data.incomingSound = incomingTradeControls.getData();
        SaveManager.settingsSaveFile.data.outgoingSound = outgoingTradeControls.getData();
        SaveManager.settingsSaveFile.data.chatScannerSound = chatScannerControls.getData();
        SaveManager.settingsSaveFile.data.kalguurSound = kalguurControls.getData();
        SaveManager.settingsSaveFile.data.playerJoinedAreaSound = playerJoinedAreaControls.getData();
        SaveManager.settingsSaveFile.data.itemIgnoredSound = ignoredItemControls.getData();
        SaveManager.settingsSaveFile.data.updateSound = updateAlertControls.getData();
    }

    @Override
    public void load() {
        AudioComboBox.purgeUnusedCombos();
        AudioComboBox.refreshAllComboSoundLists();
        incomingTradeControls.setData(SaveManager.settingsSaveFile.data.incomingSound);
        outgoingTradeControls.setData(SaveManager.settingsSaveFile.data.outgoingSound);
        chatScannerControls.setData(SaveManager.settingsSaveFile.data.chatScannerSound);
        kalguurControls.setData(SaveManager.settingsSaveFile.data.kalguurSound);
        playerJoinedAreaControls.setData(SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        ignoredItemControls.setData(SaveManager.settingsSaveFile.data.itemIgnoredSound);
        updateAlertControls.setData(SaveManager.settingsSaveFile.data.updateSound);
    }

}
