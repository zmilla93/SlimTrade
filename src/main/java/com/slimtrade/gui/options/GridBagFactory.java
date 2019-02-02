package main.java.com.slimtrade.gui.options;

import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;

import main.java.com.slimtrade.gui.panels.BufferPanel;

public class GridBagFactory extends JPanel {

	private static final long serialVersionUID = 1L;
	GridBagConstraints gc = new GridBagConstraints();

	public static JPanel createSpacedRow(int spacer, Component... c) {
		JPanel panel = new JPanel();
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		for (int i = 0; i < c.length; i++) {
			panel.add(c[i], gc);
			if (i < c.length - 1) {
				gc.gridx++;
				panel.add(new BufferPanel(spacer, 0));
				gc.gridx++;
			}
		}
		return panel;
	}

}
