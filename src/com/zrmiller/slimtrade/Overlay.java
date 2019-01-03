package com.zrmiller.slimtrade;

import java.awt.Color;
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
import com.zrmiller.slimtrade.windows.BasicMovableDialog;
import com.zrmiller.slimtrade.windows.BasicWindowDialog;
import com.zrmiller.slimtrade.windows.BasicWindowDialog;
import com.zrmiller.slimtrade.windows.CharacterWindow;
import com.zrmiller.slimtrade.windows.HistoryWindow;
import com.zrmiller.slimtrade.windows.MenubarWindow;
import com.zrmiller.slimtrade.windows.OptionWindow;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

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
	public static Container messageContainer;
	public static CharacterWindow characterWindow;
	public static HistoryWindow historyWindow;
	public static MenubarWindow menubarWindow;
	public static JButton menubarShowButton;
	public static MessageManager messageManager;
	public static OptionWindow optionWindow;
	public static StashGridOverlay stashGridOverlay;
	public static GridPanel stashGrid;
	public static StashHelperContainer stashHelperContainer;
	public static TradeHistory tradeHistory;

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
		
		
		
		
		
		
//		BasicMovableDialog mover = new BasicMovableDialog();
//
//		mover.setScreenLock(false);
//		mover.setSize(400,400);
//		mover.setVisible(true);
//		
//		BasicWindowDialog tester = new BasicWindowDialog();
//
//		tester.setScreenLock(false);
//		tester.setLocation(200, 200);
//		tester.setVisible(true);
//		tester.revalidate();
//		tester.repaint();

		
		
		
		JFrame menubarFrame = new JFrame();
		menubarFrame.setLayout(null);
		menubarFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		menubarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menubarFrame.setUndecorated(true);
		menubarFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
//		menubarFrame.setOpacity(0.0f);
		menubarFrame.setAlwaysOnTop(true);
//		menubarFrame.setType(JFrame.Type.UTILITY);
		menubarContainer = menubarFrame.getContentPane();
//		System.out.println(menubarContainer.isOpaque());
		
		JFrame optionFrame = new JFrame();
		optionFrame.setLayout(null);
		optionFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		optionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		optionFrame.setUndecorated(true);
		optionFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
//		optionFrame.setOpacity(0f);
		optionFrame.setAlwaysOnTop(true);
//		optionFrame.setType(JFrame.Type.UTILITY);
		optionContainer = optionFrame.getContentPane();
		
		//Initialize Screen Frame
		JFrame messageFrame = new JFrame();
		messageFrame.setLayout(null);
		messageFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		messageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		messageFrame.setUndecorated(true);
		messageFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
//		messageFrame.setOpacity(0f);
		messageFrame.setAlwaysOnTop(true);
//		messageFrame.setType(JFrame.Type.UTILITY);
		messageContainer = messageFrame.getContentPane();
		
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
		
		//TODO : Organize by frames
		
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
		messageContainer.add(stashHelperContainer);
		
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
				StashGridOverlay.setDefaultWinPos(winPos);
				StashGridOverlay.setDefaultContainerSize(containerSize);
				StashGridOverlay.setDefaultGridPos(gridPos);
				StashGridOverlay.setDefaultGridSize(gridSize);
			} catch (IOException e2) {
				e2.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
		stashGridOverlay = new StashGridOverlay();
		optionContainer.add(stashGridOverlay);
		
		//CHAT
		tradeHistory = new TradeHistory();
		ChatParser parser = new ChatParser();
		parser.init();
//		parser.update();
		
		//Message Manager - SHOULD ALWAYS BE LAST
		messageManager = new MessageManager();
		messageContainer.add(messageManager);

		//Finish
		
		menubarFrame.pack();
		menubarFrame.setVisible(true);
		optionFrame.pack();
		optionFrame.setVisible(true);
		messageFrame.pack();
		messageFrame.setVisible(true);
		stashHelperContainer.updateBounds();
		
		
		//Temp Testing
		JFrame debugFrame = new JFrame("SlimTrade Debugger");
		debugFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container debugContainer = debugFrame.getContentPane();
		JButton fixButton = new JButton("Plz Fix");
		fixButton.setFocusable(false);
		debugContainer.add(fixButton);

		fixButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	//OFF
		    	Overlay.menubarContainer.setVisible(false);
		    	Overlay.messageContainer.setVisible(false);
		    	Overlay.optionContainer.setVisible(false);
		    	Overlay.stashHelperContainer.setVisible(false);
		    	menubarFrame.setAlwaysOnTop(false);
		    	messageFrame.setAlwaysOnTop(false);
		    	optionFrame.setAlwaysOnTop(false);
		    	
		    	//ON
		    	Overlay.menubarContainer.setVisible(true);
		    	Overlay.messageContainer.setVisible(true);
		    	Overlay.optionContainer.setVisible(true);
		    	Overlay.stashHelperContainer.setVisible(true);
		    	menubarFrame.setAlwaysOnTop(true);
		    	messageFrame.setAlwaysOnTop(true);
		    	optionFrame.setAlwaysOnTop(true);
		    }
		});
		
		debugFrame.setBounds(0,0, 400,200);
		debugFrame.setVisible(true);
		
		
	}
	
	public static void centerFrame(JPanel panel){
		panel.setLocation(screenWidth/2-panel.getWidth()/2, screenHeight/2-panel.getHeight()/2);
	}
	
	public static void hideAllTempFrames(){
		Overlay.characterWindow.setVisible(false);
		Overlay.historyWindow.setVisible(false);
		Overlay.optionWindow.setVisible(false);
		Overlay.stashGridOverlay.setVisible(false);
	}
	
}
