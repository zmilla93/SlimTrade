package com.slimtrade.gui.panels;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public class IconPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final int SIZE = 20;
	private final double IMAGE_SCALE = 1;

	//TODO : Remove path constructors
	public IconPanel(String path) {
		Image image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
		buildIcon(image, SIZE, null);
	}
	
	public IconPanel(String path, int size) {
		Image image = new ImageIcon(this.getClass().getClassLoader().getResource(path)).getImage().getScaledInstance(SIZE, SIZE, Image.SCALE_SMOOTH);
		buildIcon(image, size, null);
	}
	
	public IconPanel(Image image) {
		buildIcon(image, SIZE, null);
	}

	public IconPanel(Image image, int size) {
		buildIcon(image, size, null);
	}

	public IconPanel(Image image, Color borderColor) {
		buildIcon(image, SIZE, borderColor);
	}

	public IconPanel(Image image, int size, Color borderColor) {
		buildIcon(image, size, borderColor);
	}

	private void buildIcon(Image image, int size, Color borderColor) {
//		App.logger.log(Level.INFO, path);
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(size, size));
		this.setBackground(ColorManager.CLEAR);
		if (borderColor == null) {
			this.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		} else {
			this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, borderColor));
		}
		int imageSize = (int)(size*IMAGE_SCALE);
//		Image image = new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(new ImageIcon(image));
		this.add(imgLabel, BorderLayout.CENTER);
	}

}
