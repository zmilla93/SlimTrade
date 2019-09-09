package com.slimtrade.gui.buttons;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.border.Border;

import com.slimtrade.Main;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.ColorUpdateListener;

public class BasicButton extends JButton implements ColorUpdateListener {

	private static final long serialVersionUID = 1L;
	
	private final ButtonModel model;
	
	private static Border borderDefault;
	private static Border borderRollover;

	protected Color primaryColor;
	protected Color secondaryColor;
	
	public BasicButton() {
		this.model = this.getModel();
		buildButton();
	}

	public BasicButton(String text) {
		super(text);
		this.model = this.getModel();
		buildButton();
	}
	
	public void setColor(Color color){
		this.primaryColor = color;
	}

	//TODO : Check mouse button?
	//TODO : Currently paints twice per action...
	private void buildButton() {
		Border outsideBorder = BorderFactory.createLineBorder(Color.GRAY);
		Border outsideBorderRollover = BorderFactory.createLineBorder(Color.BLACK);
		Border insideBorder = BorderFactory.createEmptyBorder(5, 15, 5, 15);
		borderDefault = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
		borderRollover = BorderFactory.createCompoundBorder(outsideBorderRollover, insideBorder);
		this.setBorder(borderDefault);
		setContentAreaFilled(false);
		setFocusPainted(false);
		this.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
				model.setRollover(true);
			}
			public void mouseExited(MouseEvent e) {
				model.setRollover(false);
			}
			public void mousePressed(MouseEvent e) {
				model.setPressed(true);
			}
			public void mouseReleased(MouseEvent e) {
				model.setPressed(false);
			}
		});
		
		updateColor();
		Main.eventManager.addListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		final Graphics2D g2 = (Graphics2D) g.create();
		ButtonModel model = getModel();
		//BORDER
		if(model.isRollover()){
			this.setBorder(borderRollover);
		}else{
			this.setBorder(borderDefault);
		}
		//FILL
		if(model.isPressed() && model.isRollover()){
			g2.setPaint(primaryColor);
		}else if(model.isPressed() && !model.isRollover()){
			g2.setPaint(Color.LIGHT_GRAY);
		}else{
			g2.setPaint(new GradientPaint(new Point(0, 0), secondaryColor, new Point(0, getHeight()), primaryColor));
		}
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
		super.paintComponent(g);
	}

	@Override
	public void updateColor() {
		primaryColor = ColorManager.PRIMARY;
		secondaryColor = ColorManager.BACKGROUND;
	}

}
