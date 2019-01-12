package com.zrmiller.slimtrade.dialog;

import javax.swing.JFrame;

public class BasicDialog extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public BasicDialog(){
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setType(JFrame.Type.UTILITY);
		this.pack();
	}
	
	public void forceToTop(){
		this.setAlwaysOnTop(false);
		this.setAlwaysOnTop(true);
		this.toFront();
	}
	
}
