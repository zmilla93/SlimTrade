package main.java.com.slimtrade.gui.options.general;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.EventManager;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.ISaveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class BasicsOptionsPanel extends ContainerPanel implements ISaveable {

	private static final long serialVersionUID = 1L;

	private JTextField characterInput = new JTextField();
	private JCheckBox guildCheckbox = new JCheckBox();
	private JCheckBox kickCheckbox = new JCheckBox();
	private JButton editStashButton = new JButton();
	private JButton editOverlayButton = new JButton();

	public BasicsOptionsPanel() {

//		this.setBackground(Color.LIGHT_GRAY);

		guildCheckbox.setFocusable(false);
		kickCheckbox.setFocusable(false);
		editStashButton.setFocusable(false);
		editOverlayButton.setFocusable(false);

		JPanel showGuildContainer = new JPanel(new BorderLayout());
		guildCheckbox.setOpaque(false);
		showGuildContainer.setOpaque(false);
		showGuildContainer.add(guildCheckbox, BorderLayout.EAST);

		JPanel kickContainer = new JPanel(new BorderLayout());
		kickContainer.setOpaque(false);
		kickCheckbox.setOpaque(false);
		kickContainer.add(kickCheckbox, BorderLayout.EAST);

		JLabel characterLabel = new JLabel("Character Name");
		JLabel guildLabel = new JLabel("Show Guild Name");
		JLabel kickLabel = new JLabel("Close on Kick");
		editStashButton.setText("Edit Stash");
		editOverlayButton.setText("Edit Overlay");

		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets.bottom = 5;
		gc.weightx = 1;

		// Sizing
		gc.gridx = 1;
		container.add(new BufferPanel(20, 0), gc);
		gc.gridx = 2;
		container.add(new BufferPanel(140, 0), gc);
		gc.gridx = 0;
		gc.gridy++;

		// Character
		container.add(characterLabel, gc);
		gc.gridx = 2;
		container.add(characterInput, gc);
		gc.gridwidth = 1;
		gc.gridx = 0;
		gc.gridy++;

		// show Guild
		container.add(guildLabel, gc);
		gc.gridx = 2;

		container.add(showGuildContainer, gc);
		gc.gridx = 0;
		gc.gridy++;

		// Kick on Close
		container.add(kickLabel, gc);
		gc.gridx = 2;

		container.add(kickContainer, gc);
		gc.gridx = 0;
		gc.gridy++;

		// Edit Buttons
		gc.gridwidth = 3;
		container.add(editStashButton, gc);
		gc.gridy++;
		gc.insets.bottom = 0;
		container.add(editOverlayButton, gc);
		gc.gridy++;
		
		editStashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameManager.hideAllFrames();
				FrameManager.stashOverlayWindow.setShow(true);
			}
		});

		editOverlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameManager.hideAllFrames();
				FrameManager.overlayManager.showDialog();
			}
		});

		load();
	}

	@Override
	public void save() {
		String characterName = characterInput.getText().replaceAll("\\s", "");
		if (characterName.equals("")) {
			characterName = null;
		}
		EventManager.setCharacterName(characterName);
		Main.saveManager.putObject(characterName, "general", "character");
		Main.saveManager.putObject(guildCheckbox.isSelected(), "general", "showGuild");
		Main.saveManager.putObject(kickCheckbox.isSelected(), "general", "closeOnKick");
	}

	@Override
	public void load() {
		String characterName = Main.saveManager.getString("general", "character");
		EventManager.setCharacterName(characterName);
		characterInput.setText(characterName);
		guildCheckbox.setSelected(Main.saveManager.getBool("general", "showGuild"));
		kickCheckbox.setSelected(Main.saveManager.getBool("general", "closeOnKick"));
	}

}
