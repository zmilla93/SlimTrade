package main.java.com.slimtrade.core;

public class SaveConstants {

	/*
	 * String references for JSON save file
	 */

	public static class Audio {
		
		public static class IncomingTrade {
			public static final String[] TYPE = { "audio", "incomingTrade", "type" };
			public static final String[] VOLUME = { "audio", "incomingTrade", "volume" };
		}
		
		public static class OutgoingTrade {
			public static final String[] TYPE = { "audio", "outgoingTrade", "type" };
			public static final String[] VOLUME = { "audio", "outgoingTrade", "volume" };
		}
		
		public static class ChatScanner {
			public static final String[] TYPE = { "audio", "chatScanner", "type" };
			public static final String[] VOLUME = { "audio", "chatScanner", "volume" };
		}

		public static class UIClick {
			public static final String[] TYPE = { "audio", "uiClick", "type" };
			public static final String[] VOLUME = { "audio", "uiClick", "volume" };
		}
		
	}

	public static class General {
		public static final String[] CHARACTER = { "general", "character" };
		public static final String[] CLIENT_PATH = { "general", "clientPath" };
		public static final String[] CLOSE_ON_KICK = { "general", "closeOnKick" };
		public static final String[] COLOR_THEME = { "general", "colorTheme" };
		public static final String[] QUICK_PASTE = { "general", "quickPaste" };
		public static final String[] SHOW_GUILD = { "general", "showGuild" };
		public static final String[] SHOW_STASH_TAB = { "general", "showStashTab" };
	}

	public static class History {
		public static final String[] DATE_STYLE = { "history", "dateStyle" };
		public static final String[] MAX_MESSAGE_COUNT = { "history", "maxMessageCount" };
		public static final String[] ORDER_TYPE = { "history", "orderType" };
		public static final String[] TIME_STYLE = { "history", "timeStyle" };
	}

	public static class IgnoreItems {
		public static final String[] base = { "ignoreItems" };
		private static final String[] itemName = { "ignoreItems", null, "itemName", "item" };
		private static final String[] matchType = { "ignoreItems", null, "matchType", "item" };
		private static final String[] expireTime = { "ignoreItems", null, "expireTime", "item" };

		public static final String[] getItemName(int index) {
			return numberedPath(itemName, index);
		}

		public static final String[] getMatchType(int index) {
			return numberedPath(matchType, index);
		}

		public static final String[] getExpireTime(int index) {
			return numberedPath(expireTime, index);
		}
	}

	public static class Macros {
		public static final String test = "cool";
	}

	public static class StashTabs {
		public static final String basePath[] = { "stashTabs" };
	}

	public static String[] appendedPath(String[] inputArray, String appendString) {
		String[] newArray = new String[inputArray.length + 1];
		int i = 0;
		for (String s : inputArray) {
			newArray[i] = inputArray[i];
			i++;
		}
		newArray[i] = appendString;
		// TODO : REMOVE
		System.out.println("NEW ARRAY : ");
		for (String s : newArray) {
			System.out.println("\t" + s);
		}
		return newArray;
	}

	public static String[] numberedPath(String[] inputArray, int num) {
		String[] newArray = new String[inputArray.length - 1];
		int i = 0;
		for (String s : inputArray) {
			if (s == null) {
				newArray[i] = inputArray[inputArray.length - 1] + num;
			} else {
				newArray[i] = s;
			}
			if (i == newArray.length - 1) {
				break;
			}
			i++;
		}
		return newArray;
	}

}
