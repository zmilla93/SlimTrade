package com.slimtrade.gui.setup;

import com.slimtrade.core.jna.NativeWindow;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.gui.components.ComponentPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.options.HeaderPanel;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final HeaderPanel dynamicHeader;
    private static final String automaticHeader = "Test Automatic Detection";
    private static final String monitorHeader = "Monitor Selection";
    private static final String regionHeader = "Screen Region Selection";

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatically");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select Monitor (Windowed Fullscreen Only)");
    private final JRadioButton regionRadioButton = new JRadioButton("Create Screen Region");

    // Automatic
    private static final String automaticTestFail = "No game window found. Make sure Path of Exile 1 or 2 is running.";
    private static final String automaticTestSuccess = "Game window detected!";
    private final JButton automaticTestButton = new JButton("Detect");
    private final JLabel automaticTestLabel = new JLabel("Verify game detection is working.");

    public GameDetectionSetupPanel(JButton nextButton) {
        super(nextButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(regionRadioButton);

        AbstractOptionPanel optionPanel = new AbstractOptionPanel(false, false);
        contentPanel.add(optionPanel, BorderLayout.CENTER);

        optionPanel.addHeader("Game Window Detection");
        optionPanel.addComponent(new JLabel("How should SlimTrade detect where the Path of Exile game window is?"));
        optionPanel.addVerticalStrutSmall();
        optionPanel.addComponent(automaticRadioButton);
        optionPanel.addComponent(monitorRadioButton);
        optionPanel.addComponent(regionRadioButton);
        optionPanel.addVerticalStrut();
        dynamicHeader = optionPanel.addHeader("Dynamic Method Header");
        // FIXME : Switch to CardLayoutPanel

        // Automatic Panel
        AbstractOptionPanel automaticPanel = new AbstractOptionPanel(false, false);
        automaticPanel.addComponent(new ComponentPanel(automaticTestButton, automaticTestLabel));

        optionPanel.addComponent(automaticPanel);

        for (Enumeration<AbstractButton> buttonIterator = buttonGroup.getElements(); buttonIterator.hasMoreElements(); )
            addRadioButtonListener(buttonIterator.nextElement());
        if (Platform.current == Platform.WINDOWS) {
            setHeaderText(automaticHeader);
            automaticRadioButton.setSelected(true);
        } else {
            setHeaderText(monitorHeader);
            monitorRadioButton.setSelected(true);
        }

        addListeners();
    }

    private void addListeners() {
        // FIXME : Should create confirm/deny labels
        automaticTestButton.addActionListener(e -> NativeWindow.findPathOfExileWindow(window -> {
            if (window == null) {
                automaticTestLabel.setForeground(ThemeManager.getCurrentExtensions().deny);
                automaticTestLabel.setText(automaticTestFail);
            } else {
                automaticTestLabel.setForeground(ThemeManager.getCurrentExtensions().approve);
                automaticTestLabel.setText(automaticTestSuccess);
            }
        }));
    }

    private void setHeaderText(String text) {
        assert (SwingUtilities.isEventDispatchThread());
        dynamicHeader.label.setText(text);
    }

    private void updateHeaderTextUsingSelectingButton(Object button) {
        if (button.equals(automaticRadioButton)) {
            setHeaderText(automaticHeader);
        } else if (button.equals(monitorRadioButton)) {
            setHeaderText(monitorHeader);
        } else if (button.equals(regionRadioButton)) {
            setHeaderText(regionHeader);
        }
    }

    private void addRadioButtonListener(AbstractButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateHeaderTextUsingSelectingButton(e.getSource());
            }
        });
    }

    @Override
    public boolean isSetupValid() {
        // FIXME: this
//        return SaveManager.settingsSaveFile.data.gameDetectionMethod != GameDetectionMethod.UNSET;
        return true;
    }

}
