package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.SectionHeader;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

public class GeneralPanel extends ContainerPanel implements ISaveable, IColorable {

	private static final long serialVersionUID = 1L;

	private BasicsPanel basicsPanel;
	private HistoryOptionsPanel historyPanel;
	private AudioPanel audioPanel;
	private AdvancedPanel advancedPanel;

	private final int smallGap = 4;
	private final int largeGap = 15;

	public GeneralPanel(){
		this.setVisible(false);
//		this.setBorder(null);
		
//		ToggleButton basicsButton = new ToggleButton("Basics", true);
//		ToggleButton historyButton = new ToggleButton("History", true);
//		ToggleButton audioButton = new ToggleButton("Audio", true);
//		ToggleButton advancedButton = new ToggleButton("Save Path", true);
		SectionHeader basicsHeader = new SectionHeader("Basics");
		SectionHeader historyHeader = new SectionHeader("History");
		SectionHeader audioHeader = new SectionHeader("Audio");
		SectionHeader clientHeader = new SectionHeader("Client");

		basicsPanel = new BasicsPanel();
		historyPanel = new HistoryOptionsPanel();
		audioPanel = new AudioPanel();
		advancedPanel = new AdvancedPanel();
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

        gc.insets.bottom = smallGap;
		container.add(basicsHeader, gc);
		gc.gridy++;
        gc.insets.bottom = largeGap;
		container.add(basicsPanel, gc);
		gc.gridy++;

        gc.insets.bottom = smallGap;
		container.add(historyHeader, gc);
		gc.gridy++;
        gc.insets.bottom = largeGap;
		container.add(historyPanel, gc);
		gc.gridy++;

        gc.insets.bottom = smallGap;
		container.add(audioHeader, gc);
        gc.insets.bottom = largeGap;
		gc.gridy++;
		container.add(audioPanel, gc);
		gc.gridy++;

        gc.insets.bottom = smallGap;
		container.add(clientHeader, gc);
		gc.gridy++;
        gc.insets.bottom = 0;
		container.add(advancedPanel, gc);
		gc.gridy++;
		
//		FrameManager.linkToggle(basicsButton, basicsPanel);
//		FrameManager.linkToggle(historyButton, historyPanel);
//		FrameManager.linkToggle(audioButton, audioPanel);
//		FrameManager.linkToggle(advancedButton, advancedPanel);
//
		App.eventManager.addListener(this);
		this.updateColor();
		
	}

	@Override
	public void save() {
//		System.out.println(basicsPanel);
		basicsPanel.save();
		historyPanel.save();
		audioPanel.save();
		advancedPanel.save();
	}

	@Override
	public void load() {
		basicsPanel.load();
		historyPanel.load();
		audioPanel.load();
		advancedPanel.load();
	}

	@Override
	public void updateColor() {
	    this.setBackground(ColorManager.LOW_CONSTRAST_1);

//	    this.setBackground();
	}
	
}
