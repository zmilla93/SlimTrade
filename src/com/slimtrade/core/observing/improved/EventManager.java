package com.slimtrade.core.observing.improved;

import java.util.ArrayList;

import com.slimtrade.enums.ColorThemeType;
import com.slimtrade.core.managers.ColorManager;

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
