package main.java.com.slimtrade.gui.components;

import javax.swing.JPanel;

import main.java.com.slimtrade.gui.basic.BasicDialog;

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

}
