package main.java.com.slimtrade.gui.buttons;

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

import main.java.com.slimtrade.core.audio.AudioManager;
import main.java.com.slimtrade.core.audio.Sound;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;

public class IconButton extends JButton {

	private static final long serialVersionUID = -6435841710429512781L;
	private final int DEFAULT_SIZE = 30;
	private final double IMAGE_SCALE = 0.94;

	public Color colorDefault = Color.GRAY;
	public Color colorHover = Color.LIGHT_GRAY;
	public Color colorPressed = Color.WHITE;

	public Border borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	public Border borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	public Border borderPressed = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

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
		image = new ImageIcon(this.getClass().getResource(path)).getImage().getScaledInstance(imageSize, imageSize, Image.SCALE_SMOOTH);
//		this.setPreferredSize(new Dimension(size, size));
		
	}
	
	private void buildButton(Image image, int size){
//		image = ImagePreloader.rad;
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
		//
//		this.addMouseListener(new AdvancedMouseAdapter() {
//			public void click(MouseEvent e){
////				AudioManager.play(SoundComponent.BUTTON_CLICK);
//			}
//		});
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

}
