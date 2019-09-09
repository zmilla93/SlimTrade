package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import com.slimtrade.core.Main;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

public class AdvancedPanel extends ContainerPanel implements ISaveable, ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	AdvancedRow clientRow = new AdvancedRow("Client Path");

	public AdvancedPanel() {
		GridBagConstraints gc = new GridBagConstraints();

		this.container.add(clientRow, gc);

		clientRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = clientRow.getFileChooser().showOpenDialog(clientRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File clientFile = clientRow.getFileChooser().getSelectedFile();
					String path = clientFile.getPath();
					clientRow.setText(path);
				}
			}
		});

		this.updateColor();
		Main.eventManager.addListener(this);
		
		this.load();
	}

	public void save() {
		if (clientRow.isChanged()) {
			new Thread(new Runnable() {
				public void run() {
					clientRow.setChanged(false);
					Main.saveManager.putObject(clientRow.getText(), "general", "clientPath");

					Main.saveManager.refreshPath();
					Main.chatParser.setClientPath(clientRow.getText());

					Main.fileMonitor.stopMonitor();
					Main.chatParser.init();
					Main.fileMonitor.startMonitor();

				}
			}).start();

		}
	}

	public void load() {
		if (Main.saveManager.hasEntry("general", "clientPath")) {
			clientRow.setText(Main.saveManager.getString("general", "clientPath"));
			clientRow.setChanged(false);
		}
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
	}

}
