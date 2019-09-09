package com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;

public class ListButton extends JButton implements ColorUpdateListener {

	private static final long serialVersionUID = 1L;

	public boolean active = false;

	public Color colorInactive = ColorManager.LOW_CONSTRAST;
	public Color colorActive = ColorManager.PRIMARY;
	public Color colorPressed = ColorManager.BACKGROUND;

	private Border borderOuter = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST);
	private Border borderInner = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
	private Border b = BorderFactory.createCompoundBorder(borderOuter, borderInner);

	private Border borderOuter2 = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
	private Border borderInner2 = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST);
	private Border borderDefault = BorderFactory.createCompoundBorder(borderOuter2, borderInner2);

	public Border borderHover = b;
	public Border borderPressed = b;

	public ListButton(String title) {
		super(title);
//		borderOuter = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
//		borderInner = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST);
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
		
		if(getModel().isPressed()) {
			this.setBorder(BorderFactory.createLoweredBevelBorder());
			g.setColor(colorPressed);
		}else{
			if (active) {
				g.setColor(colorActive);
				this.setBorder(borderDefault);
			}else{
				g.setColor(colorInactive);
			}
		}
		if (getModel().isRollover()) {
			this.setBorder(borderHover);
		}else{
			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	@Override
	public void updateColor() {
		borderOuter = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST);
		borderInner = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
		b = BorderFactory.createCompoundBorder(borderOuter, borderInner);

		borderOuter2 = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
		borderInner2 = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST);
		borderDefault = BorderFactory.createCompoundBorder(borderOuter2, borderInner2);
		
		borderHover = b;
		borderPressed = b;
	}

}
