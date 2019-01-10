package com.zrmiller.slimtrade;

import java.awt.Dimension;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import com.zrmiller.slimtrade.buttons.MenubarToggleButton;
import com.zrmiller.slimtrade.datatypes.MessageType;
import com.zrmiller.slimtrade.debug.Debug;
import com.zrmiller.slimtrade.dialog.BasicWindowDialog;
import com.zrmiller.slimtrade.dialog.ItemHighlighter;
import com.zrmiller.slimtrade.dialog.MenubarDialog;
import com.zrmiller.slimtrade.dialog.StashHelperContainer;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class FrameManager {

	public static Debug debug = new Debug();
	
	public static BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	public static BasicWindowDialog historyWindow = new BasicWindowDialog("History");	
	public static MenubarDialog menubar = new MenubarDialog();
	public static MenubarToggleButton menubarToggle = new MenubarToggleButton();
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer;
	public static StashGridOverlay stashOverlay;
	public static CharacterWindow characterWindow = new CharacterWindow();
	
	public static ExternalFileManager fileManager = new ExternalFileManager();
	
	
	
	
	public FrameManager(){
		
		stashHelperContainer = new StashHelperContainer();
	
		
		//TODO : Revisit saving/loading once 
		if(fileManager.hasStashData){
			int[] stashData = fileManager.getStashData();
			stashOverlay = new StashGridOverlay(new Point(stashData[0], stashData[1]), stashData[2], stashData[3]);
			StashGridOverlay.setDefaultGridPos(new Point(stashData[4], stashData[5]));
			StashGridOverlay.setDefaultGridSize(new Dimension(stashData[6], stashData[7]));
//			stashOverlay.setDefaultGrid
			ItemHighlighter.saveGridInfo(stashData[4], stashData[5], (double)(stashData[6]/12.0), (double)(stashData[7]/12.0));
			stashHelperContainer.updateBounds();
		}else{
			stashOverlay = new StashGridOverlay(new Point(0,0), 400, 400);
			stashHelperContainer.updateBounds(0, 0, 0);
		}
		
		
		
//		stashHelperContainer.setVisible(true);
		
//		ItemHighlighter.saveGridInfo(100, 100, 50, 50);
		
//		stashHelperContainer.updateBounds();
//		StashHelper.updateCellSize(StashGridOverlay.gridWidth, StashGridOverlay.gridHeight);
		
		if(fileManager.hasCharacterData){
			String[] chracterData = new String[2];
			chracterData = fileManager.getCharacterData();
			characterWindow.setCharacter(chracterData[0], chracterData[1]);
		}
		
//		File charFile = new File("char.pref");
//		if(charFile.exists()){
//			System.out.println("Loading character...");
//			try {
//				ObjectInputStream charInput = new ObjectInputStream(new FileInputStream("char.pref"));
//				String character = (String)charInput.readObject();
//				String league = (String)charInput.readObject();
//				characterWindow.setCharacter(character, league);
//				charInput.close();
//			} catch (IOException e2) {
//				e2.printStackTrace();
//			} catch (ClassNotFoundException e1) {
//				e1.printStackTrace();
//			}
//		}
		
		TradeOffer trade1 = new TradeOffer(MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls#1", "ITEM_NAME", 7.5, "chaos", 3.5, "STASH_TAB", 1, 1, "");
		TradeOffer trade2 = new TradeOffer(MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls#2", "ITEM_NAME", 2.0, "chaos", 750.0, "STASH_TAB", 1, 1, "");
		
//		messageManager.addMessage(trade1);
//		messageManager.addMessage(trade2);
//		messageManager.setVisible(true);
		
//		menubar = new MenubarDialog();
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
			menubarToggle.forceToTop();
		}
		if(menubar.isVisible()){
			menubar.forceToTop();
		}
		messageManager.forceToTop();
	}
	
}
