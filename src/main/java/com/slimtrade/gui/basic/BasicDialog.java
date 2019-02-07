package main.java.com.slimtrade.gui.basic;

import javax.swing.JDialog;

public class BasicDialog extends JDialog{

	private static final long serialVersionUID = 1L;
	
	public BasicDialog(){
		this.setTitle("SlimTrade Window");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setFocusable(false);
		this.setFocusableWindowState(false);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
		this.setModal(false);
		this.setType(JDialog.Type.UTILITY);
		this.pack();
	}
	
	public void forceToTop(){
		this.setAlwaysOnTop(false);
		this.setAlwaysOnTop(true);
//		this.toFront();
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
