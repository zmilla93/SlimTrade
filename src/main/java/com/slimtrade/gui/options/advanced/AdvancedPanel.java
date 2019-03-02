package main.java.com.slimtrade.gui.options.advanced;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class AdvancedPanel extends ContentPanel implements Saveable {

	private static final long serialVersionUID = 1L;
	private final int BUFFER_SIZE = 5;

	AdvancedRow clientRow = new AdvancedRow("Client Path");
	AdvancedRow saveRow = new AdvancedRow("Save Directory");

	public AdvancedPanel() {

		// TODO : hide save directory if valid
		this.addRow(clientRow, gc);
		this.addRow(new BufferPanel(0, BUFFER_SIZE), gc);
		this.addRow(saveRow, gc);

		clientRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = clientRow.getFileChooser().showOpenDialog(clientRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File clientFile = clientRow.getFileChooser().getSelectedFile();
					String path = clientFile.getPath();
					clientRow.getPathLabel().setText(path);
				}
			}
		});

		saveRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = saveRow.getFileChooser().showOpenDialog(saveRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File savePath = saveRow.getFileChooser().getSelectedFile();
					String path = savePath.getPath();
					saveRow.getPathLabel().setText(path);
				}
			}
		});

		if (Main.saveManager.validSaveDirectory) {
			// saveRow.setVisible(false);
		}

		this.load();
		this.autoResize();
	}

	public void save() {
		if(clientRow.isChanged()){
			Main.saveManager.putString(clientRow.getText(), "advanced", "clientPath");
		}
	}

	public void load() {
		try {
			if (Main.saveManager.hasEntry("advanced", "clientPath")) {
				clientRow.setText(Main.saveManager.getString("advanced", "clientPath"));
			} else if (Main.saveManager.validSaveDirectory) {
				clientRow.setText(Main.saveManager.getClientPath());
			}
		} catch (Exception e) {
		}
	}

}
