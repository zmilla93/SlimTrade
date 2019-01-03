package com.zrmiller.slimtrade.windows;

import javax.swing.JFrame;

public class FrameManager {

	private BasicWindowDialog optionWindow = new BasicWindowDialog("Options");	
	
	public FrameManager(){
		
		optionWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		optionWindow.revalidate();
		optionWindow.repaint();
		optionWindow.setVisible(true);
		
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
