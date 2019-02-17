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
	public Color colorDefault = Color.GRAY;
	public Color colorHover = Color.LIGHT_GRAY;
	public Color colorClick = Color.DARK_GRAY;
	
//	private Border borderDefault = BorderFactory.createEmptyBorder(1,1,1,1);
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
		super.paintComponent(g);
		if(mouseDown){
			g.setColor(colorClick);
			this.setBorder(borderClick);
		}else if(hover){
			g.setColor(colorHover);
			this.setBorder(borderHover);
		}else{
			g.setColor(colorDefault);
			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getWidth());
		
	}
}


