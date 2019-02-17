package main.java.com.slimtrade.gui.options;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class BasicsPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;

	BasicsPanel() {
		this.setLayout(new GridBagLayout());
		// COLOR THEME
		JLabel colorLabel = new JLabel("Color");
		JComboBox<String> colorCombo = new JComboBox<String>();
		colorCombo.setFocusable(false);
		colorCombo.addItem("Default");

		// TIME FORMAT
		JLabel timeLabel = new JLabel("Time Format");
		JComboBox<String> timeCombo = new JComboBox<String>();
		timeCombo.setFocusable(false);
		timeCombo.addItem("Time Style #1");
		timeCombo.addItem("Time Style #2");

		// DATE FORMAT
		JLabel dateLabel = new JLabel("Date Format");
		JComboBox<String> dateCombo = new JComboBox<String>();
		dateCombo.setFocusable(false);
		dateCombo.addItem("Date Style #1");
		dateCombo.addItem("Date Style #2");

		// GUILD NAME
		JLabel guildLabel = new JLabel("Show Guild");
		JCheckBox guildCheckbox = new JCheckBox();
		JComboBox<String> guildCombo = new JComboBox<String>();
		guildCheckbox.setFocusable(false);
		guildCombo.setFocusable(false);
		guildCombo.addItem("Enabled");
		guildCombo.addItem("Disabled");

		// CLOSE ON KICK
		JLabel kickLabel = new JLabel("Close on Kick");
		JCheckBox kickCheckbox = new JCheckBox();
		JComboBox<String> kickCombo = new JComboBox<String>();
		kickCheckbox.setFocusable(false);
		kickCombo.setFocusable(false);
		kickCombo.addItem("Enabled");
		kickCombo.addItem("Disabled");

		// STASH
		JLabel stashLabel = new JLabel("Stash Location");
		JButton stashButton = new JButton("Edit");
		stashButton.setFocusable(false);

		// OVERLAY
		JLabel overlayLabel = new JLabel("Overlay Layout");
		JButton overlayButton = new JButton("Edit");
		overlayButton.setFocusable(false);

		
		
		
		//Actions
		stashButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				FrameManager.hideMenuFrames();
				FrameManager.menubar.setVisible(false);
				FrameManager.menubarToggle.setVisible(false);
				FrameManager.messageManager.setVisible(false);
				FrameManager.stashOverlayWindow.setVisible(true);
			}
		});
		
		overlayButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				//TODO : Make more specific
				FrameManager.hideMenuFrames();
				FrameManager.menubar.setVisible(false);
				FrameManager.menubarToggle.setVisible(false);
				FrameManager.messageManager.setVisible(false);
				FrameManager.overlayManager.showDialog();
			}
		});
		
		gc.insets = new Insets(0, 0, bufferY, 0);
		this.addPair(timeLabel, timeCombo, gc);
		this.addPair(dateLabel, dateCombo, gc);
		this.addPair(guildLabel, guildCheckbox, gc);
		gc.insets = new Insets(0, 0, 0, 0);
		this.addPair(kickLabel, kickCheckbox, gc);
		this.autoResize();
		// this.setMaximumSize(this.getPreferredSize());
	}
	
	private void addPair(Component c1, Component c2, GridBagConstraints gc){
		this.add(c1, gc);
		gc.gridx++;
		this.add(new BufferPanel(bufferX,0), gc);
		gc.gridx++;
		this.add(c2, gc);
		gc.gridx=0;
		gc.gridy++;
	}

}
