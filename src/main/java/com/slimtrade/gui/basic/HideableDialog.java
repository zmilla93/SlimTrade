package com.slimtrade.gui.basic;

import javax.swing.*;

public class HideableDialog extends JDialog{
	
	private static final long serialVersionUID = 1L;
	public boolean visible = false;
	
	public void toggleShow(){
		this.setShow(!this.visible);
	}
	
	public void setShow(boolean visible){
		this.visible = visible;
		this.setVisible(visible);
	}
	
	public void refreshVisibility(){
		this.setVisible(visible);
		this.setAlwaysOnTop(false);
		this.setAlwaysOnTop(true);
	}
	
}
