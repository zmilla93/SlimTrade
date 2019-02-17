package main.java.com.slimtrade.gui.options;

public interface Saveable {

	public boolean unsavedChangess = false;
	
	public void save();
	public void load();
	
}
