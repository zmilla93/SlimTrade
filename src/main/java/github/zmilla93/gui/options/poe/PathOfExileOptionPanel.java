package github.zmilla93.gui.options.poe;

import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.GameDetectionMethod;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.components.MonitorPicker;
import github.zmilla93.gui.components.poe.POEFolderPicker;
import github.zmilla93.gui.components.poe.POEInstallFolderExplanationPanel;
import github.zmilla93.gui.components.poe.detection.GameDetectionPanel;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PathOfExileOptionPanel extends AbstractOptionPanel {

    // Shared
    private final JComboBox<GameDetectionMethod> gameDetectionMethodCombo = new JComboBox<>();
    private final GameDetectionPanel detectionPanel = new GameDetectionPanel();
    private final MonitorPicker monitorPicker = new MonitorPicker();
    private final HotkeyButton chatHotkeyButton = new HotkeyButton();
    private final JButton explanationButton = new JButton("Install Folder Info");
    JPanel explanationPanel = new POEInstallFolderExplanationPanel(true, false);

    // Game Specific
    private final JCheckBox poe1UsingStashFoldersCheckbox = new JCheckBox("Using Stash Folders");
    private final JCheckBox poe2UsingStashFoldersCheckbox = new JCheckBox("Using Stash Folders");
    private final POEFolderPicker poe1FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_1);
    private final POEFolderPicker poe2FolderPicker = new POEFolderPicker(Game.PATH_OF_EXILE_2);
    private final AbstractOptionPanel poe1OptionPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel poe2OptionPanel = new AbstractOptionPanel(false, false);

    public PathOfExileOptionPanel() {
        gameDetectionMethodCombo.addItem(GameDetectionMethod.AUTOMATIC);
        gameDetectionMethodCombo.addItem(GameDetectionMethod.MONITOR);

        /// Shared
        addHeader("Path of Exile 1 & 2");
        addComponent(new ComponentPanel(new JLabel("Window Detection"), gameDetectionMethodCombo));
        addComponent(detectionPanel);
        addComponent(monitorPicker);
        addComponent(new ComponentPanel(new JLabel("Open Chat Hotkey"), chatHotkeyButton));
        addVerticalStrut();

        /// Path of Exile 1
        poe1OptionPanel.addComponent(poe1UsingStashFoldersCheckbox);
        addHeader(Game.PATH_OF_EXILE_1.getExplicitName());
        addComponent(poe1FolderPicker);
        addFullWidthComponent(poe1OptionPanel);
        addVerticalStrut();

        /// Path of Exile 2
        poe2OptionPanel.addComponent(poe2UsingStashFoldersCheckbox);
        addHeader(Game.PATH_OF_EXILE_2.toString());
        addComponent(poe2FolderPicker);
        addFullWidthComponent(poe2OptionPanel);
        addVerticalStrut();

        addComponent(explanationButton);
        addComponent(explanationPanel);
        addListeners();
    }

    private void addListeners() {
        gameDetectionMethodCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        explanationButton.addActionListener(e -> explanationPanel.setVisible(!explanationPanel.isVisible()));
        poe1FolderPicker.notInstalledCheckbox.addActionListener(e -> poe1OptionPanel.setVisible(!poe1FolderPicker.notInstalledCheckbox.isSelected()));
        poe2FolderPicker.notInstalledCheckbox.addActionListener(e -> poe2OptionPanel.setVisible(!poe2FolderPicker.notInstalledCheckbox.isSelected()));
    }

}
