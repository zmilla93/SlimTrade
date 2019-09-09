package com.slimtrade.gui.dialogs;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;

public class LoadingDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	public LoadingDialog(){
		this.setLayout(new GridBagLayout());
		this.getRootPane().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		this.setSize(200, 80);
		
		Container container = this.getContentPane();
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		container.add(new JLabel("Loading SlimTrade..."), gc);
		
		FrameManager.centerFrame(this);
		this.setVisible(true);
	}
	
}
