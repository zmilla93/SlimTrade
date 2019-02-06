package main.java.com.slimtrade.gui.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;

public class BasicsPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private int bufferX = 30;
	private int bufferY = 5;

	BasicsPanel(int width, int height) {
		super(width, height);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		GridBagConstraints gcMain = new GridBagConstraints();
		GridBagConstraints gcRow = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		// COLOR THEME
		JLabel colorLabel = new JLabel("Time Format");
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

		gcMain.gridx = 0;
		gcMain.gridy = 0;
		gcRow.gridx = 0;
		gcRow.gridy = 0;

		this.addPair(timeLabel, timeCombo, gcMain);
		this.addPair(dateLabel, dateCombo, gcMain);
		// this.addPair(guildLabel, guildCombo, gcMain);
		this.addPair(guildLabel, guildCheckbox, gcMain);
		// this.addPair(kickLabel, kickCombo, gcMain);
		this.addPair(kickLabel, kickCheckbox, gcMain);
		this.addPair(stashLabel, stashButton, gcMain);
		this.addPair(overlayLabel, overlayButton, gcMain);
		
		
		//Actions
		stashButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				FrameManager.hideMenuFrames();
				FrameManager.menubar.setVisible(false);
				FrameManager.menubarToggle.setVisible(false);
				FrameManager.stashOverlay.setVisible(true);
			}
		});
		
		overlayButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				//TODO : Make more specific
				FrameManager.hideMenuFrames();
				FrameManager.menubar.setVisible(false);
				FrameManager.menubarToggle.setVisible(false);
				FrameManager.overlayManager.showDialog();
			}
		});

		// DECENT

		// JPanel pneg1 = new JPanel(new GridBagLayout());
		// pneg1.add(new BufferPanel(10, 0), gcRow);
		// gcRow.gridx++;
		// pneg1.add(timeLabel, gcRow);
		// gcRow.gridx++;
		// pneg1.add(new BufferPanel(40, 0), gcRow);
		// gcRow.gridx++;
		// pneg1.add(timeCombo, gcRow);
		// gcRow.gridx = 0;
		// gcRow.gridy++;
		// pneg1.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
		// Color.black));

		// this.add(pneg1, gcMain);
		// gcMain.gridy++;
		// this.add(new BufferPanel(0, 20), gcMain);
		// gcMain.gridy++;

		// JPanel p0 = new JPanel(new GridBagLayout());
		// p0.add(new BufferPanel(10, 0), gcRow);
		// gcRow.gridx++;
		// p0.add(dateLabel, gcRow);
		// gcRow.gridx++;
		// p0.add(new BufferPanel(40, 0), gcRow);
		// gcRow.gridx++;
		// p0.add(dateCombo, gcRow);
		// gcRow.gridx = 0;
		// gcRow.gridy++;
		//
		// this.add(p0, gcMain);
		// gcMain.gridy++;
		// this.add(new BufferPanel(0, 20), gcMain);
		// gcMain.gridy++;

		// JPanel p1 = new JPanel(new GridBagLayout());
		// p1.add(new BufferPanel(10, 0), gcRow);
		// gcRow.gridx++;
		// p1.add(stashLabel, gcRow);
		// gcRow.gridx++;
		// p1.add(new BufferPanel(40, 0), gcRow);
		// gcRow.gridx++;
		// p1.add(stashButton, gcRow);
		// gcRow.gridx = 0;
		// gcRow.gridy++;
		//
		// this.add(p1, gcMain);
		// gcMain.gridy++;
		// this.add(new BufferPanel(0, 20), gcMain);
		// gcMain.gridy++;
		//
		// JPanel p2 = new JPanel(new GridBagLayout());
		// p2.add(new BufferPanel(10, 0), gcRow);
		// gcRow.gridx++;
		// p2.add(overlayLabel, gcRow);
		// gcRow.gridx++;
		// p2.add(new BufferPanel(40, 0), gcRow);
		// gcRow.gridx++;
		// p2.add(overlayButton, gcRow);
		// gcRow.gridx = 0;
		// gcRow.gridy++;
		//
		// this.add(p2, gcMain);
		// gcMain.gridy++;

		// this.addPairPanel(stashLabel, stashButton, bufferX, bufferY, gc);
		// this.addPair(stashLabel, stashButton, bufferX, bufferY, gc);
		// this.addPair(overlayLabel, overlayButton, bufferX, bufferY, gc);
		// this.addPairPanel(overlayLabel, overlayButton, bufferX, bufferY, gc);
		// this.add(GridBagFactory.createSpacedRow(10, stashLabel, stashButton),
		// gc);
		// gc.gridy++;
		// this.add(GridBagFactory.createSpacedRow(10, overlayLabel,
		// overlayButton), gc);

		this.autoResize();
		// this.setMaximumSize(this.getPreferredSize());

	}

}
