package main.java.com.slimtrade.gui.setup.panels;

import main.java.com.slimtrade.gui.panels.ContainerPanel;
import main.java.com.slimtrade.gui.setup.SetupWindow;

public class AbstractSetupPanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;
	
	protected SetupWindow parent;
	protected boolean complete;
	
	public AbstractSetupPanel(SetupWindow parent){
		this.parent = parent;
	}
	
	public boolean isComplete(){
		return this.complete;
	}

}
