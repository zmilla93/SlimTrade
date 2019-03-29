package main.java.com.slimtrade.gui.basic;

import javax.swing.JDialog;

public class BasicDialog extends HideableDialog{

	private static final long serialVersionUID = 1L;
	
	public BasicDialog(){
		buildDialog("SlimTrade Window");
	}
	
	public BasicDialog(String title){
		buildDialog(title);
	}
	
	private void buildDialog(String title){
		this.setTitle(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setFocusable(false);
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setType(JDialog.Type.UTILITY);
	}
	
	public void forceToTop(){
		this.setAlwaysOnTop(false);
		this.setAlwaysOnTop(true);
	}
	
}
