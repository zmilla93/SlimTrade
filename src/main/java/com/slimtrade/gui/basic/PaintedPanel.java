package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PaintedPanel extends JPanel {

	public static final int BORDER_SIZE = 1;
	
	public Color backgroundDefault = ColorManager.LOW_CONTRAST_1;
	public Color backgroundHover;
	public Color backgroundClick;

	public Color textDefault = ColorManager.TEXT;
	public Color textHover;
	public Color textClick;

	public Color borderDefault;
	public Color borderHover;
	public Color borderClick;
	
	private boolean hover;
	private boolean mouseDown;

	protected JLabel label = new CustomLabel();

	public PaintedPanel(){
		backgroundDefault = ColorManager.LOW_CONTRAST_1;
		backgroundHover = backgroundDefault;
		backgroundClick = backgroundDefault;

		borderDefault = backgroundDefault;
		borderHover = backgroundDefault;
		borderClick = backgroundDefault;

		textDefault = ColorManager.TEXT;
		textHover = textDefault;
		textClick = textDefault;

		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
				hover = true;
				repaint();
			}
			public void mouseExited(MouseEvent e) {
				hover = false;
				repaint();
			}
			public void mousePressed(MouseEvent e) {
				mouseDown = true;
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				mouseDown = false;
				repaint();
			}
		});
		this.add(label);
//		App.eventManager.addColorListener(this);
//		this.updateColor();
	}
	
    @Override
	public void paintComponent(Graphics g) {
		Color bg;
		Color border;
		Color text;
		if(mouseDown){
			bg = backgroundClick;
			border = borderClick;
			text = textDefault;
		}else if(hover){
			bg = backgroundHover;
			border = borderHover;
			text = textDefault;
		}else{
			bg = backgroundDefault;
			border = borderDefault;
			text = textDefault;
		}
		g.setColor(border);
		g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(bg);
		g.fillRect(1, 1, getWidth()-2, getHeight()-2);
		label.setForeground(text);
	}

//	@Override
//	public void updateColor() {
//
//	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public JLabel getLabel() {
		return this.label;
	}

	public void setBackgroundColor(Color color) {
		backgroundDefault = color;
		backgroundClick = color;
		backgroundHover = color;
	}

	public void setBorderColor(Color color) {
		borderDefault = color;
		borderClick = color;
		borderHover = color;
	}

	public void setTextColor(Color color) {
		textDefault = color;
		textClick = color;
		textHover = color;
	}

}


