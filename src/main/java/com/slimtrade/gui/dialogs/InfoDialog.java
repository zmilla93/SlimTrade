package com.slimtrade.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.OLD_AbstractWindowDialog;
import com.slimtrade.gui.panels.BufferPanel;

//TODO : Improve resizing functions and buffer areas
public class InfoDialog extends OLD_AbstractWindowDialog {

	private static final long serialVersionUID = 1L;
	private JLabel infoLabel = new JLabel();
	private JPanel infoPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	private int defaultWidth = 600;
	private int defaultHeight = 100;
	private int height = 30;
	private int bufferHeight = 10;
	private int textHeight=10;

	public InfoDialog(String title) {
		// TODO : Modify Colors
		super(title);
		Graphics g = this.getGraphics();
//		textHeight = g.getFontMetrics().getHeight();
		container.setLayout(new BorderLayout());
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		infoLabel.setAlignmentX(SwingConstants.CENTER);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		container.add(infoPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
		this.addBuffer();
		JButton closeButton = new JButton("Accept");
		buttonPanel.add(closeButton);
		closeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				destroy();
			}
		});
	}

	public void addSectionHeader(String text) {
		JLabel label = new JLabel(text);
		label.setForeground(Color.RED);
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		infoPanel.add(label);
		height += textHeight;
	}

	public void addText(String... text) {
		for (String t : text) {
			JLabel label = new JLabel(t);
			label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			infoPanel.add(label);
			height += textHeight;
		}
	}

	public void addBuffer() {
		infoPanel.add(new BufferPanel(0, 10));
		height += bufferHeight;
	}

	public void finalizeDialog() {
		this.resizeWindow(defaultWidth, height);
		FrameManager.centerFrame(this);
		this.setVisible(true);
	}

	private void destroy() {
		this.dispose();
	}

	public void resizeWindow(int width, int height) {
		this.setSize(width, height + titlebarHeight);
		container.setPreferredSize(new Dimension(width, height));
		titlebarContainer.setPreferredSize(new Dimension(width, titlebarHeight));
		titlebarPanel.setPreferredSize(new Dimension(width, titlebarHeight));
		this.revalidate();
		this.repaint();
	}

}
