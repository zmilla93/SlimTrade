package com.slimtrade.gui.stash;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

class StashTabCellRenderer extends JPanel implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 1L;

	public StashTabCellRenderer() {
		this.setPreferredSize(new Dimension(30, 20));
		setOpaque(true);
	}

	boolean active = false;

	public void setBackground(Color bg) {
		if (!active) {
			return;
		}
		super.setBackground(bg);
	}

	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		active = true;
		if (isSelected) {
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.WHITE));
		}else{
			this.setBorder(BorderFactory.createEmptyBorder());
		}
		setBackground((Color) value);
		active = false;
		return this;
	}
}