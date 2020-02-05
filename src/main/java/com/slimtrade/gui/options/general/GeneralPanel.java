package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.ToggleButton;
import com.slimtrade.gui.panels.ContainerPanel;

public class GeneralPanel extends ContainerPanel implements ISaveable, ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	private BasicsPanel basicsPanel;
	private HistoryOptionsPanel historyPanel;
	private AudioPanel audioPanel;
	private AdvancedPanel advancedPanel;
	
	public GeneralPanel(){
		this.setVisible(false);
//		this.setBorder(null);
		
		ToggleButton basicsButton = new ToggleButton("Basics", true);
		ToggleButton historyButton = new ToggleButton("History", true);
		ToggleButton audioButton = new ToggleButton("Audio", true);
		ToggleButton advancedButton = new ToggleButton("Save Path", true);
		
		basicsPanel = new BasicsPanel();
		historyPanel = new HistoryOptionsPanel();
		audioPanel = new AudioPanel();
		advancedPanel = new AdvancedPanel();
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets.bottom = 5;

		container.add(basicsButton, gc);
		gc.gridy++;
		container.add(basicsPanel, gc);
		gc.gridy++;

		container.add(historyButton, gc);
		gc.gridy++;
		container.add(historyPanel, gc);
		gc.gridy++;

		container.add(audioButton, gc);
		gc.gridy++;
		container.add(audioPanel, gc);
		gc.gridy++;

		container.add(advancedButton, gc);
		gc.gridy++;
		container.add(advancedPanel, gc);
		gc.gridy++;
		
		FrameManager.linkToggle(basicsButton, basicsPanel);
		FrameManager.linkToggle(historyButton, historyPanel);
		FrameManager.linkToggle(audioButton, audioPanel);
		FrameManager.linkToggle(advancedButton, advancedPanel);
		
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
