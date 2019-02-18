package main.java.com.slimtrade.gui.stash;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.enums.StashTabType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class StashTabPanel extends ContentPanel implements Saveable {

	private static final long serialVersionUID = 1L;

	JPanel rowContainer;
	GridBagConstraints gcRow;
	int rowBuffer = 10;

	public StashTabPanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gcRow = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gcRow.gridx = 0;
		gcRow.gridy = 0;
		// gcRow.insets = new Insets(0, 0, rowBuffer, 0);

		rowContainer = new JPanel(new GridBagLayout());
		rowContainer.setBackground(Color.RED);

		JScrollPane rowScrollPane = new JScrollPane(rowContainer);
		StashTabRow dummyRow = new StashTabRow();
		Dimension scrollPaneSize = dummyRow.getPreferredSize();
		scrollPaneSize.width += 25;
		scrollPaneSize.height = scrollPaneSize.height * 10 + rowBuffer * 10;
		rowScrollPane.setPreferredSize(scrollPaneSize);

		JPanel stashOptions = new JPanel();
		stashOptions.setLayout(new GridBagLayout());

		JButton addButton = new JButton("Add Stash Tab");

		JPanel alignPanel = new JPanel();
		JLabel alignLabel = new JLabel("Align stashtab");
		JButton alignButton = new JButton("Align Stash Tab Overlay");

		// JLabel

		stashOptions.add(alignLabel, gc);
		gc.gridx++;
		stashOptions.add(alignButton, gc);
		gc.gridx = 0;
		gc.gridy++;

		JLabel autoLabel = new JLabel("Automatically add new stash tabs");
		JCheckBox autoCheckbox = new JCheckBox();
		stashOptions.add(autoLabel, gc);
		gc.gridx++;
		stashOptions.add(autoCheckbox, gc);
		gc.gridx = 0;
		gc.gridy++;

		// this.addRow(new JLabel("TITLE"), gc);
		this.addRow(alignButton, gc);
		this.addRow(new BufferPanel(0, rowBuffer), gc);
		// this.addRow(new JLabel("Save stash tabs for quad tab detection or
		// assign color coding"), gc);
		this.addRow(new JLabel("Colors and Quad tabs must be manually set"), gc);
		this.addRow(new JLabel("Default white will use a randomized color with white text"), gc);
		this.addRow(new BufferPanel(0, rowBuffer), gc);
		this.addRow(addButton, gc);
		this.addRow(rowScrollPane, gc);
		// this.addRow(buttonPanel, gc);

		// this.addRow(c, gc);
		alignButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameManager.stashOverlayWindow.setVisible(true);
			}
		});

		addButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				addNewRow();
			}
		});
		load();
		// stashOptions.autoResize();
		this.autoResize();
	}

	private StashTabRow addNewRow() {
		StashTabRow row = new StashTabRow();
		rowContainer.add(row, gcRow);
		gcRow.gridy++;
		row.getDeleteButton().addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				row.setVisible(false);
				row.setDelete(true);
			}
		});
		rowContainer.revalidate();
		rowContainer.repaint();
		return row;
	}

	public void save() {
		int index = 0;
		Main.saveManager.deleteArray("stashTabs");
		for (Component c : rowContainer.getComponents()) {
			StashTabRow row = (StashTabRow) c;
			row.setFresh(false);
			if (row.isDelete()) {
				rowContainer.remove(c);
			} else {
				Main.saveManager.putString(row.getText(), "stashTabs", "tab" + index, "text");
				Main.saveManager.putString(row.getType().name(), "stashTabs", "tab" + index, "type");
				Main.saveManager.putString(row.getColor().name(), "stashTabs", "tab" + index, "color");
				index++;
			}
		}
		rowContainer.revalidate();
		rowContainer.repaint();
	}

	public void load() {
		int index = 0;
		rowContainer.removeAll();
		while (Main.saveManager.hasEntry("stashTabs", "tab" + index)) {
			StashTabRow row = addNewRow();
			row.setFresh(false);
			row.setText(Main.saveManager.getString("stashTabs", "tab" + index, "text"));
			row.setType(StashTabType.valueOf(Main.saveManager.getString("stashTabs", "tab" + index, "type")));
			row.setColor(StashTabColor.valueOf(Main.saveManager.getString("stashTabs", "tab" + index, "color")));
			index++;
		}
	}

}
