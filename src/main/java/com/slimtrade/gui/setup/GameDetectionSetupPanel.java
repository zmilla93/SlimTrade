package com.slimtrade.gui.setup;

import com.slimtrade.core.jna.NativeWindow;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.gui.components.ComponentPanel;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.options.HeaderPanel;

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

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatically (Recommended)");
    private final JRadioButton monitorRadioButton = new JRadioButton("Use a Monitor (Windowed Fullscreen Only)");
    private final JRadioButton regionRadioButton = new JRadioButton("Manually Mark Screen Region");

    // Automatic
    private static final String automaticTestFail = "No Path of Exile window found! Make sure the game is open.";
    private static final String automaticTestSuccess = "Game window detected!";
    private final JButton automaticTestButton = new JButton("Detect");
    private final JLabel automaticTestLabel = new JLabel("Verify game detection is working.");

    public GameDetectionSetupPanel(JButton nextButton) {
        super(nextButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(regionRadioButton);

        AbstractOptionPanel optionPanel = new AbstractOptionPanel(false);
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
        automaticTestButton.addActionListener(e -> NativeWindow.findPathOfExileWindow(window -> {
            if (window == null) automaticTestLabel.setText(automaticTestFail);
            else automaticTestLabel.setText(automaticTestSuccess);
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
