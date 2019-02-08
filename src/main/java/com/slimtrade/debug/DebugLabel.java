package main.java.com.slimtrade.debug;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class DebugLabel extends JPanel{

	public DebugLabel(String s1, String s2){
		JLabel labelLeft = new JLabel(s1);
		labelLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		this.add(labelLeft);
		
		JLabel labelRight = new JLabel(s2);
		labelRight.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(labelRight);
	}
	
}
