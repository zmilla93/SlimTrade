package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.windows.MessageWindow;

public class MessageManager extends JPanel{

	private static final long serialVersionUID = 1L;
	private int msgGapSize = 1;
	Robot robot;
	
	public MessageManager(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(1230, 2, 0, 0);
		this.setBackground(ColorManager.CLEAR);
//		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, null, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
//		this.addMessage(new TradeOffer(MessageType.OUTGOING_TRADE, null, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
//		this.setVisible(true);
	}
	
	private void refresh(){
		Dimension d = new Dimension(MessageWindow.totalWidth, MessageWindow.totalHeight*(this.getComponentCount()/2) + msgGapSize*(this.getComponentCount()/2));
		this.setPreferredSize(d);
		this.setSize(d);
		this.revalidate();
		this.repaint();
	}
	
	//TODO : This may be causing a memory leak...
	//Should move messages to another spot in memory and limit how much can be used
	public void addMessage(TradeOffer trade){
		File ping = new File("audio/ping.wav");
		try {
			AudioManager.playSound(ping);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
			e.printStackTrace();
		}
		MessageWindow msg = new MessageWindow(trade);
		BasicPanel buffer = new BasicPanel(MessageWindow.totalWidth, msgGapSize, ColorManager.CLEAR);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	if(msg.getMessageType() == MessageType.INCOMING_TRADE){
		    		
		    		//Hide
	//		    	msg.stashHelper.itemHighlighter.setVisible(false);
	//		    	msg.stashHelper.setVisible(false);
			    	//Remove
		    		Overlay.messageContainer.remove(msg.stashHelper.itemHighlighter);
			    	Overlay.stashHelperContainer.remove(msg.stashHelper);
			    	//Refresh
			    	Overlay.stashHelperContainer.revalidate();
			    	Overlay.stashHelperContainer.repaint();
			    	Overlay.messageContainer.revalidate();
			    	Overlay.messageContainer.repaint();
		    	}
		    	
		    	
		    	//Focus
		    	remove(msg);
		    	remove(buffer);
		    	refresh();
		    	PoeInterface.focus();
		    	
		    	
		    }
		});
		this.add(msg);
		this.add(buffer);
		refresh();
	}
	
}
