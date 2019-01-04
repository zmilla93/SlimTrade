package com.zrmiller.slimtrade;

import javax.swing.JFrame;

import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.dialog.BasicWindowDialog;
import com.zrmiller.slimtrade.dialog.MenubarDialog;
import com.zrmiller.slimtrade.panels.StashHelperContainer;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class FrameManager {

	
	public static BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer = new StashHelperContainer();
	public static StashGridOverlay stashGridOverlay = new StashGridOverlay();
	
	
	public FrameManager(){
		
		optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		optionWindow.revalidate();
		optionWindow.repaint();
		optionWindow.setVisible(true);
		
//		messageManager = new MessageManager();
		TradeOffer trade = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", 1, 1, "");
//		messageManager.addMessage(trade);
		
		messageManager.addMessage(trade);
		messageManager.setVisible(true);
		
//		stashHelperContainer.setBackground(Color.yellow);
//		stashHelperContainer.setVisible(true);
		
		
		
		
		MenubarDialog menubar = new MenubarDialog();
		menubar.setVisible(true);
		
	}
	
	public static void hideAllFrames(){
		stashGridOverlay.setVisible(false);
	}
	
}
