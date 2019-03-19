package main.java.com.slimtrade.gui.setup.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class PathPanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;

	public PathPanel(){

		JLabel info1 = new JLabel();
		JLabel info2 = new JLabel();
		JLabel info3 = new JLabel();
		if(Main.saveManager.isValidClientPath()){
			info1.setText("Valid client.txt file found!");
			info2.setText(Main.saveManager.getClientPath());
		}
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		container.add(info1, gc);
		gc.gridy++;
		container.add(info2, gc);
	}
	
}
