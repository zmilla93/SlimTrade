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

	AdvancedRow clientRow = new AdvancedRow("Client Path");

	public AdvancedPanel() {
		this.addRow(clientRow, gc);

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

		this.load();
		this.autoResize();
	}

	public void save() {
		if (clientRow.isChanged()) {
			Main.saveManager.putObject(clientRow.getText(), "general", "clientDirectory");
		}
	}

	public void load() {
		if (Main.saveManager.hasEntry("general", "clientDirectory")) {
			clientRow.setText(Main.saveManager.getString("general", "clientDirectory"));
		}
	}

}
