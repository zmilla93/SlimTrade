package main.java.com.slimtrade.event;

import java.util.EventListener;

public interface InfoButtonListener extends EventListener {

	public void infoButtonEventOccurred(InfoButtonEvent event);
	
}
