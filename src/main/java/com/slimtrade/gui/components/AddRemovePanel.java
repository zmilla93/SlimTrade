package com.slimtrade.gui.components;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AddRemovePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagConstraints gc = new GridBagConstraints();
	private int spacer = 5;

	public AddRemovePanel() {
		this.setLayout(FrameManager.gridbag);
		this.setBackground(new Color(1, 1, 1, 0));
		gc.gridx = 0;
		gc.gridy = 0;
	}

	public void addPanel(JPanel panel) {
	    int i = this.getComponentCount();
        gc.gridy = i;
        gc.insets.top = i == 0 ? 0 : spacer;
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
