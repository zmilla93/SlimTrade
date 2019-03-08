package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class ListButton extends JButton {

	private static final long serialVersionUID = 1L;

	public boolean active = false;

	public Color colorInactive = new Color(150, 150, 150);
//	public Color colorActive = Color.red;
	public Color colorActive = new Color(220, 220, 220);
//	public Color colorHover = new Color(220, 220, 220);
//	public Color colorPressed = Color.WHITE;

	private Border borderOuter = BorderFactory.createLineBorder(Color.black);
	private Border borderInner = BorderFactory.createLineBorder(Color.gray);
	private Border b = BorderFactory.createCompoundBorder(borderOuter, borderInner);

	private Border borderOuter2 = BorderFactory.createLineBorder(Color.gray);
	private Border borderInner2 = BorderFactory.createLineBorder(Color.LIGHT_GRAY);
	private Border b2 = BorderFactory.createCompoundBorder(borderOuter2, borderInner2);

	public Border borderDefault = b2;
//	public Border borderInactive = b;
//	public Border borderActive = b;
	public Border borderHover = b;
	public Border borderPressed = b;

	public ListButton(String title) {
		super(title);

		borderOuter = BorderFactory.createLineBorder(Color.black);
		borderInner = BorderFactory.createLineBorder(Color.gray);
//		borderInactive = BorderFactory.createCompoundBorder(borderOuter, borderInner);

		this.setContentAreaFilled(false);
		this.setFocusable(false);
		Dimension size = this.getPreferredSize();
		size.height = 30;
		this.setPreferredSize(size);
		this.setBackground(colorInactive);
//		this.setBorder(borderInactive);

		JButton local = this;

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				local.getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				local.getModel().setPressed(false);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
//			g.setColor(colorPressed);
			this.setBorder(borderPressed);
		} else if (getModel().isRollover()) {
//			g.setColor(colorHover);
			this.setBorder(borderHover);
		} else {
			this.setBorder(borderDefault);
		}
		if (active) {
			g.setColor(colorActive);
//			this.setBorder(borderActive);
		} else {
			g.setColor(colorInactive);
//			this.setBorder(borderInactive);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

}
