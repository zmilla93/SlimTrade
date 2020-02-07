package com.slimtrade.gui.stash;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.StashTab;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StashTabPanel extends ContainerPanel implements ISaveable, IColorable {

	private static final long serialVersionUID = 1L;

	public static final AddRemovePanel rowContainer= new AddRemovePanel();
	private GridBagConstraints gcRow;
	int rowBuffer = 10;
	private JPanel stashOptions;

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

		stashOptions = new JPanel();
		stashOptions.setLayout(new GridBagLayout());

		JButton addButton = new BasicButton("Add Stash Tab");

//		JLabel alignLabel = new JLabel("Align stashtab");
//		JButton alignButton = new JButton("Align Stash Tab Overlay");

//        Add stash tab names to mark
//        quad tabs or to add color coding

		
		container.add(new JLabel("Add stash tabs by name to mark quad tabs or add color coding."), gc);
		gc.gridy++;
//		container.add(new JLabel("quad tabs or add color coding."), gc);
//		gc.gridy++;
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
        App.eventManager.addListener(this);
        updateColor();

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
//		App.eventManager.addListener(this);
//		updateColor();
	}

    @Override
    public void updateColor() {
        super.updateColor();
        stashOptions.setBackground(Color.GREEN);
    }

    public void revertChanges(){
		rowContainer.revertChanges();
	}

}
