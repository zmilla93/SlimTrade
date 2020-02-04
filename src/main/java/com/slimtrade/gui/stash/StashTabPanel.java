package com.slimtrade.gui.stash;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.StashTab;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

public class StashTabPanel extends ContainerPanel implements ISaveable {

	private static final long serialVersionUID = 1L;

	public static final AddRemovePanel rowContainer= new AddRemovePanel();
	private GridBagConstraints gcRow;
	int rowBuffer = 10;

	public StashTabPanel() {
		this.setVisible(false);
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gcRow = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gcRow.gridx = 0;
		gcRow.gridy = 0;
		gcRow.insets.bottom = 5;
		
//		rowContainer = new AddRemovePanel();

		JPanel stashOptions = new JPanel();
		stashOptions.setLayout(new GridBagLayout());

		JButton addButton = new JButton("Add Stash Tab");

//		JLabel alignLabel = new JLabel("Align stashtab");
//		JButton alignButton = new JButton("Align Stash Tab Overlay");

		
		container.add(new JLabel("Stash tabs only need to be added if you want to set colors or quad tabs"), gc);
		gc.gridy++;
		container.add(new JLabel("Default white will use a randomized color like normal"), gc);
		gc.gridy++;
		container.add(new BufferPanel(0, rowBuffer), gc);
		gc.gridy++;
		container.add(addButton, gc);
		gc.gridy++;
		container.add(rowContainer, gc);
		

		addButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				addNewRow();
				revalidate();
			}
		});
		load();
	}

	private StashTabRow addNewRow() {
		StashTabRow row = new StashTabRow(rowContainer);
		rowContainer.addPanel(row);
//		gcRow.gridy++;
//		row.getDeleteButton().addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e) {
//				row.markForDeletion();
//			}
//		});
		return row;
	}

	public void save() {
		int index = 0;
		App.saveManager.saveFile.stashTabs.clear();
		rowContainer.saveChanges();
		for (Component c : rowContainer.getComponents()) {
			StashTabRow row = (StashTabRow) c;
			App.saveManager.saveFile.stashTabs.add(row.getStashTabData());
			index++;
		}
	}

	public void load() {
		int index = 0;
		rowContainer.removeAll();
		for(StashTab tab : App.saveManager.saveFile.stashTabs) {
            StashTabRow row = addNewRow();
            row.setText(tab.name);
            row.setType(tab.type);
            row.setColor(tab.color);
            index++;
        }
//		while (App.saveManager.hasEntry("stashTabs", "tab" + index)) {
//			StashTabRow row = addNewRow();
//			row.setText(App.saveManager.getString("stashTabs", "tab" + index, "text"));
//			row.setType(StashTabType.valueOf(App.saveManager.getString("stashTabs", "tab" + index, "type")));
//			row.setColor(StashTabColor.valueOf(App.saveManager.getString("stashTabs", "tab" + index, "color")));
//			index++;
//		}
		rowContainer.saveChanges();
	}
	
	public void revertChanges(){
		rowContainer.revertChanges();
	}

}
