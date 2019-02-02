package main.java.com.slimtrade.gui.options;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GridBagPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	GridBagConstraints gc = new GridBagConstraints();
	
	public GridBagPanel() {
		this.setLayout(new GridBagLayout());
		gc.gridx=0;
		gc.gridy=0;
	}
	
	public void addRow(int spacer, Component... c){
		gc.gridx=0;
	}
	
}
