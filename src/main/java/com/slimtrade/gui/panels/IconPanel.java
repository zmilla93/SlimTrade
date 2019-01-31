package main.java.com.slimtrade.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.managers.ColorManager;

public class IconPanel extends JPanel {

	private static final long serialVersionUID = -5297536255112243190L;
	private final int SIZE = 30;
	private final double IMAGE_SCALE = 0.9;

	public IconPanel(String path) {
		buildIcon(path, SIZE, null);
	}

	public IconPanel(String path, int size) {
		buildIcon(path, size, null);
	}

	public IconPanel(String path, Color borderColor) {
		buildIcon(path, SIZE, borderColor);
	}

	public IconPanel(String path, int size, Color borderColor) {
		buildIcon(path, size, borderColor);
	}

	private void buildIcon(String path, int size, Color borderColor) {
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(size, size));
		this.setBackground(ColorManager.CLEAR);
		if (borderColor == null) {
			this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		} else {
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor));
		}
		int imageSize = (int)(size*IMAGE_SCALE);
		Image image = new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(image));
		this.add(imgLabel, BorderLayout.CENTER);
	}

}
