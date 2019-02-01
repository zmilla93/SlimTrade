package main.java.com.slimtrade.gui.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;

import javax.swing.Box;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.panels.MessagePanel;
import main.java.com.slimtrade.gui.panels.MessagePanel_OLD;


//TODO : Could reuse panels instead of creating/destroying constantly, especially rigid areas
public class MessageManager extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	public static final int buffer = 1;
	private final int maxMessageCount = 20;
	private int messageCount = 0;
	private MessagePanel[] messages = new MessagePanel[maxMessageCount];
	private Component[] rigidAreas = new Component[maxMessageCount];
	
	public MessageManager(){
		//TODO : Get default theme, or move setMessageTheme
//		ColorManager.setMessageTheme();
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(600, 0, 500, 400);
		this.setBackground(ColorManager.CLEAR);
		this.setVisible(true);
	}
	
	
	//TODO : Clean up stash helper removal
	public void addMessage(TradeOffer trade){
		if(messageCount==maxMessageCount){
			return;
		}
		int i = 0;
		while(messages[i]!=null){
			i++;
		}
		messages[i] = new MessagePanel(trade);
		rigidAreas[i] = Box.createRigidArea(new Dimension(MessagePanel_OLD.totalWidth, buffer));
		int closeIndex = i;
		messages[i].getCloseButton().addMouseListener(new AdvancedMouseAdapter() {
		    public void click(MouseEvent e) {
		    	removeMessage(closeIndex);
		    }
		});
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
		this.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight*messageCount+buffer*messageCount);
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
