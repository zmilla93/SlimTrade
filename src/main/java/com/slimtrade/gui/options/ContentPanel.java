package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.panels.BufferPanel;

public class ContentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int widthBuffer = 20;
	private int heightBuffer = 20;

	public ContentPanel(int width, int height) {
		this.setBackground(Color.YELLOW);
		// this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLayout(new GridBagLayout());
		Dimension size = new Dimension(width, height);
//		 this.setPreferredSize(size);
//		 this.setMinimumSize(new Dimension(width, height));
		// this.setMaximumSize(size);
		this.setVisible(false);
		// this.revalidate();
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
	}

	protected void autoResize() {
		Dimension size = this.getPreferredSize();
		size.width = size.width + widthBuffer;
		// size.width = OptionsWindow.contentWidth;
		size.height = size.height + heightBuffer;
		this.setMaximumSize(size);
	}

	protected void addPair(Component c1, Component c2, GridBagConstraints gc) {
		this.add(c1, gc);
		gc.gridx++;
		this.add(c2, gc);
		gc.gridx = 0;
		gc.gridy++;
	}

	protected void addPair(Component c1, Component c2, int buffer, GridBagConstraints gc) {
		this.add(c1, gc);
		gc.gridx++;
		this.add(new BufferPanel(buffer, 0));
		gc.gridx++;
		this.add(c2, gc);
		gc.gridx = 0;
		gc.gridy++;
	}

	protected void addPair(Component c1, Component c2, int bufferX, int bufferY, GridBagConstraints gc) {
		this.add(c1, gc);
		gc.gridx++;
		this.add(new BufferPanel(bufferX, 0), gc);
		gc.gridx++;
		this.add(c2, gc);
		gc.gridx = 0;
		gc.gridy++;
		this.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;
	}

	protected void addPairPanel(Component c1, Component c2, int bufferX, int bufferY, GridBagConstraints gc) {
		GridBagConstraints gcCenter = new GridBagConstraints();
		JPanel p1 = new JPanel(new GridBagLayout());
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		p1.add(c1, gcCenter);
		this.add(p1, gc);
		gc.gridx++;
		p2.add(new BufferPanel(bufferX, 0));
		this.add(p2, gc);
		gc.gridx++;
		p3.add(c2);
		this.add(p3, gc);
		Dimension p3size = p3.getPreferredSize();
		Dimension p2size = p2.getPreferredSize();
		Dimension p1size = p1.getPreferredSize();
		p2.setPreferredSize(new Dimension(p2size.width, p3size.height));
		p1.setPreferredSize(new Dimension(p1size.width, p3size.height));
		gc.gridx = 0;
		gc.gridy++;
		this.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;
	}

	protected void addBufferY(int buffer, GridBagConstraints gc) {
		gc.gridy++;
		this.add(new BufferPanel(0, buffer), gc);
		gc.gridy++;
	}

}
