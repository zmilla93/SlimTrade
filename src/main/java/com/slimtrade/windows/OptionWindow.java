package main.java.com.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.TradeUtility;
import main.java.com.slimtrade.panels.BasicPanel;

public class OptionWindow extends BasicMenuWindow{
	
	private static final long serialVersionUID = 1L;
	public static int width = 400;
	public static int minHeight = 800;
	public static int rowHeight = 25;
	private JButton closeButton;
	
	//TODO : cleanup size variables for better resizing
	
	public OptionWindow(){
		super("Options", width, minHeight);
		//TODO : FIX CENTERING
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));;
		this.setBounds(TradeUtility.screenSize.width/2-width/2, TradeUtility.screenSize.height/2-minHeight/2, width, minHeight);
		this.setMinimumSize(new Dimension(width, minHeight));
		this.setVisible(false);
		
		JLabel lol = new JLabel("LOL YOU THOUGHT THERE WOULD BE OPTIONS?");
		container.add(lol);
//		BasicTitlebar titlebar = new BasicTitlebar("Options", this,  width);
//		this.add(titlebar);
		this.add(new BasicPanel(width,10));
		
		//TOGGLES
		JPanel toggleHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		JLabel toggleLabel = new JLabel("Toggle");
		Border toggleBorder = BorderFactory.createRaisedSoftBevelBorder();
		toggleHeader.setPreferredSize(new Dimension((int)(width), rowHeight));
		toggleHeader.setBorder(toggleBorder);
		toggleHeader.add(toggleLabel);
		this.add(toggleHeader);
		
		int toggleCount = 2;
		double toggleWidth = 0.6;
		
		JPanel toggleFold= new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		toggleFold.setPreferredSize(new Dimension(width, (int)(rowHeight*toggleCount)));
		//toggleFold.setMinimumSize(new Dimension(width, (int)(rowHeight*toggleCount)));
		toggleFold.setBackground(Color.red);
		this.add(toggleFold);
		
		JPanel dndContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		dndContainer.setPreferredSize(new Dimension((int)(width*toggleWidth), rowHeight));
		dndContainer.setBackground(Color.green);
		toggleFold.add(dndContainer);
		
		JPanel saveOutgoing = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		saveOutgoing.setPreferredSize(new Dimension((int)(width*toggleWidth), rowHeight));
		saveOutgoing.setBackground(Color.yellow);
		toggleFold.add(saveOutgoing);
		
		//TOGGLE BUTTONS
		toggleHeader.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(toggleFold.isVisible()){
		    		toggleFold.setVisible(false);
		    	}else{
		    		toggleFold.setVisible(true);
		    	}
		    }
		});
		
		//this.add(toggleFold);
		//toggleFold.setPreferredSize(preferredSize);
		
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
