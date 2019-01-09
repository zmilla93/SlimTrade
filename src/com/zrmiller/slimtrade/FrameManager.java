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
import com.zrmiller.slimtrade.dialog.MenubarDialog;
import com.zrmiller.slimtrade.dialog.StashHelper;
import com.zrmiller.slimtrade.dialog.StashHelperContainer;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class FrameManager {

	
	public static BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	public static BasicWindowDialog historyWindow = new BasicWindowDialog("History");	
	public static MenubarDialog menubar = new MenubarDialog();
	public static MenubarToggleButton menubarToggle = new MenubarToggleButton();
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer;
	public static StashGridOverlay stashOverlay;
	public static CharacterWindow characterWindow = new CharacterWindow();
	
	public static Debug debug = new Debug();
	
	
	public FrameManager(){
		stashHelperContainer = new StashHelperContainer();
		//TODO : Revisit saving/loading once 
		File stashFile = new File("stash.pref");
		if(stashFile.exists()){
			System.out.println("Loading stash...");
			try {
				ObjectInputStream stash = new ObjectInputStream(new FileInputStream("stash.pref"));
				Point winPos = (Point) stash.readObject();
				Dimension winSize = (Dimension) stash.readObject();
				Point gridPos = (Point) stash.readObject();
				Dimension gridSize = (Dimension) stash.readObject();
				stash.close();
				StashGridOverlay.setDefaultWinPos(winPos);
				StashGridOverlay.setDefaultWinSize(winSize);
				StashGridOverlay.setDefaultGridPos(gridPos);
				StashGridOverlay.setDefaultGridSize(gridSize);
				stashOverlay = new StashGridOverlay(winPos, winSize.width, winSize.height);
				stashHelperContainer.updateBounds();
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			
		}else{
			stashOverlay = new StashGridOverlay(new Point(0, 0), 200, 400);
		}
		
//		stashHelperContainer.updateBounds();
//		StashHelper.updateCellSize(StashGridOverlay.gridWidth, StashGridOverlay.gridHeight);
		stashHelperContainer.setVisible(true);
		
		File charFile = new File("char.pref");
		if(charFile.exists()){
			System.out.println("Loading character...");
			try {
				ObjectInputStream charInput = new ObjectInputStream(new FileInputStream("char.pref"));
				String character = (String)charInput.readObject();
				String league = (String)charInput.readObject();
				characterWindow.setCharacter(character, league);
				charInput.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
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
