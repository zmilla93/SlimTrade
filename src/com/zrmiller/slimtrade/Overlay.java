package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.buttons.BasicButton;
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
	public static Container screenContainer;
	public static CharacterWindow characterPanel;
	public static HistoryWindow historyPanel;
	public static MenubarWindow menubar;
	public static JButton menubarShowButton;
	public static MessageManager messageManager;
	public static OptionWindow optionPanel;
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
		
		//Initialize Screen Frame
		JFrame screenFrame = new JFrame();
		screenFrame.setLayout(null);
		screenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		screenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		screenFrame.setUndecorated(true);
		screenFrame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		screenFrame.setAlwaysOnTop(true);
		screenContainer = screenFrame.getContentPane();
		
		//TEMP VIEWABLE FRAME
		
		
		
		
		
		
		
		
		BasicPanel p = new BasicPanel(400,400, Color.GRAY, new FlowLayout(FlowLayout.CENTER, 10, 10));
		p.setBounds(0, 0, 400, 400);
		int bSize = 20;
		CloseButton.width = bSize;
		CloseButton.height = bSize;
		CloseButton b = new CloseButton();
		b.setCustomIcon("/alch.png");
		b.setLayout(Overlay.flowCenter);
		p.add(b);
//		JLabel priceIcon = new JLabel();
//		priceIcon.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/close.png")).getImage().getScaledInstance((int)(bSize), (int)(bSize), Image.SCALE_SMOOTH)));
//		priceIcon.setBounds(0, 0, bSize, bSize);
//		priceIcon.setPreferredSize(new Dimension(bSize, bSize));
//		b.add(priceIcon);
		
		
		Border b1 = BorderFactory.createEmptyBorder();
//		Border b1 = BorderFactory.createLineBorder(Color.BLACK);
		Border b2 = BorderFactory.createLineBorder(Color.RED);
//		b.setBorderPresets(b1, b2);
		
//		pbut.add(priceIcon);
//		screenContainer.add(p);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Menu Bar Show Button
		menubarShowButton = new JButton();
		menubarShowButton.setBounds(0, (int)(screenSize.getHeight()-MenuButton.height), MenuButton.height, MenuButton.height);
		menubarShowButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent e) {
		    	menubarShowButton.setVisible(false);
		    	Overlay.menubar.setVisible(true);
		    }
		});
		screenContainer.add(menubarShowButton);
		
		//Menu Bar+
		menubar = new MenubarWindow();
		screenContainer.add(menubar);
		
		optionPanel = new OptionWindow();
		screenContainer.add(optionPanel);
		
		characterPanel = new CharacterWindow();
		screenContainer.add(characterPanel);
		
		historyPanel = new HistoryWindow();
		centerFrame(historyPanel);
		screenContainer.add(historyPanel);
		
		stashHelperContainer = new StashHelperContainer();
		screenContainer.add(stashHelperContainer);
		
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
		screenContainer.add(stashWindow);
		
		//Message Manager - SHOULD ALWAYS BE LAST
		messageManager = new MessageManager();
		screenContainer.add(messageManager);
		
		//Finish
		screenFrame.pack();
		screenFrame.setVisible(true);
		stashHelperContainer.updateBounds();
	}
	
	public static void hideAllTempFrames(){
		Overlay.characterPanel.setVisible(false);
		Overlay.historyPanel.setVisible(false);
		Overlay.optionPanel.setVisible(false);
		Overlay.stashWindow.setVisible(false);
	}
	
	public static void centerFrame(JPanel panel){
		panel.setLocation(screenWidth/2-panel.getWidth()/2, screenHeight/2-panel.getHeight()/2);
	}
	
}
