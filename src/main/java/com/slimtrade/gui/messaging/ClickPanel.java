package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ClickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Color backgroudDefault;
	public Color backgroundHover;
	
	public Color textDefault;
	public Color textHover;

	public Border borderDefault;
	public Border borderHover;
	
	public JLabel label = new JLabel();

	
	//TODO : Add label?
	public ClickPanel() {
//		this.setLayout(new GridBagLayout());
//		GridBagConstraints gc = new GridBagConstraints();
//		this.add(label, gc);
		JPanel p = this;
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				p.setBackground(backgroundHover);
				p.setForeground(textHover);
				p.setBorder(borderHover);
			}

			public void mouseExited(MouseEvent e) {
				p.setBackground(backgroudDefault);
				p.setForeground(textDefault);
				p.setBorder(borderDefault);
			}
		});
	}
	
	public void setText(String text){
		label.setText(text);
	}
}
