package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.SectionHeader;
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

    //    JPanel aboutOuterPanel = new JPanel(new GridBagLayout());
//    JPanel aboutInnerPanel = new JPanel(new GridBagLayout());
//    JPanel contactOuterPanel = new JPanel(new GridBagLayout());
//    JPanel contactInnerPanel = new JPanel(new GridBagLayout());
    ContainerPanel aboutPanel = new ColorPanel();
    JPanel aboutContainer = aboutPanel.container;
    ContainerPanel contactPanel = new ColorPanel();
    JPanel contactContainer = contactPanel.container;

    public InformationPanel() {

        container.setLayout(new GridBagLayout());

        SectionHeader aboutHeader = new SectionHeader("About");
        JButton tutorialButton = new BasicButton("Tutorial");
        JButton patchNotesButton = new BasicButton("Patch Notes");
        JButton logsButtons = new BasicButton("Log Folder");

        SectionHeader contactHeader = new SectionHeader("Contact");
        JButton gitButton = new BasicButton("GitHub");
        JButton emailButton = new BasicButton("E-Mail");
        JButton discordButton = new BasicButton("Discord");

//        JLabel tutorialLabel = new CustomLabel("A brief overview of the program.", false);
//        JLabel patchNotesLabel = new CustomLabel("View the SlimTrade patch notes.", false);
//        JLabel logsLabel = new CustomLabel("Open the logs folder.", false);
//        JLabel gitLabel = new CustomLabel("Submit feedback & bug reports on GitHub.", false);
//        JLabel emailLabel = new CustomLabel("Send an e-mail to slimtradepoe@gmail.com.", false);
//        JLabel donateLabel = new CustomLabel("Donate with PayPal if you wish to support the developer.", false);
//        JLabel discordLabel = new CustomLabel("Join the official discord for discussion and feedback.", false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        gc.weighty = 1;
//        gc.insets = new Insets(BORDER_GAP_SMALL, BORDER_GAP_SMALL, BORDER_GAP_SMALL, BORDER_GAP_SMALL);
//        aboutOuterPanel.add(aboutInnerPanel, gc);
//        contactOuterPanel.add(contactInnerPanel, gc);

        gc.insets = new Insets(0, 0, 0, 0);
        int inset = 20;

        // Main Container
        container.add(aboutHeader, gc);
        gc.gridy += 2;
        gc.insets.top = 20;
        container.add(contactHeader, gc);
        gc.gridy--;

        gc.insets.top = 5;
        gc.insets.left = inset;
        gc.insets.right = inset;
        container.add(aboutPanel, gc);
        gc.gridy += 2;
        container.add(contactPanel, gc);
        gc.insets.left = 0;
        gc.insets.right = 0;

        // About Buttons
        gc.gridy = 0;
        gc.insets.top = 0;
        aboutContainer.add(tutorialButton, gc);
        gc.insets.top = 5;
        gc.gridy++;
        aboutContainer.add(patchNotesButton, gc);
        gc.gridy++;
        aboutContainer.add(logsButtons, gc);

        // Contact Buttons
        gc.gridy = 0;
        gc.insets.top = 0;
        contactContainer.add(gitButton, gc);
        gc.insets.top = 5;
        gc.gridy++;
        contactContainer.add(emailButton, gc);
        gc.gridy++;
        contactContainer.add(discordButton, gc);

        // Listeners
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

    private static class ColorPanel extends ContainerPanel {
        @Override
        public void updateColor() {
            super.updateColor();
            setBackground(ColorManager.BACKGROUND);
        }
    }

}
