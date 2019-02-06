package main.java.com.slimtrade.gui;

import java.awt.Dimension;
import java.awt.Point;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;
import main.java.com.slimtrade.gui.menubar.MenubarToggleButton;
import main.java.com.slimtrade.gui.messaging.ItemHighlighter;
import main.java.com.slimtrade.gui.messaging.MessageManager;
import main.java.com.slimtrade.gui.messaging.StashHelperContainer;
import main.java.com.slimtrade.gui.options.OptionsWindow;
import main.java.com.slimtrade.gui.windows.CharacterWindow;
import main.java.com.slimtrade.gui.windows.HistoryWindow;
import main.java.com.slimtrade.gui.windows.OverlayManager;
import main.java.com.slimtrade.gui.windows.StashGridOverlay;

public class FrameManager {

//	public static Debug debug = new Debug();
	
//	public static FlowLayout flowLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);
//	public static FlowLayout flowCenter = new FlowLayout(FlowLayout.CENTER, 0, 0);
//	public static FlowLayout flowRight = new FlowLayout(FlowLayout.RIGHT, 0, 0);
	
	public static OptionsWindow optionsWindow = new OptionsWindow();	
	public static HistoryWindow historyWindow = new HistoryWindow();	
	public static MenubarDialog menubar = new MenubarDialog();
	public static MenubarToggleButton menubarToggle = new MenubarToggleButton();
	public static MessageManager messageManager = new MessageManager();
	public static StashHelperContainer stashHelperContainer = new StashHelperContainer();
	public static StashGridOverlay stashOverlay;
	public static CharacterWindow characterWindow = new CharacterWindow();
	public static OverlayManager overlayManager = new OverlayManager();
	
	public FrameManager(){
		
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
		
		menubar.showDialog();
		
	}
	
	public static void hideMenuFrames(){
		characterWindow.setVisible(false);
		historyWindow.setVisible(false);
		optionsWindow.setVisible(false);
		stashOverlay.setVisible(false);
	}
	
	public static void centerFrame(BasicDialog window){
		window.setLocation((TradeUtility.screenSize.width/2)-(window.getWidth()/2), (TradeUtility.screenSize.height/2)-(window.getHeight()/2));
	}
	
//	public static void centerFrame(UNUSED_BasicWindowDialogBorder window){
//		window.setLocation((TradeUtility.screenSize.width/2)-(window.getWidth()/2), (TradeUtility.screenSize.height/2)-(window.getHeight()/2));
//	}
	
	public static void forceAllToTop(){
		if(menubarToggle.isVisible()){
			
		}
		if(menubar.isVisible()){
			
		}
		menubarToggle.forceToTop();
		menubar.forceToTop();
		messageManager.forceToTop();
		stashHelperContainer.forceToTop();
//		PoeInterface.focus();
	}
	
}
