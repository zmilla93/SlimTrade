package main.java.com.slimtrade.dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.util.Objects;
import java.util.Random;

import javax.swing.Box;

import main.java.com.slimtrade.core.ColorManager;
import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.TradeOffer;
import main.java.com.slimtrade.core.TradeUtility;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.panels.MessagePanel;
import main.java.com.slimtrade.panels.StashHelper;


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
//		ColorManager.setMessageTheme();
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
		Random rand = new Random();
		int r = rand.nextInt(255)+1;
		int g = rand.nextInt(255)+1;
		int b = rand.nextInt(255)+1;
		Color color = new Color(r, g, b);
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
//		}});
		this.add(messages[i]);
		this.add(rigidAreas[i]);
		messageCount++;
		refresh();
		FrameManager.forceAllToTop();
	}
	
	private void removeMessage(int i){
		if(messages[i].getMessageType() == MessageType.INCOMING_TRADE){
//			messages[i].stashHelper.highlighterTimer.stop();
			messages[i].stashHelper.itemHighlighter.destroy();
			FrameManager.stashHelperContainer.remove(messages[i].stashHelper);
			FrameManager.stashHelperContainer.refresh();
		}
		this.remove(messages[i]);
		this.remove(rigidAreas[i]);
		messages[i] = null;
		rigidAreas[i] = null;
		messageCount--;
		refresh();
	}
	
	private void refresh(){
		this.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight*messageCount+gap*messageCount);
		this.revalidate();
		this.repaint();
	}
	
	public boolean isDuplicateTrade(TradeOffer trade){
		for(MessagePanel msg : messages){
			if(msg != null){
				if(TradeUtility.isDuplicateTrade(msg.trade, trade)){
					return true;
				}
			}
		}
		return false;
	}
	
}
