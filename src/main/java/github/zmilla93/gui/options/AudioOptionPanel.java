package github.zmilla93.gui.options;

import github.zmilla93.core.managers.AudioManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.AudioComboBox;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.gui.options.audio.AudioRowControls;
import github.zmilla93.gui.options.audio.AudioThresholdPanel;
import github.zmilla93.modules.filemonitor.FileChangeEvent;
import github.zmilla93.modules.filemonitor.FileChangeListener;
import github.zmilla93.modules.filemonitor.FileMonitor;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class AudioOptionPanel extends AbstractOptionPanel implements ISavable, FileChangeListener {

    // Custom Audio Controls
    private final JButton openFolderButton = new JButton("Open Audio Folder");
    private final JLabel customFileCountLabel = new JLabel();

    // Sound Settings Controls
    private final AudioRowControls incomingTradeControls;
    private final AudioRowControls outgoingTradeControls;
    private final AudioRowControls chatScannerControls;
    //    private final AudioRowControls kalguurControls;
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
//        kalguurControls = new AudioRowControls("Kalguur Shipments", soundSettingsPanel, gc);
        playerJoinedAreaControls = new AudioRowControls("Player Joined Area", soundSettingsPanel, gc);
        ignoredItemControls = new AudioRowControls("Ignored Item", soundSettingsPanel, gc);
        updateAlertControls = new AudioRowControls("Update Alert", soundSettingsPanel, gc);

        // Build Panel
        addHeader("Custom Audio");
        addComponent(new JLabel("Add custom audio files to the audio folder."));
        JLabel label = new StyledLabel("Only supports .wav files. Online file converters are available if you have different formats.").bold();
        addComponent(label);
        addComponent(new ComponentPanel(openFolderButton, customFileCountLabel));
        addVerticalStrut();

        addHeader("Sound Settings");
        addComponent(soundSettingsPanel);
        addVerticalStrut();

        addHeader("Price Thresholds");
        addComponent(new AudioThresholdPanel());

        // Finalize
        updateInfoLabel();
        addListeners();
        FileMonitor.startNewMonitor(SaveManager.getAudioDirectory(), this);
    }

    private void addListeners() {
        openFolderButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getAudioDirectory().toString()));
    }

    private void updateSoundCombos() {
        SwingUtilities.invokeLater(() -> {
            AudioManager.rebuildSoundList();
            updateInfoLabel();
            AudioComboBox.refreshAllComboSoundLists();
        });
    }

    public void updateInfoLabel() {
        int count = AudioManager.getCustomSoundFileCount();
        if (count == 0) {
            customFileCountLabel.setText("No custom files loaded.");
        } else if (count == 1) {
            customFileCountLabel.setText("1 custom file loaded.");
        } else {
            customFileCountLabel.setText(count + " custom files loaded.");
        }
    }

    @Override
    public void save() {
        AudioComboBox.purgeUnusedCombos();
        SaveManager.settingsSaveFile.data.incomingSound = incomingTradeControls.getData();
        SaveManager.settingsSaveFile.data.outgoingSound = outgoingTradeControls.getData();
        SaveManager.settingsSaveFile.data.chatScannerSound = chatScannerControls.getData();
//        SaveManager.settingsSaveFile.data.kalguurSound = kalguurControls.getData();
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
//        kalguurControls.setData(SaveManager.settingsSaveFile.data.kalguurSound);
        playerJoinedAreaControls.setData(SaveManager.settingsSaveFile.data.playerJoinedAreaSound);
        ignoredItemControls.setData(SaveManager.settingsSaveFile.data.itemIgnoredSound);
        updateAlertControls.setData(SaveManager.settingsSaveFile.data.updateSound);
    }

    @Override
    public void onFileChanged(FileChangeEvent event) {
        updateSoundCombos();
    }

}
