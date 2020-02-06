package com.slimtrade.core.observing.improved;

import java.util.ArrayList;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.managers.ColorManager;

public class EventManager {

	private ArrayList<IColorable> updateColorList = new ArrayList<IColorable>();
	
	public void addListener(IColorable listener){
		updateColorList.add(listener);
//		listener.updateColor();
	}
	
	public void removeListener(IColorable listener){
		updateColorList.remove(listener);
	}
	
	public void updateAllColors(ColorTheme theme){
		System.out.println("Updaing All Colors");
		ColorManager.setTheme(theme);
		for(IColorable l : updateColorList){
			l.updateColor();
		}
	}
	
}
