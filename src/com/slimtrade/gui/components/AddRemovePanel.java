package com.slimtrade.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AddRemovePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints gc = new GridBagConstraints();

	public AddRemovePanel() {
		// this.setPreferredSize(new Dimension(400,100));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		// this.setLayout(new GridLayout(0, 1, 5, 5));
		this.setBackground(Color.RED);
	}

	public void addPanel(JPanel panel) {
		this.add(panel, gc);
		this.revalidate();
		this.repaint();
	}

	public void saveChanges() {
		for (Component c : this.getComponents()) {
			if (c instanceof RemovablePanel) {
				RemovablePanel panel = (RemovablePanel) c;
				if (panel.isToBeDeleted()) {
					this.remove(panel);
				} else if (panel.isNewPanel()) {
					panel.setNewPanel(false);
				}
			}
		}
		this.revalidate();
		this.repaint();
	}

	public void revertChanges() {
		for (Component c : this.getComponents()) {
			if (c instanceof RemovablePanel) {
				RemovablePanel panel = (RemovablePanel) c;
				if (panel.isNewPanel()) {
					this.remove(panel);
				} else if (panel.isToBeDeleted()) {
					panel.setVisible(true);
					panel.setToBeDeleted(false);
				}

			}
		}
		this.revalidate();
		this.repaint();
	}

}
