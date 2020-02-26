package com.slimtrade.gui.buttons;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class IconButton extends JButton implements IColorable {

	private final int DEFAULT_SIZE = 30;
	private final double IMAGE_SCALE = 0.94;

//	public Color colorDefault = Color.GRAY;
	public Color colorDefault = ColorManager.LOW_CONTRAST_1;
	public Color colorHover = ColorManager.PRIMARY;
	public Color colorPressed = ColorManager.BACKGROUND;

	public Border borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	public Border borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	public Border borderPressed = borderHover;

	private Image image;
	
	public IconButton(String path) {
		getNewImage(path, DEFAULT_SIZE);
		buildButton(image, DEFAULT_SIZE);
	}

	public IconButton(String path, int size) {
		getNewImage(path, size);
		buildButton(image, size);
	}
	
	public IconButton(Image image, int size){
		buildButton(image, size);
	}

	private void getNewImage(String path, int size) {
//		System.out.println("NEW IMAGE");
		int imageSize = (int)(size*IMAGE_SCALE);
//        System.out.println("PATH : " + path);
        ClassLoader loader = getClass().getClassLoader();
		image = new ImageIcon(loader.getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
//		this.setPreferredSize(new Dimension(size, size));
		
	}
	
	private void buildButton(Image image, int size){
		this.setPreferredSize(new Dimension(size, size));
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(borderDefault);
		this.setIcon(new ImageIcon(image));
		final IconButton localButton = this;
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				localButton.getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				localButton.getModel().setPressed(false);
			}
		});
	}

	//TODO : Switch to normal background stuff??
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(colorPressed);
			this.setBorder(borderPressed);
		} else if (getModel().isRollover()) {
			g.setColor(colorHover);
			this.setBorder(borderHover);
		} else {
			g.setColor(colorDefault);
			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}


    @Override
    public void updateColor() {
        colorDefault = ColorManager.LOW_CONTRAST_1;
        colorHover = ColorManager.LOW_CONTRAST_2;
        colorPressed = ColorManager.HIGH_CONTRAST_1;
    }
}
