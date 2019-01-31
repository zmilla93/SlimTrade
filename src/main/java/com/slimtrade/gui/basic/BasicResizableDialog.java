package main.java.com.slimtrade.gui.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class BasicResizableDialog extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;
	
	private final JPanel topBorder = new JPanel();
	private final JPanel leftBorder = new JPanel();
	private final JPanel rightBorder = new JPanel();
	private final JPanel bottomBorder = new JPanel();
	private final JPanel centerPanel = new JPanel();
	
	public BasicResizableDialog(){
		
		centerPanel.setBackground(Color.YELLOW);
		topBorder.setBackground(Color.RED);
		leftBorder.setBackground(Color.BLUE);
		rightBorder.setBackground(Color.BLUE);
		bottomBorder.setBackground(Color.RED);
		
		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(centerPanel, BorderLayout.CENTER);
		c.add(topBorder, BorderLayout.PAGE_START);
		c.add(leftBorder, BorderLayout.LINE_START);
		c.add(rightBorder, BorderLayout.LINE_END);
		c.add(bottomBorder, BorderLayout.PAGE_END);
		
	}

	
	public void createResizeListeners(JPanel p){
		p.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		    	if(e.getButton() == MouseEvent.BUTTON2){
		    		offsetX = e.getX();
		    		offsetY = e.getY();
		    		mouseDown = true;
		    	}
		    }
		});
		
		p.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON2){
					mouseDown = false;
				}
			}
		});
	}
	
}
