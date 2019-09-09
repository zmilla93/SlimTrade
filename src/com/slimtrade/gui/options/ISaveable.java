package com.slimtrade.gui.options;

public interface ISaveable {

	public boolean unsavedChangess = false;
	
	public void save();
	public void load();
	
}
