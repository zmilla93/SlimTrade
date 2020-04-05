package com.slimtrade.gui.options;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class InformationPanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;

	public InformationPanel(){

		JButton tutorialButton = new BasicButton("Tutorial");
		JButton gitButton = new BasicButton("Github");
		JButton emailButton = new BasicButton("E-Mail");
		JButton donateButton = new BasicButton("Donate");

		JLabel tutorialLabel = new CustomLabel("A brief overview of the program.", false);
		JLabel gitLabel = new CustomLabel("Submit feedback & bug reports on github.", false);
		JLabel emailLabel = new CustomLabel("Send an e-mail to slimtradepoe@gmail.com", false);
		JLabel donateLabel = new CustomLabel("Donate with PayPal if you wish to support the developer.", false);

		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.insets.bottom = 5;

		// Tutorial
		gc.fill = GridBagConstraints.BOTH;
		container.add(tutorialButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx++;
		gc.insets.left = 30;
		container.add(tutorialLabel, gc);
		gc.insets.left = 0;
		gc.gridx = 0;
		gc.gridy++;

		// Github
		gc.fill = GridBagConstraints.BOTH;
		container.add(gitButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx++;
		gc.insets.left = 30;
		container.add(gitLabel, gc);
        gc.insets.left = 0;
		gc.gridx = 0;
		gc.gridy++;

		// Github
		gc.fill = GridBagConstraints.BOTH;
		container.add(emailButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx++;
		gc.insets.left = 30;
		container.add(emailLabel, gc);
		gc.insets.left = 0;
		gc.gridx = 0;
		gc.gridy++;

		// Donate
		gc.fill = GridBagConstraints.BOTH;
		container.add(donateButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx++;
		gc.insets.left = 30;
		container.add(donateLabel, gc);
		gc.insets.left = 0;
		gc.gridx = 0;
		gc.gridy++;

		tutorialButton.addActionListener(e -> {
			FrameManager.optionsWindow.setVisible(false);
			FrameManager.showTutorialWindow();
		});

		gitButton.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/zmilla93/SlimTrade"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		});

		emailButton.addActionListener(e -> {
			try {
				Desktop.getDesktop().mail(new URI("mailto:slimtradepoe@gmail.com"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		});

		donateButton.addActionListener(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://paypal.me/zmilla93"));
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		});
	}

}
