package main.java.com.slimtrade.core;

public class SaveConstants {

	/*
	 * String references for JSON save file
	 */
	
	public static class General{
		public static final String[] CHARACTER = {"general", "character"};
		public static final String[] CLIENT_PATH = {"general", "clientPath"};
		public static final String[] CLOSE_ON_KICK = {"general", "closeOnKick"};
		public static final String[] SHOW_GUILD = {"general", "showGuild"};
		public static final String[] SHOW_STASH_TAB = {"general", "showStashTab"};
	}
	
	public static class History{
		public static final String[] DATE_STYLE = {};
		public static final String[] MAX_MESSAGE_COUNT = {};
		public static final String[] ORDER_TYPE = {};
		public static final String[] TIME_STYLE = {};
	}
	
	public static class IgnoreItems{
		public static final String[] base = {"ignoreItems"};
		public static final String[] itemName = {"ignoreItems", null, "itemName", "item"};
		public static final String[] matchType = {"ignoreItems", null, "matchType", "item"};
		public static final String[] expireTime = {"ignoreItems", null, "expireTime", "item"};
	}
	
	public static class Macros{
		public static final String test = "cool";
	}
	
	public static class StashTabs{
		public static final String basePath[] = {"stashTabs"};
	}
	
	public static String[] appendedPath(String[] inputArray, String appendString){
		String[] newArray = new String[inputArray.length+1];
		int i = 0;
		for(String s : inputArray){
			newArray[i] = inputArray[i];
			i++;
		}
		newArray[i] = appendString;
		//TODO : REMOVE
		System.out.println("NEW ARRAY : ");
		for(String s : newArray){
			System.out.println("\t" + s);
		}
		return newArray;
	}
	
	public static String[] numeredPath(String[] inputArray, int num){
		String[] newArray = new String[inputArray.length-1];
		int i = 0;
		for(String s : inputArray){
			if(s == null){
				newArray[i] = inputArray[inputArray.length-1] + num;
			}else{
				newArray[i] = s;
			}
			if(i == newArray.length-1){
				break;
			}
			i++;
		}
		return newArray;
	}
	
}
