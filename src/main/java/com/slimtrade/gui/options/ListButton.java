package com.slimtrade.gui.options;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.Border;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;

public class ListButton extends JButton implements IColorable {

	private static final long serialVersionUID = 1L;

	public boolean active = false;

	public Color colorInactive = ColorManager.LOW_CONTRAST_1;
	public Color colorActive = ColorManager.PRIMARY;
	public Color colorPressed = ColorManager.BACKGROUND;

	private Border borderOuter = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);
	private Border borderInner = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
	private Border b = BorderFactory.createCompoundBorder(borderOuter, borderInner);

	private Border borderOuter2 = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
	private Border borderInner2 = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);
	private Border borderDefault = BorderFactory.createCompoundBorder(borderOuter2, borderInner2);

	public Border borderHover = b;
	public Border borderPressed = b;

	public ListButton(String title) {
		super(title);
		this.setForeground(ColorManager.TEXT);
//		borderOuter = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
//		borderInner = BorderFactory.createLineBorder(ColorManager.LOW_CONSTRAST_1);
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

	public static void link(JPanel panel, ListButton button){
//        OptionsWindow local = this;
        button.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                ListButton lb;
                for (Component c : panel.getComponents()) {
                    if(c instanceof ListButton){
                        lb = (ListButton) c;
                        lb.active = false;
                    }
                }
                button.active = true;
                panel.repaint();
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
		borderOuter = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);
		borderInner = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
		b = BorderFactory.createCompoundBorder(borderOuter, borderInner);

		borderOuter2 = BorderFactory.createLineBorder(ColorManager.HIGH_CONTRAST_1);
		borderInner2 = BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_1);
		borderDefault = BorderFactory.createCompoundBorder(borderOuter2, borderInner2);
		
		borderHover = b;
		borderPressed = b;
	}

}
