package main.java.com.slimtrade.gui.setup;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import main.java.com.slimtrade.gui.basic.AbstractResizableWindow;
import main.java.com.slimtrade.gui.setup.panels.PathPanel;

public class SetupWindow extends AbstractResizableWindow {

	private static final long serialVersionUID = 1L;

	public SetupWindow(){
		super("Setup");
		
		JLabel pathLabel = new JLabel("Client Path");
		PathPanel pathPanel = new PathPanel();
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		container.add(pathLabel, gc);
		gc.gridy++;
		container.add(pathPanel, gc);

		this.pack();
	}
	
}
