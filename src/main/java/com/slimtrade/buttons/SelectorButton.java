package main.java.com.slimtrade.buttons;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.ColorManager;
import main.java.com.slimtrade.datatypes.ButtonState;

public class SelectorButton extends JPanel{

	private static final long serialVersionUID = 1L;
	public ButtonState state = ButtonState.INACTIVE;

	public SelectorButton(String name, int width, int height){
		JLabel buttonName = new JLabel(name);
		
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(width, height));
		this.add(buttonName, BorderLayout.CENTER);
		buttonName.setHorizontalAlignment(JLabel.CENTER);
//		this.setBackground(Color.green);
//		this.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.gray));
		updateColor();
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			updateColor(0);
		}});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseExited(java.awt.event.MouseEvent evt) {
			updateColor();
		}});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
			state = ButtonState.ACTIVE;
			updateColor();
		}});
		
//		this.revalidate();
//		this.repaint();
	}
	
	public void updateColor(int ... i){
		if(i.length>0){
			this.setBackground(ColorManager.HistoryWindow.buttonBG_hover);
		}else{
			if(state == ButtonState.ACTIVE){
				this.setBackground(ColorManager.HistoryWindow.buttonBG_active);
				this.setBorder(ColorManager.HistoryWindow.buttonBorder_active);
			}else if (state == ButtonState.INACTIVE){
				this.setBackground(ColorManager.HistoryWindow.buttonBG_inactive);
				this.setBorder(ColorManager.HistoryWindow.buttonBorder_inactive);
			}
		}
		this.revalidate();
		this.repaint();
	}
	
	public void setState(ButtonState state){
		this.state = state;
		updateColor();
	}
	
}
