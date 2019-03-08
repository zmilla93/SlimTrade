package main.java.com.slimtrade.gui.basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
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
			public void mouseClicked(MouseEvent arg0) {
			}
			
			public void mouseEntered(MouseEvent arg0) {
				hover = true;
				local.repaint();
			}
			
			public void mouseExited(MouseEvent arg0) {
				hover = false;
				local.repaint();
			}
			
			public void mousePressed(MouseEvent arg0) {
				mouseDown = true;
				local.repaint();
			}
			
			public void mouseReleased(MouseEvent arg0) {
				mouseDown = false;
				local.repaint();
			}
			
		});
	}
	

	protected void paintComponent(Graphics g) {
//		System.out.println(textDefault);
		super.paintComponent(g);
		if(mouseDown){
			g.setColor(backgroundClick);
			this.setForeground(textClick);
			this.setBorder(borderClick);
		}else if(hover){
			g.setColor(backgroundHover);
			this.setForeground(textHover);
			this.setBorder(borderHover);
		}else{
			g.setColor(backgroundDefault);
			this.setForeground(textDefault);
			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getWidth());
		
	}
}


