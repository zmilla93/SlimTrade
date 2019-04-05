package main.java.com.slimtrade.gui.stash;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.enums.StashTabType;
import main.java.com.slimtrade.gui.components.AddRemovePanel;
import main.java.com.slimtrade.gui.options.ISaveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

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
		StashTabRow row = new StashTabRow();
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
		Main.saveManager.deleteObject("stashTabs");
		rowContainer.saveChanges();
		for (Component c : rowContainer.getComponents()) {
			System.out.println("Saving...");
			StashTabRow row = (StashTabRow) c;
			Main.saveManager.putObject(row.getText(), "stashTabs", "tab" + index, "text");
			Main.saveManager.putObject(row.getType().name(), "stashTabs", "tab" + index, "type");
			Main.saveManager.putObject(row.getColor().name(), "stashTabs", "tab" + index, "color");
			index++;
		}
	}

	public void load() {
		int index = 0;
		rowContainer.removeAll();
		while (Main.saveManager.hasEntry("stashTabs", "tab" + index)) {
			StashTabRow row = addNewRow();
			row.setText(Main.saveManager.getString("stashTabs", "tab" + index, "text"));
			row.setType(StashTabType.valueOf(Main.saveManager.getString("stashTabs", "tab" + index, "type")));
			row.setColor(StashTabColor.valueOf(Main.saveManager.getString("stashTabs", "tab" + index, "color")));
			index++;
		}
		rowContainer.saveChanges();
	}
	
	public void revertChanges(){
		rowContainer.revertChanges();
	}

}
