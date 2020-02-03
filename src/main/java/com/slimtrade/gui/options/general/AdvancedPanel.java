package com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import com.slimtrade.App;
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
		App.eventManager.addListener(this);
		
		this.load();
	}

	public void save() {
		if (clientRow.isChanged()) {
			new Thread(new Runnable() {
				public void run() {
					clientRow.setChanged(false);
					App.saveManager.putObject(clientRow.getText(), "general", "clientPath");

					App.saveManager.refreshPath();
					App.chatParser.setClientPath(clientRow.getText());

					App.fileMonitor.stopMonitor();
					App.chatParser.init();
					App.fileMonitor.startMonitor();

				}
			}).start();

		}
	}

	public void load() {
		if (App.saveManager.hasEntry("general", "clientPath")) {
			clientRow.setText(App.saveManager.getString("general", "clientPath"));
			clientRow.setChanged(false);
		}
	}

	@Override
	public void updateColor() {
		this.setBackground(ColorManager.BACKGROUND);
	}

}
