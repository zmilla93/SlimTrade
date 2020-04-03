package com.slimtrade.gui.components;

import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.messaging.MessagePanel;

public class PanelWrapper extends BasicDialog {

	private static final long serialVersionUID = 1L;
	public MessagePanel panel;
	
	public PanelWrapper(MessagePanel panel) {
		super("Slimtrade Wrapper");
		buildWrapper(panel);
	}
	
	public PanelWrapper(MessagePanel panel, String title) {
		super(title);
		buildWrapper(panel);
	}
	
	private void buildWrapper(MessagePanel panel){
		this.panel = panel;
		this.add(this.panel);
		this.pack();
		this.setVisible(true);
	}
	
	public MessagePanel getPanel(){
		return this.panel;
	}

}
