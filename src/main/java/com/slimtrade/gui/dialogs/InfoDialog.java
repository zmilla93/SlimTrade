package main.java.com.slimtrade.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicWindowDialog;
import main.java.com.slimtrade.gui.buttons.CustomButton_REWORK;
import main.java.com.slimtrade.gui.panels.BufferPanel;

//TODO : Improve resizing functions and buffer areas
public class InfoDialog extends BasicWindowDialog {

	private static final long serialVersionUID = 1L;
	private JLabel infoLabel = new JLabel();
	private JPanel infoPanel = new JPanel();
	private JPanel buttonPanel = new JPanel();

	private int defaultWidth = 600;
	private int defaultHeight = 100;
	private int height = 30;
	private int bufferHeight = 10;
	private int textHeight;

	public InfoDialog(String title) {
		// TODO : Modify Colors
		super(title);
		Graphics g = this.getGraphics();
		textHeight = g.getFontMetrics().getHeight();
		container.setLayout(new BorderLayout());
		titlebarPanel.setPreferredSize(new Dimension(defaultWidth, titlebarHeight));
		infoLabel.setAlignmentX(SwingConstants.CENTER);
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		container.add(infoPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
		this.addBuffer();
		CustomButton_REWORK closeButton = new CustomButton_REWORK("Accept");
		buttonPanel.add(closeButton);
		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
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
