package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;

public class ToggleButton extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	public boolean active = false;

	public Color inactiveColor = Color.DARK_GRAY;
	public Color activeColor = Color.LIGHT_GRAY;
	public Color hoverColor = Color.gray;
	public Color pressedColor = Color.WHITE;
	
	public Border inactiveBorder = BorderFactory.createRaisedBevelBorder();
	public Border activeBorder = BorderFactory.createLoweredBevelBorder();
	
	public ToggleButton(String text){
		super(text);
		this.setFocusable(false);
		JButton local = this;
		this.setPreferredSize(new Dimension(300, 20));
		
		this.setBackground(inactiveColor);
		this.setBorder(inactiveBorder);
		
		this.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				active = !active;
			}
		});
		
		this.addMouseListener(new AdvancedMouseAdapter(){
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				local.setBackground(hoverColor);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(active){
					local.setBackground(activeColor);
				}else{
					local.setBackground(inactiveColor);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				local.setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				local.setBackground(hoverColor);
				if(active){
					local.setBorder(activeBorder);
				}else{
					local.setBorder(inactiveBorder);
				}
			}});
		
	}

}
