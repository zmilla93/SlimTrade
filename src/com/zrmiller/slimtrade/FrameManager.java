package com.zrmiller.slimtrade;

import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.dialog.BasicWindowDialog;
import com.zrmiller.slimtrade.dialog.MenubarDialog;
import com.zrmiller.slimtrade.dialog.StashHelperContainer;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class FrameManager {

	
	public static BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	public static MenubarDialog menubar = new MenubarDialog();
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer = new StashHelperContainer();
	public static StashGridOverlay stashOverlay = new StashGridOverlay();
	public static CharacterWindow characterWindow = new CharacterWindow();
	
	
	public FrameManager(){
		
		TradeOffer trade1 = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls#1", "ITEM_NAME", 7.5, "chaos", 3.5, "STASH_TAB", 1, 1, "");
		TradeOffer trade2 = new TradeOffer(MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls#2", "ITEM_NAME", 2.0, "chaos", 750.0, "STASH_TAB", 1, 1, "");
		
		messageManager.addMessage(trade1);
		messageManager.addMessage(trade2);
		messageManager.setVisible(true);
		
//		menubar = new MenubarDialog();
		menubar.setVisible(true);
		
		ChatParser parser = new ChatParser();
		parser.init();
		
//		stashOverlay.setVisible(true);
//		stashHelperContainer.setBackground(Color.yellow);
//		stashHelperContainer.setVisible(true);
		
	}
	
	public static void hideAllFrames(){
		stashOverlay.setVisible(false);
	}
	
}
