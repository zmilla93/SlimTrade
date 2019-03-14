package main.java.com.slimtrade.gui.options.general;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class AdvancedPanel extends ContainerPanel implements Saveable {

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
//					clientRow.getPathLabel().setText(path);
					clientRow.setText(path);
				}
			}
		});

		this.load();
//		this.autoResize();
	}

	public void save() {
		if (clientRow.isChanged()) {
			Main.saveManager.putObject(clientRow.getText(), "general", "clientPath");

			Main.saveManager.refreshPath();
			Main.chatParser.setClientPath(clientRow.getText());
			
			Main.fileMonitor.stopMonitor();
			Main.chatParser.init();
			Main.fileMonitor.startMonitor();
		}
	}

	public void load() {
		if (Main.saveManager.hasEntry("general", "clientPath")) {
			clientRow.setText(Main.saveManager.getString("general", "clientPath"));
		}
	}

}
