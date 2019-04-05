package main.java.com.slimtrade.gui.options.ignore;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import main.java.com.slimtrade.gui.buttons.BasicButton;
import main.java.com.slimtrade.gui.components.AddRemovePanel;
import main.java.com.slimtrade.gui.enums.MatchType;
import main.java.com.slimtrade.gui.options.ISaveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class ItemIgnorePanel extends ContainerPanel implements ISaveable {

	private static final long serialVersionUID = 1L;

	private AddRemovePanel addRemovePanel = new AddRemovePanel();
	
	
	
	private final int defaultTime = 60;
	
	private JTextField itemText = new JTextField();
	private JComboBox<MatchType> typeCombo = new JComboBox<MatchType>();
	private SpinnerModel spinnerModel = new SpinnerNumberModel(60, 10, 120, 10);
	private JSpinner timerSpinner = new JSpinner(spinnerModel);
	private JButton addButton = new BasicButton("Ignore Item");
//	private JButton addButton = new JButton("Ignore Item");
	
//	private JButton addButton = new BasicButton();
	
	public ItemIgnorePanel(){
		this.setVisible(false);
		
		JPanel entryPanel = new JPanel(new GridBagLayout());
		
		JLabel itemLabel = new JLabel("Item Name");
		JLabel typeLabel = new JLabel("Match Type");
		JLabel timerLabel = new JLabel("Minutes");
		
		for(MatchType type : MatchType.values()){
			typeCombo.addItem(type);
		}
				
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.insets.left = 10;
		gc.insets.right = 10;
		
		//Entry Panel
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
		
		//Reset gc
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.insets.left = 0;
		gc.insets.right = 0;
		
		//Container
		container.add(entryPanel, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 15), gc);
		gc.gridy++;
		container.add(addRemovePanel, gc);
		gc.gridy++;
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemovePanel.addPanel(new IgnoreRow(itemText.getText(), (MatchType)typeCombo.getSelectedItem(), (int)timerSpinner.getValue()));
			}
		});
		
		System.out.println(addButton.getPreferredSize());
		
	}
	
	public void revertChanges(){
		addRemovePanel.revertChanges();
	}

	@Override
	public void save() {
		//TODO : Saving
		addRemovePanel.saveChanges();
	}

	@Override
	public void load() {
		//TODO : Loading
	}
	
}
