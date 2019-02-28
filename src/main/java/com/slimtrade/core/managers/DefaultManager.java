package main.java.com.slimtrade.core.managers;

import java.util.ArrayList;

import main.java.com.slimtrade.core.Main;

public class DefaultManager {

	ArrayList<String[]> stringList = new ArrayList<String[]>();
	ArrayList<int[]> intList = new ArrayList<int[]>();
	ArrayList<double[]> doubleList = new ArrayList<double[]>();
	ArrayList<boolean[]> booleanList = new ArrayList<boolean[]>();
	
	public DefaultManager(){
		
		checkInt(0, "stashOverlay", "x");
		checkInt(0, "stashOverlay", "y");
		
		stringList.add(new String[]{"Test", "test"});
	}
	
	private void checkInt(int def, String... keys){
		int i = Main.saveManager.getInt(keys);
		if(i==Integer.MIN_VALUE){
			
		}
		System.out.println(i);
	}
	
}
