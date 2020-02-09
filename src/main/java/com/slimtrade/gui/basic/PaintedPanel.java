package com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ColorModel;

import javax.swing.*;
import javax.swing.border.Border;

public class PaintedPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public Color backgroundDefault = Color.GRAY;
	public Color backgroundHover = Color.LIGHT_GRAY;
	public Color backgroundClick = Color.DARK_GRAY;
	
	public Color textDefault;
	public Color textHover;
	public Color textClick;
	
	public Border borderDefault;
	public Border borderHover;
	public Border borderClick;
	
	private boolean hover;
	private boolean mouseDown;

	protected GridBagConstraints gc = new GridBagConstraints();
	
	JPanel local = this;
	public PaintedPanel(){
		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
				hover = true;
				local.repaint();
			}

			public void mouseExited(MouseEvent e) {
				hover = false;
				local.repaint();
			}

			public void mousePressed(MouseEvent e) {
				mouseDown = true;
				local.repaint();
			}

			public void mouseReleased(MouseEvent e) {
				mouseDown = false;
				local.repaint();
			}

		});
	}
	
    @Override
	public void paint(Graphics g) {
//		System.out.println(textDefault);
		super.paintComponent(g);

		if(mouseDown){
			g.setColor(backgroundClick);
		}else if(hover){
			g.setColor(backgroundHover);
//            this.setBackground(Color.BLUE);
			this.setForeground(textHover);
//			this.setBorder(borderHover);
		}else{
			g.setColor(backgroundDefault);
			this.setForeground(textDefault);
//			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GREEN);
		g.fillRect(1, 1, getWidth()-2, getHeight()-2);
		super.paintChildren(g);

	}
}


