package com.zrmiller.slimtrade;

import java.awt.Dimension;
import java.awt.Point;

import com.zrmiller.slimtrade.buttons.MenubarToggleButton;
import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.dialog.BasicWindowDialog;
import com.zrmiller.slimtrade.dialog.ItemHighlighter;
import com.zrmiller.slimtrade.dialog.MenubarDialog;
import com.zrmiller.slimtrade.dialog.StashHelperContainer;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.HistoryWindow;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class FrameManager {

//	public static Debug debug = new Debug();
	
	public static BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	public static HistoryWindow historyWindow = new HistoryWindow("History");	
	public static MenubarDialog menubar = new MenubarDialog();
	public static MenubarToggleButton menubarToggle = new MenubarToggleButton();
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer;
	public static StashGridOverlay stashOverlay;
	public static CharacterWindow characterWindow = new CharacterWindow();
	
	public FrameManager(){
		
		stashHelperContainer = new StashHelperContainer();

		if(Main.fileManager.hasStashData){
			int[] stashData = Main.fileManager.getStashData();
			stashOverlay = new StashGridOverlay(new Point(stashData[0], stashData[1]), stashData[2], stashData[3]);
			StashGridOverlay.setDefaultGridPos(new Point(stashData[4], stashData[5]));
			StashGridOverlay.setDefaultGridSize(new Dimension(stashData[6], stashData[7]));
			ItemHighlighter.saveGridInfo(stashData[4], stashData[5], (double)(stashData[6]/12.0), (double)(stashData[7]/12.0));
			stashHelperContainer.updateBounds();
		}else{
			stashOverlay = new StashGridOverlay(new Point(0,0), 400, 400);
			stashHelperContainer.updateBounds(0, 0, 0);
		}
		
		if(Main.fileManager.hasCharacterData){
			String[] chracterData = new String[2];
			chracterData = Main.fileManager.getCharacterData();
			characterWindow.setCharacter(chracterData[0], chracterData[1]);
		}

		TradeOffer trade1 = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls#1", "ITEM_NAME", 7.5, "chaos", 3.5, "STASH_TAB", 1, 1, "");
		TradeOffer trade2 = new TradeOffer(MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls#2", "ITEM_NAME", 2.0, "chaos", 750.0, "STASH_TAB", 1, 1, "");

		menubar.setVisible(true);
		
		
		ChatParser parser = new ChatParser();
		parser.init();
		
//		stashOverlay.setVisible(true);
//		stashHelperContainer.setBackground(Color.yellow);
//		stashHelperContainer.setVisible(true);
		
	}
	
	public static void hideAllFrames(){
		characterWindow.setVisible(false);
		historyWindow.setVisible(false);
		optionWindow.setVisible(false);
		stashOverlay.setVisible(false);
	}
	
	public static void forceAllToTop(){
		if(menubarToggle.isVisible()){
			
		}
		if(menubar.isVisible()){
			
		}
		menubarToggle.forceToTop();
		menubar.forceToTop();
		messageManager.forceToTop();
		stashHelperContainer.forceToTop();
		PoeInterface.focus();
	}
	
}
