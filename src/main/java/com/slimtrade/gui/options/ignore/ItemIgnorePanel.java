package main.java.com.slimtrade.gui.options.ignore;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.SaveConstants;
import main.java.com.slimtrade.gui.buttons.BasicButton;
import main.java.com.slimtrade.gui.components.AddRemovePanel;
import main.java.com.slimtrade.gui.enums.MatchType;
import main.java.com.slimtrade.gui.options.ISaveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class ItemIgnorePanel extends ContainerPanel implements ISaveable {

	private static final long serialVersionUID = 1L;

	private AddRemovePanel addRemovePanel = new AddRemovePanel();
	private JTextField itemText = new JTextField();
	private JComboBox<MatchType> typeCombo = new JComboBox<MatchType>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(60, 10, 120, 10);
	private JSpinner timerSpinner = new JSpinner(spinnerModel);
	private JButton addButton = new BasicButton("Ignore Item");

	//TODO : Impose max
	private final int MAX_IGNORE_COUNT = 40;

	public ItemIgnorePanel() {
		this.setVisible(false);

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
		for (Component c : addRemovePanel.getComponents()) {
			if (c instanceof IgnoreRow) {
				IgnoreRow row = (IgnoreRow) c;
				Main.saveManager.putObject(row.getIgnoreData().getItemName(), SaveConstants.numeredPath(SaveConstants.IgnoreItems.itemName, index));
				Main.saveManager.putObject(row.getIgnoreData().getMatchType().name(), SaveConstants.numeredPath(SaveConstants.IgnoreItems.matchType, index));
				Main.saveManager.putObject(row.getIgnoreData().getExpireTime(), SaveConstants.numeredPath(SaveConstants.IgnoreItems.expireTime, index));
				System.out.println("Ignore : ");
				for (String s : SaveConstants.numeredPath(SaveConstants.IgnoreItems.itemName, index)) {
					System.out.println("\t" + s);
				}
			}
			index++;
		}
	}

	@Override
	public void load() {
		for (int i = 0; i < MAX_IGNORE_COUNT; i++) {
			if(Main.saveManager.hasEntry(SaveConstants.numeredPath(SaveConstants.IgnoreItems.itemName, i))){
				try{
					String itemName = Main.saveManager.getString(SaveConstants.numeredPath(SaveConstants.IgnoreItems.itemName, i));
					MatchType matchType = MatchType.valueOf(Main.saveManager.getEnumValue(MatchType.class, SaveConstants.numeredPath(SaveConstants.IgnoreItems.matchType, i)));
					LocalDateTime expireTime = LocalDateTime.parse(Main.saveManager.getString(SaveConstants.numeredPath(SaveConstants.IgnoreItems.expireTime, i)));
					IgnoreData data = new IgnoreData(itemName, matchType, expireTime);
					System.out.println("REMAINING : " + data.getRemainingTime());
					if(data.getRemainingTime()>0){
						addRemovePanel.add(new IgnoreRow(data, addRemovePanel));
					}
				}catch (DateTimeParseException e){
					
				}
			}
		}
		addRemovePanel.saveChanges();
	}

}
