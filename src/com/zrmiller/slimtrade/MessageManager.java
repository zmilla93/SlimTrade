package com.zrmiller.slimtrade;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;

import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.dialog.BasicDialog;
import com.zrmiller.slimtrade.windows.MessagePanel;


//TODO : Could reuse panels instead of creating/destroying constantly, especially rigid areas
public class MessageManager extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	private final int gap = 1;
	private final int maxMessageCount = 20;
	private int messageCount = 0;
	private MessagePanel[] messages = new MessagePanel[maxMessageCount];
	private Component[] rigidAreas = new Component[maxMessageCount];
	
	
	public MessageManager(){
		//TODO : Get default theme, or move setMessageTheme
		ColorManager.setMessageTheme();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(1400, 0, 500, 400);
		this.setBackground(ColorManager.CLEAR);
		this.setVisible(true);
	}
	
	
	//TODO : Clean up stash helper removal
	public void addMessage(TradeOffer trade){
		if(messageCount==maxMessageCount){
			return;
		}
//		File ping = new File("audio/ping.wav");
//		try {
//			AudioManager.playSound(ping);
//		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException | InterruptedException e) {
//			e.printStackTrace();
//		}
		int i = 0;
		while(messages[i]!=null){
			i++;
		}
		messages[i] = new MessagePanel(trade);
		rigidAreas[i] = Box.createRigidArea(new Dimension(MessagePanel.totalWidth, gap));
		int closeIndex = i;
		messages[i].closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	removeMessage(closeIndex);
		    }
		});
		this.add(messages[i]);
		this.add(rigidAreas[i]);
		messageCount++;
		this.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight*messageCount+gap*messageCount);
	}
	
	private void removeMessage(int i){
		if(messages[i].getMessageType() == MessageType.INCOMING_TRADE){
			FrameManager.stashHelperContainer.remove(messages[i].stashHelper);
			FrameManager.stashHelperContainer.refresh();
		}
		this.remove(messages[i]);
		this.remove(rigidAreas[i]);
		this.revalidate();
		this.repaint();
		messages[i] = null;
		rigidAreas[i] = null;
		messageCount--;
	}
	
}
