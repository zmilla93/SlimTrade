package com.slimtrade.gui.options;

import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;

public class InformationPanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;

	private final int buffer = 10;

    private JTextField gitText;
    private JTextField emailText;
    private JButton tutorialButton = new BasicButton("Show Tutorial");

	public InformationPanel(){
		this.setVisible(false);
		//TODO : Might need to change background color of text areas
//		super(false);
		
		JLabel versionLabel = new CustomLabel(References.APP_NAME);
		
		JLabel gitLabel = new CustomLabel("GitHub");
		gitText = new JTextField("https://github.com/zmilla93/SlimTrade");
		Dimension gitSize = gitText.getPreferredSize();
		gitSize.width++;
		gitText.setPreferredSize(gitSize);
		gitText.setBorder(null);
		gitText.setEditable(false);
		
		JLabel emailTitle = new CustomLabel("E-Mail");
        emailText = new JTextField("slimtradepoe@gmail.com");
		Dimension emailSize = emailText.getPreferredSize();
		emailSize.width++;
		emailText.setPreferredSize(emailSize);
		emailText.setBorder(null);
		emailText.setEditable(false);
		
		JLabel info1 = new CustomLabel("Bugs can be reported on github or via e-mail");
		JLabel info2 = new CustomLabel("Design/feature feedback can be sent via e-mail.");
		JLabel info3 = new CustomLabel("Please read the github post before sending feedback to avoid redundancy.");

		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.insets.bottom = buffer;
		
		gc.gridwidth = 2;
		container.add(versionLabel, gc);
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;
		
		container.add(gitLabel, gc);
		gc.gridx = 1;
		gc.insets.left = 30;
		container.add(gitText, gc);
        gc.insets.left = 0;
		gc.gridx = 0;
		gc.gridy++;

		container.add(emailTitle, gc);
		gc.gridx = 1;
		container.add(emailText, gc);
		gc.gridx = 0;
		gc.gridy++;

		gc.gridwidth = 2;
		gc.insets.bottom = 0;
		container.add(tutorialButton, gc);

		tutorialButton.addActionListener(e -> {
			FrameManager.optionsWindow.setVisible(false);
			FrameManager.showTutorialWindow();
		});
		gitText.setOpaque(false);
		emailText.setOpaque(false);
	}

    @Override
    public void updateColor() {
        super.updateColor();
		gitText.setForeground(ColorManager.TEXT);
		emailText.setForeground(ColorManager.TEXT);
    }
}
