package com.slimtrade.gui.basic;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.JDialog;

public class BasicDialog extends HideableDialog implements IColorable {

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

    @Override
    public void updateColor() {
        this.getContentPane().setBackground(ColorManager.BACKGROUND);
        this.getContentPane().setForeground(ColorManager.TEXT);
    }
}
