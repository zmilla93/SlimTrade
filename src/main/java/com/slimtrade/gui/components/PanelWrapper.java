package com.slimtrade.gui.components;

import com.slimtrade.gui.basic.BasicDialog;

import javax.swing.*;

public class PanelWrapper extends BasicDialog {

	private static final long serialVersionUID = 1L;
	public JPanel panel;
	
	public PanelWrapper(JPanel panel) {
		super("Slimtrade Wrapper");
		buildWrapper(panel);
	}
	
	public PanelWrapper(JPanel panel, String title) {
		super(title);
		buildWrapper(panel);
	}
	
	private void buildWrapper(JPanel panel){
		this.panel = panel;
		this.add(this.panel);
		this.pack();
		this.setVisible(true);
	}
	
	public JPanel getPanel(){
		return this.panel;
	}

}
