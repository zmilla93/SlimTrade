package main.java.com.slimtrade.gui.options.ignore;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.components.RemovablePanel;
import main.java.com.slimtrade.gui.enums.MatchType;
import main.java.com.slimtrade.gui.enums.PreloadedImage;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class IgnoreRow extends RemovablePanel {

	private static final long serialVersionUID = 1L;
	private final int ITEM_MAX_WIDTH = 300;
	
	public IgnoreRow(String name, MatchType type, int duration) {
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		LocalTime expireTime = LocalTime.now().plusMinutes(duration);
		IconButton removeButton = new IconButton(PreloadedImage.CLOSE.getImage(), 20);
		JLabel itemLabel = new JLabel(name);
		JPanel itemPanel = new JPanel(new GridBagLayout());
		itemPanel.setPreferredSize(new Dimension(ITEM_MAX_WIDTH, itemLabel.getPreferredSize().height));
		JLabel matchLabel = new JLabel(type.toString());
		JLabel timerLabel = new JLabel(Integer.toString(duration));
		
//		itemLabel.setPreferredSize(new Dimension(ITEM_MAX_WIDTH, itemLabel.getPreferredSize().height));
		itemPanel.setBackground(ColorManager.CLEAR);
		itemPanel.add(itemLabel);
		
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.gridx++;
//		this.add(new BufferPanel(ITEM_MAX_WIDTH, 0), gc);
		gc.gridx++;
		this.add(new BufferPanel(100, 0), gc);
		gc.gridx++;
		this.add(new BufferPanel(40, 0), gc);
		gc.gridx = 0;
		gc.gridy++;

//		gc.fill = GridBagConstraints.NONE;
		this.add(removeButton, gc);
		gc.insets.left = 10;
		gc.insets.right = 10;
		gc.gridx++;
		this.add(itemPanel, gc);
		gc.gridx++;
		this.add(matchLabel, gc);
		gc.gridx++;
		this.add(timerLabel, gc);
		gc.gridx++;
		
		this.setRemoveButton(removeButton);
		this.revalidate();
	}

}
