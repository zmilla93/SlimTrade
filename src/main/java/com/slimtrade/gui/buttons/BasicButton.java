package main.java.com.slimtrade.gui.buttons;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class BasicButton extends JButton {

	private static final long serialVersionUID = 1L;

	public BasicButton() {
		buildButton();
	}

	public BasicButton(String text) {
		super(text);
		buildButton();
	}

	private void buildButton() {
		// this.setBorder(BorderFactory.createLineBorder(Color.RED));
		// Border outsideBorder =
		// BorderFactory.createBevelBorder(BevelBorder.RAISED);
		Border outsideBorder = BorderFactory.createLineBorder(Color.GRAY);
		Border insideBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
		Border border = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
		this.setBorder(border);
		setContentAreaFilled(false);
		setFocusPainted(false);
		// setBorderPainted(false);
		// this.revalidate();
	}

	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new GradientPaint(new Point(0, 0), Color.WHITE, new Point(0, getHeight()), new Color(20, 240, 20)));
		// g2.setPaint(Color.LIGHT_GRAY);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();

		super.paintComponent(g);
	}

}
