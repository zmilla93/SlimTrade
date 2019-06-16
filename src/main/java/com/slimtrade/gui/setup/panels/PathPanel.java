package main.java.com.slimtrade.gui.setup.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.options.general.AdvancedRow;
import main.java.com.slimtrade.gui.setup.SetupWindow;

public class PathPanel extends AbstractSetupPanel {

	private static final long serialVersionUID = 1L;
	
	public PathPanel(SetupWindow parent){
		super(parent);
		JLabel foundLabel = new JLabel();
		JLabel foundLabel2 = new JLabel();
		JLabel info2 = new JLabel();
		JLabel info3 = new JLabel();
		
		AdvancedRow row = new AdvancedRow("Client Path");
		
		int clientCount = Main.saveManager.getClientCount();
		
		if(clientCount == 0){
			foundLabel.setText("Unable to locate a valid Client.txt file.");
		}else if(clientCount == 1){
			foundLabel.setText("Client.txt file has been successfully located!");
			row.setText(Main.saveManager.getClientPath());
			this.complete = true;
			parent.updateButtonState();
		}else if(clientCount > 1){
			foundLabel.setText("Multiple potential Client.txt files have been found. Please manually set the correct one.");
		}
		
		row.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = row.getFileChooser().showOpenDialog(row);
				if (action == JFileChooser.APPROVE_OPTION) {
					File clientFile = row.getFileChooser().getSelectedFile();
					String path = clientFile.getPath();
					row.setText(path);
					complete = true;
					parent.updateButtonState();
				}
			}
		});
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		container.add(foundLabel, gc);
		gc.gridy++;
		container.add(row, gc);
		gc.gridy++;
	}
	
}
