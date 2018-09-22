package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageWindow extends JPanel{

	REF_MSG_WINDOW ref = new REF_MSG_WINDOW();
	
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	JPanel namePanel = new JPanel();
	JPanel itemPanel = new JPanel();
	JPanel offerPanel = new JPanel();
	
	JButton expandButton = new JButton();
	JButton closeButton = new JButton();
	
	public MessageWindow(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(ref.totalWidth, ref.totalHeight));
		//this.setSize(ref.totalWidth, ref.totalHeight);
		
		topPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		bottomPanel.setSize(ref.totalWidth, ref.totalHeight/2);
		topPanel.setBackground(Color.yellow);
		bottomPanel.setBackground(Color.green);
		
		//namePanel.setSize((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight);
		namePanel.setMaximumSize(new Dimension((int) (ref.nameWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		namePanel.setBackground(Color.blue);
		topPanel.add(namePanel);
		offerPanel.setMaximumSize(new Dimension((int) (ref.offerWidthPercent*(ref.msgWidth-(ref.buttonWidth*ref.buttonCountRow1))), ref.nameHeight));
		offerPanel.setBackground(Color.green);
		topPanel.add(offerPanel);
		
		expandButton.setPreferredSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		expandButton.setMaximumSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		closeButton.setPreferredSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		closeButton.setMaximumSize(new Dimension(ref.buttonWidth, ref.buttonHeight));
		topPanel.add(expandButton);
		topPanel.add(closeButton);
		
		itemPanel.setPreferredSize(new Dimension(ref.itemWidth, ref.itemHeight));
		itemPanel.setMaximumSize(new Dimension(ref.itemWidth, ref.itemHeight));
		itemPanel.setBackground(Color.orange);
		bottomPanel.add(itemPanel);
		
		this.add(topPanel);
		this.add(bottomPanel);
		
		//this.setLayout();
	}
	
}
