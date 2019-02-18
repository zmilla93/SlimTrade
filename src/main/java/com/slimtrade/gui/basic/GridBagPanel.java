package main.java.com.slimtrade.gui.basic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GridBagPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public GridBagConstraints gc = new GridBagConstraints();
	public Insets inset = new Insets(0, 0, 0, 0);

	JLabel defaultLabel;

	public GridBagPanel() {
		buildPanel();
	}

	public GridBagPanel(String label) {
		buildPanel();
		setLabel(label);
	}
	
	public GridBagPanel(String label, int buffer) {
		buildPanel();
		inset.set(buffer, buffer, buffer, buffer);
		setLabel(label);
	}

	private void buildPanel() {
		this.setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets = inset;
	}

	public void setLabel(String s) {
		this.removeAll();
		defaultLabel = new JLabel(s);
		this.add(defaultLabel, gc);
	}

}
