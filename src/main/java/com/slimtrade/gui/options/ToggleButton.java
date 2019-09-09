package com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.slimtrade.core.observing.AdvancedMouseAdapter;

public class ToggleButton extends JButton {

	private static final long serialVersionUID = 1L;

	public boolean active = false;

	public Color colorInactive = Color.GRAY;
	public Color colorActive = Color.LIGHT_GRAY;
	public Color colorHover = Color.DARK_GRAY;
	public Color colorPressed = Color.WHITE;

	public Border borderInactive = BorderFactory.createRaisedBevelBorder();
	public Border borderActive = BorderFactory.createLoweredBevelBorder();

	public ToggleButton(String text) {
		super(text);
		buildButton(text, false);
	}

	public ToggleButton(String text, boolean pressed) {
		super(text);
		buildButton(text, pressed);
	}

	private void buildButton(String text, boolean pressed) {
		this.active = pressed;
		
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		JButton local = this;
		this.setPreferredSize(new Dimension(300, 20));

		this.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				local.getModel().setRollover(false);
				active = !active;
			}
		});

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				local.getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				local.getModel().setPressed(false);
			}
		});

		// this.addMouseListener(new AdvancedMouseAdapter(){
		//
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// }
		//
		// @Override
		// public void mouseEntered(MouseEvent e) {
		// local.setBackground(colorHover);
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// if(active){
		// local.setBackground(colorActive);
		// }else{
		// local.setBackground(colorInactive);
		// }
		// }
		//
		// @Override
		// public void mousePressed(MouseEvent e) {
		// local.setBackground(colorPressed);
		// }
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		// local.setBackground(colorHover);
		// if(active){
		// local.setBorder(borderActive);
		// }else{
		// local.setBorder(borderInactive);
		// }
		// }});

	}

	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(colorPressed);
			// this.setBorder(borderPressed);
		} else if (getModel().isRollover()) {
			g.setColor(colorHover);
			// this.setBorder(borderHover);
		} else {
			if (active) {
				g.setColor(colorActive);
				this.setBorder(borderActive);
			} else {
				g.setColor(colorInactive);
				this.setBorder(borderInactive);
			}
		}

		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

}
