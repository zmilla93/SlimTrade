package main.java.com.slimtrade.gui.buttons;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class IconButton extends JButton {

	private static final long serialVersionUID = -6435841710429512781L;
	private final int SIZE = 30;
	
//	private MouseClickState clickState;
//	private MouseHoverState hoverState;

	private Color colorDefault = Color.GRAY;
	private Color colorHover = Color.LIGHT_GRAY;
	private Color colorPressed = Color.WHITE;

	private Border borderDefault = BorderFactory.createEmptyBorder();
	private Border borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	private Border borderPressed = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);

	public IconButton(String path) {
		buildButton(path, SIZE);
	}
	
	public IconButton(String path, int size) {
		buildButton(path, size);
	}
	
	private void buildButton(String path, int size){
		this.setPreferredSize(new Dimension(size, size));
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(borderDefault);

		Image img = null;
		try {
			img = ImageIO.read(this.getClass().getResource(path)).getScaledInstance(size, size, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setIcon(new ImageIcon(img));

		final IconButton localButton = this;
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				localButton.getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				localButton.getModel().setPressed(false);
			}
		});
	}

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
		g.fillRect(0, 0, getWidth(), getWidth());
		super.paintComponent(g);
	}

//	public MouseClickState getClickState() {
//		return clickState;
//	}
//
//	public void setClickState(MouseClickState clickState) {
//		this.clickState = clickState;
//	}
//
//	public MouseHoverState getHoverState() {
//		return hoverState;
//	}
//
//	public void setHoverState(MouseHoverState hoverState) {
//		this.hoverState = hoverState;
//	}
	
}
