package com.slimtrade.gui.options.ignore;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.slimtrade.core.Main;
import com.slimtrade.core.SaveConstants;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.MatchType;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

public class ItemIgnorePanel extends ContainerPanel implements ISaveable {

	private static final long serialVersionUID = 1L;

	private AddRemovePanel addRemovePanel = new AddRemovePanel();
	private JTextField itemText = new JTextField();
	private JComboBox<MatchType> typeCombo = new JComboBox<MatchType>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(60, 10, 120, 10);
	private JSpinner timerSpinner = new JSpinner(spinnerModel);
	private JButton addButton = new BasicButton("Ignore Item");

	// TODO : Impose max
	private final int MAX_IGNORE_COUNT = 40;

	public ItemIgnorePanel() {
		this.setVisible(false);
		
		FrameManager.ignoreItemAddRemovePanel = addRemovePanel;

		JPanel entryPanel = new JPanel(new GridBagLayout());
		entryPanel.setOpaque(false);

		JLabel itemLabel = new JLabel("Item Name");
		JLabel typeLabel = new JLabel("Match Type");
		JLabel timerLabel = new JLabel("Minutes");

		for (MatchType type : MatchType.values()) {
			typeCombo.addItem(type);
		}
		((DefaultEditor) timerSpinner.getEditor()).getTextField().setEditable(false);
		((DefaultEditor) timerSpinner.getEditor()).getTextField().setHighlighter(null);

		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		gc.insets.left = 10;
		gc.insets.right = 10;

		// Entry Panel
		gc.gridx = 1;
		entryPanel.add(new BufferPanel(200, 0), gc);
		gc.gridy++;

		entryPanel.add(itemLabel, gc);
		gc.gridx++;
		entryPanel.add(typeLabel, gc);
		gc.gridx++;
		entryPanel.add(timerLabel, gc);
		gc.gridx = 0;
		gc.gridy++;

		entryPanel.add(addButton, gc);
		gc.gridx++;
		gc.fill = GridBagConstraints.BOTH;
		entryPanel.add(itemText, gc);
		gc.gridx++;
		entryPanel.add(typeCombo, gc);
		gc.gridx++;
		entryPanel.add(timerSpinner, gc);
		gc.gridx = 0;
		gc.gridy++;

		// Reset gc
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.insets.left = 0;
		gc.insets.right = 0;

		// Container
		container.add(entryPanel, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 15), gc);
		gc.gridy++;
		container.add(addRemovePanel, gc);
		gc.gridy++;

		load();

		FrameManager.itemIgnorePanel = this;
		
		AddRemovePanel local = addRemovePanel;
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addRemovePanel.addPanel(new IgnoreRow(new IgnoreData(itemText.getText(), (MatchType) typeCombo.getSelectedItem(), (int) timerSpinner.getValue()), local));
			}
		});
	}

	public void revertChanges() {
		addRemovePanel.revertChanges();
	}

	@Override
	public void save() {
		addRemovePanel.saveChanges();
		int index = 0;
		Main.saveManager.deleteObject(SaveConstants.IgnoreItems.base);
		ArrayList<IgnoreData> fullData = new ArrayList<IgnoreData>();
		for (Component c : addRemovePanel.getComponents()) {
			if (c instanceof IgnoreRow) {
				IgnoreRow row = (IgnoreRow) c;
				IgnoreData rowData = row.getIgnoreData();
				Main.saveManager.putObject(rowData.getItemName(), SaveConstants.IgnoreItems.getItemName(index));
				Main.saveManager.putObject(rowData.getMatchType().name(), SaveConstants.IgnoreItems.getMatchType(index));
				Main.saveManager.putObject(rowData.getExpireTime(), SaveConstants.IgnoreItems.getExpireTime(index));
				fullData.add(rowData);
			}
			index++;
		}
		Main.chatParser.setWhisperIgnoreTerms(fullData);
	}

	@Override
	public void load() {
		ArrayList<IgnoreData> fullData = new ArrayList<IgnoreData>();
		for (int index = 0; index < MAX_IGNORE_COUNT; index++) {
			if (Main.saveManager.hasEntry(SaveConstants.IgnoreItems.getItemName(index))) {
				try {
					String itemName = Main.saveManager.getString(SaveConstants.IgnoreItems.getItemName(index));
					MatchType matchType = MatchType.valueOf(Main.saveManager.getEnumValue(MatchType.class, SaveConstants.IgnoreItems.getMatchType(index)));
					LocalDateTime expireTime = LocalDateTime.parse(Main.saveManager.getString(SaveConstants.IgnoreItems.getExpireTime(index)));
					IgnoreData data = new IgnoreData(itemName, matchType, expireTime);
					if (data.getRemainingTime() > 0) {
						addRemovePanel.add(new IgnoreRow(data, addRemovePanel));
						fullData.add(data);
					}
				} catch (DateTimeParseException e) {

				}
			}
		}
		addRemovePanel.saveChanges();
		Main.chatParser.setWhisperIgnoreTerms(fullData);
	}

}
