package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.buttons.CloseButton;
import com.zrmiller.slimtrade.buttons.MenuButton;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.panels.GridPanel;
import com.zrmiller.slimtrade.panels.StashHelperContainer;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.HistoryWindow;
import com.zrmiller.slimtrade.windows.MenubarWindow;
import com.zrmiller.slimtrade.windows.OptionWindow;
import com.zrmiller.slimtrade.windows.StashWindow;

public class Overlay {

	//Pseudo Constants
	public static Dimension screenSize;
	public static int screenWidth;
	public static int screenHeight;
	public static FlowLayout flowLeft;
	public static FlowLayout flowCenter;
	public static FlowLayout flowRight;
	
	//PANELS
	public static Container menubarContainer;
	public static Container optionContainer;
	public static Container messageManagerContainer;
	public static CharacterWindow characterWindow;
	public static HistoryWindow historyWindow;
	public static MenubarWindow menubarWindow;
	public static JButton menubarShowButton;
	public static MessageManager messageManager;
	public static OptionWindow optionWindow;
	public static StashWindow stashWindow;
	public static GridPanel stashGrid;
	public static StashHelperContainer stashHelperContainer;
	
	public Overlay(){
		
		//TODO : Doublecheck all statics
		//TODO : disallow windows going off screen
		//TODO : null deleted panels
		
		ColorManager.setMessageTheme();
		
		//Initialize Pseudo Constants
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.width;
		screenHeight = screenSize.height;
		flowLeft = new FlowLayout(FlowLayout.LEFT, 0, 0);
		flowCenter = new FlowLayout(FlowLayout.CENTER, 0, 0);
		flowRight = new FlowLayout(FlowLayout.RIGHT, 0, 0);
		
		JFrame menubarFrame = new JFrame();
		menubarFrame.setLayout(null);
		menubarFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		menubarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menubarFrame.setUndecorated(true);
		menubarFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		menubarFrame.setAlwaysOnTop(true);
		menubarFrame.setType(JFrame.Type.UTILITY);
		menubarContainer = menubarFrame.getContentPane();
		
		JFrame optionFrame = new JFrame();
		optionFrame.setLayout(null);
		optionFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionFrame.setUndecorated(true);
		optionFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		optionFrame.setAlwaysOnTop(true);
		optionFrame.setType(JFrame.Type.UTILITY);
		optionContainer = optionFrame.getContentPane();
		
		//Initialize Screen Frame
		JFrame messageManagerFrame = new JFrame();
		messageManagerFrame.setLayout(null);
		messageManagerFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		messageManagerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messageManagerFrame.setUndecorated(true);
		messageManagerFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		messageManagerFrame.setAlwaysOnTop(true);
//		screenFrame.setType(JFrame.Type.UTILITY);
		messageManagerContainer = messageManagerFrame.getContentPane();
		
		/*
		 * TESTING
		 */
		
		BasicPanel p = new BasicPanel(400,400, Color.GRAY, new FlowLayout(FlowLayout.CENTER, 10, 10));
		p.setBounds(800, 0, 400, 400);
		int bSize = 20;
		CloseButton.width = bSize;
		CloseButton.height = bSize;
		CloseButton b = new CloseButton();
		b.setCustomIcon("/alch.png");
		b.setLayout(Overlay.flowCenter);
		p.add(b);		
		
		Border b1 = BorderFactory.createEmptyBorder();
//		Border b1 = BorderFactory.createLineBorder(Color.BLACK);
		Border b2 = BorderFactory.createLineBorder(Color.RED);
//		screenContainer.add(p);
		
		/*
		 * END TESTING
		 */
		
		//Menu Bar Show Button
		menubarShowButton = new JButton();
		menubarShowButton.setBounds(0, (int)(screenSize.getHeight()-MenuButton.height), MenuButton.height, MenuButton.height);
		menubarShowButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	menubarShowButton.setVisible(false);
		    	Overlay.menubarWindow.setVisible(true);
		    }
		});
		menubarContainer.add(menubarShowButton);
		
		//Menu Bar+
		menubarWindow = new MenubarWindow();
		menubarContainer.add(menubarWindow);
		
		optionWindow = new OptionWindow();
		optionContainer.add(optionWindow);
		
		characterWindow = new CharacterWindow();
		optionContainer.add(characterWindow);
		File charFile = new File("char.pref");
		if(charFile.exists()){
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
		
		historyWindow = new HistoryWindow();
		centerFrame(historyWindow);
		optionContainer.add(historyWindow);
		
		stashHelperContainer = new StashHelperContainer();
		optionContainer.add(stashHelperContainer);
		
		//TODO : Move Loading once more is added
		//Load Stash Settings
		File stashFile = new File("stash.pref");
		if(stashFile.exists()){
			try {
				ObjectInputStream stash = new ObjectInputStream(new FileInputStream("stash.pref"));
				Point winPos = (Point) stash.readObject();
				Dimension containerSize = (Dimension) stash.readObject();
				Point gridPos = (Point) stash.readObject();
				Dimension gridSize = (Dimension) stash.readObject();
				stash.close();
				StashWindow.setDefaultWinPos(winPos);
				StashWindow.setDefaultContainerSize(containerSize);
				StashWindow.setDefaultGridPos(gridPos);
				StashWindow.setDefaultGridSize(gridSize);
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		stashWindow = new StashWindow();
		optionContainer.add(stashWindow);
		
		//Message Manager - SHOULD ALWAYS BE LAST
		messageManager = new MessageManager();
		messageManagerContainer.add(messageManager);
		
		//Finish
//		characterWindow.setVisible(true);
		
		menubarFrame.pack();
		menubarFrame.setVisible(true);
		optionFrame.pack();
		optionFrame.setVisible(true);
		messageManagerFrame.pack();
		messageManagerFrame.setVisible(true);
		stashHelperContainer.updateBounds();
	}
	
	//TODO : This is a temp fix for laying. A better solution would be layered panels
	public static void fixPanelLayering(){
		Component[] renderFrames = {optionWindow, stashWindow, characterWindow, historyWindow, menubarWindow};
		for(Component c : renderFrames){
			if(c.isVisible()){
				c.revalidate();
				c.repaint();
			}
		}
	}
	
	public static void centerFrame(JPanel panel){
		panel.setLocation(screenWidth/2-panel.getWidth()/2, screenHeight/2-panel.getHeight()/2);
	}
	
	public static void hideAllTempFrames(){
		Overlay.characterWindow.setVisible(false);
		Overlay.historyWindow.setVisible(false);
		Overlay.optionWindow.setVisible(false);
		Overlay.stashWindow.setVisible(false);
	}
	

	
}
