package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.SectionHeader;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.popups.PatchNotesWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InformationPanel extends ContainerPanel {

    private static final long serialVersionUID = 1L;

    public InformationPanel() {

        JButton tutorialButton = new BasicButton("Tutorial");
        JButton patchNotesButton = new BasicButton("Patch Notes");
        JButton logsButtons = new BasicButton("Log Folder");


        JButton gitButton = new BasicButton("GitHub");
        JButton emailButton = new BasicButton("E-Mail");
//        JButton donateButton = new BasicButton("Donate");
        JButton discordButton = new BasicButton("Discord");

        JLabel tutorialLabel = new CustomLabel("A brief overview of the program.", false);
        JLabel patchNotesLabel = new CustomLabel("View the SlimTrade patch notes.", false);
        JLabel logsLabel = new CustomLabel("Open the logs folder.", false);
        JLabel gitLabel = new CustomLabel("Submit feedback & bug reports on GitHub.", false);
        JLabel emailLabel = new CustomLabel("Send an e-mail to slimtradepoe@gmail.com.", false);
        JLabel donateLabel = new CustomLabel("Donate with PayPal if you wish to support the developer.", false);
        JLabel discordLabel = new CustomLabel("Join the official discord for discussion and feedback.", false);

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

//        container.add(Box.createHorizontalStrut(250), gc);
        gc.gridy++;


        // Tutorial
//        gc.gridwidth = 2;
        int i = 5;
        gc.insets.bottom = i;
        gc.fill = GridBagConstraints.BOTH;
        container.add(new SectionHeader("About"), gc);
        gc.gridy++;
        gc.insets.bottom = 5;

        int sideInset = 20;
        gc.insets.left = sideInset;
        gc.insets.right = sideInset;

        container.add(tutorialButton, gc);
        gc.gridy++;

        container.add(patchNotesButton, gc);
        gc.gridy++;

        gc.insets.bottom = 30;
        container.add(logsButtons, gc);
        gc.gridy++;

        gc.insets.left = 0;
        gc.insets.right = 0;
        gc.insets.bottom = i;
        container.add(new SectionHeader("Contact"), gc);
        gc.insets.left = sideInset;
        gc.insets.right = sideInset;
        gc.insets.bottom = 5;
        gc.gridy++;

        // Github
//        gc.fill = GridBagConstraints.BOTH;
        container.add(gitButton, gc);

        gc.fill = GridBagConstraints.NONE;
//        gc.gridx++;
//        gc.insets.left = 30;
//        container.add(gitLabel, gc);
//        gc.insets.left = 0;
//        gc.gridx = 0;
        gc.gridy++;

        // E-Mail
        gc.fill = GridBagConstraints.BOTH;
        container.add(emailButton, gc);
        gc.fill = GridBagConstraints.NONE;
//        gc.gridx++;
//        gc.insets.left = 30;
//        container.add(emailLabel, gc);
//        gc.insets.left = 0;
//        gc.gridx = 0;
        gc.gridy++;

        // Discord
        gc.fill = GridBagConstraints.BOTH;
        container.add(discordButton, gc);
        gc.fill = GridBagConstraints.NONE;
//        gc.gridx++;
//        gc.insets.left = 30;
//        container.add(discordLabel, gc);
//        gc.insets.left = 0;
//        gc.gridx = 0;
        gc.gridy++;

        tutorialButton.addActionListener(e -> {
            FrameManager.optionsWindow.setVisible(false);
            FrameManager.showTutorialWindow();
        });

        patchNotesButton.addActionListener(e -> {
            FrameManager.optionsWindow.setVisible(false);
            if (FrameManager.patchNotesWindow == null) {
                FrameManager.patchNotesWindow = new PatchNotesWindow();
            } else {
                FrameManager.patchNotesWindow.setVisible(true);
            }
        });

        logsButtons.addActionListener(e -> {
            TradeUtility.openFolder(App.saveManager.INSTALL_DIRECTORY + File.separator + "logs");
        });

        gitButton.addActionListener(e -> {
            TradeUtility.openLink(References.GITHUB);
        });

        emailButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().mail(new URI("mailto:slimtradepoe@gmail.com"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        discordButton.addActionListener(e -> {
            TradeUtility.openLink(References.DISCORD);
        });
    }

}
