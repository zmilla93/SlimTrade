package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class AbstractClickPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	protected Color bgDefault;
	protected Color bgHover;
	
	protected Color textDefault;
	protected Color textHover;

	protected Border borderDefault;
	protected Border borderHover;
	
	protected JLabel label = new JLabel();

	
	//TODO : Add label?
	public AbstractClickPanel() {
//		this.setLayout(new GridBagLayout());
//		GridBagConstraints gc = new GridBagConstraints();
//		this.add(label, gc);
		JPanel p = this;
		this.addMouseListener(new MouseAdapter() {
			
			public void mouseEntered(MouseEvent e) {
				p.setBackground(bgHover);
				p.setForeground(textHover);
				p.setBorder(borderHover);
			}

			public void mouseExited(MouseEvent e) {
				p.setBackground(bgDefault);
				p.setForeground(textDefault);
				p.setBorder(borderDefault);
			}
		});
	}
	
	public void setText(String text){
		label.setText(text);
	}
}
