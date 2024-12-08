package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.Platform;
import com.slimtrade.gui.options.AbstractOptionPanel;
import com.slimtrade.gui.options.HeaderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private static final String automaticHeader = "Test Automatic Detection";
    private static final String monitorHeader = "Monitor Selection";
    private static final String regionHeader = "Screen Region Selection";

    private final JRadioButton automaticButton = new JRadioButton("Automatically (Recommended)");
    private final JRadioButton monitorButton = new JRadioButton("Use a Monitor (Windowed Fullscreen Only)");
    private final JRadioButton regionButton = new JRadioButton("Manually Mark Screen Region");
    private final HeaderPanel methodHeader;

    public GameDetectionSetupPanel(JButton nextButton) {
        super(nextButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticButton);
        buttonGroup.add(monitorButton);
        buttonGroup.add(regionButton);

        AbstractOptionPanel optionPanel = new AbstractOptionPanel(false);
        contentPanel.add(optionPanel, BorderLayout.CENTER);


        optionPanel.addHeader("Game Window Detection");
        optionPanel.addComponent(new JLabel("How should SlimTrade detect where the Path of Exile game window is?"));
        optionPanel.addVerticalStrutSmall();
        optionPanel.addComponent(automaticButton);
        optionPanel.addComponent(monitorButton);
        optionPanel.addComponent(regionButton);
        optionPanel.addVerticalStrut();
        methodHeader = optionPanel.addHeader("Dynamic Method Header");

        for (Enumeration<AbstractButton> buttonIterator = buttonGroup.getElements(); buttonIterator.hasMoreElements(); )
            addRadioButtonListener(buttonIterator.nextElement());

        if (Platform.current == Platform.WINDOWS) {
            setHeaderText(automaticHeader);
            automaticButton.setSelected(true);
        } else {
            setHeaderText(monitorHeader);
            monitorButton.setSelected(true);
        }
    }

    private void setHeaderText(String text) {
        methodHeader.label.setText(text);
    }

    private void updateHeaderTextUsingSelectingButton(Object button) {
        if (button.equals(automaticButton)) {
            setHeaderText(automaticHeader);
        } else if (button.equals(monitorButton)) {
            setHeaderText(monitorHeader);
        } else if (button.equals(regionButton)) {
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
