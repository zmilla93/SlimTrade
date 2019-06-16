package main.java.com.slimtrade.core.observing.improved;

import java.util.ArrayList;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.enums.ColorThemeType;

public class EventManager {

	private ArrayList<ColorUpdateListener> updateColorList = new ArrayList<ColorUpdateListener>();
	
	public void addListener(ColorUpdateListener listener){
		updateColorList.add(listener);
	}
	
	public void removeListener(ColorUpdateListener listener){
		updateColorList.remove(listener);
	}
	
	public void updateAllColors(ColorThemeType theme){
		System.out.println("Updaing All Colors");
		ColorManager.setTheme(theme);
		for(ColorUpdateListener l : updateColorList){
			l.updateColor();
		}
	}
	
}
