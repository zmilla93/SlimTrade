package com.slimtrade.core.observing.improved;

import java.util.ArrayList;

import com.slimtrade.enums.ColorTheme;
import com.slimtrade.core.managers.ColorManager;

public class EventManager {

    private ArrayList<IColorable> updateColorList = new ArrayList<IColorable>();

    public void addListener(IColorable listener) {
        if (updateColorList.contains(listener)) {
            return;
        }
//        int i = updateColorList.size();
        updateColorList.add(listener);
//        System.out.print("IColor");
//        if (i == updateColorList.size()) {
//            System.out.print("!");
//        }
//        System.out.println("++(" + updateColorList.size() + "):" + listener);
    }

    public void removeListener(IColorable listener) {
//        int i = updateColorList.size();
        updateColorList.remove(listener);
//        System.out.print("IColor");
//        if (i == updateColorList.size()) {
//            System.out.print("!");
//        }
//        System.out.println("--(" + updateColorList.size() + "):" + listener);
    }

    public void updateAllColors(ColorTheme theme) {
        System.out.println("Updaing All Colors");
        ColorManager.setTheme(theme);
        for (IColorable l : updateColorList) {
            l.updateColor();
        }

    }

}
