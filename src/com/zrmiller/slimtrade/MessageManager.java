package com.zrmiller.slimtrade;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MessageManager extends JPanel{
	
	int buttonCountRow1 = 1;
	int buttonCountRow2 = 1;
	
	//Sizes
	int messageWidth = 400;
	int messageHeight = 40;
	
	int buttonWidth = messageHeight/2;
	int buttonHeight = buttonWidth;
	
	//SET THESE
	double nameWidthPercent = 0.7;
	int nameWidth = (int) (nameWidthPercent*(messageWidth-(buttonWidth*buttonCountRow1)));
	
	//Internal
	int messageCount = 0;
	
	public MessageManager(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setBackground(Color.red);
		refresh();
	}
	
	public void refresh(){
		this.setSize(messageWidth,messageHeight*messageCount);
		this.revalidate();
		this.repaint();
	}
	
	public void addMessage(){
		MessageWindow msg = new MessageWindow();
		add(msg);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {removeMessage(msg);}
		});
		messageCount++;
		refresh();
	}
	
	private void removeMessage(MessageWindow m){
		messageCount--;
		remove(m);
		refresh();
	}
}
