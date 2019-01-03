package com.zrmiller.slimtrade.windows;

import javax.swing.JFrame;

import com.zrmiller.slimtrade.MessageManager;

public class FrameManager {

	private BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	
	public FrameManager(){
		
		optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		optionWindow.revalidate();
		optionWindow.repaint();
		optionWindow.setVisible(true);
		
		MessageManager messageManager = new MessageManager();
		
		MenubarWindow menubarWindow = new MenubarWindow();
		menubarWindow.setVisible(true);
		
//		messageManager.setVisible(true);
		
//		optionWindow.revalidate();
//		optionWindow.repaint();
		
//		BasicWindowDialog tester = new BasicWindowDialog();

//		tester.setScreenLock(false);
//		tester.setLocation(200, 200);
//		tester.setVisible(true);
//		tester.revalidate();
//		tester.repaint();
		
	}
	
}
