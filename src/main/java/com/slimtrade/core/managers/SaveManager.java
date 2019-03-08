package main.java.com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.slimtrade.core.Main;

public class SaveManager {

	// Full
	private String clientDirectory;
	private String clientPath;
	private String savePath;

	// Stubs
	private final String folderWin = "SlimTrade";
	private final String folderOther = ".slimtrade";
	private final String saveDirectory;
	private final String sep = File.separator;
	private final String saveStub = sep + "settings.json";
	private final String poeLogs = "Path of Exile" + sep + "logs";
	private final String steamStub = "Program Files" + sep + "Steam" + sep + "steamapps" + sep + "common";
	private final String steamStubx86 = "Program Files (x86)" + sep + "Steam" + sep + "steamapps" + sep + "common";
	private final String standAlone = "Program Files" + sep + "Grinding Gear Games";
	private final String standAlonex86 = "Program Files (x86)" + sep + "Grinding Gear Games";

	private JSONObject saveData;

	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;

	boolean validClientPath = false;

	private boolean log = false;

	public SaveManager() {
		// Set save directory
		String os = (System.getProperty("os.name")).toUpperCase();
		if (os.contains("WIN")) {
			saveDirectory = System.getenv("LocalAppData") + File.separator + folderWin;
		} else {
			saveDirectory = System.getProperty("user.home") + File.separator + folderOther;
		}
		savePath = saveDirectory + saveStub;
		File saveDir = new File(saveDirectory);
		if (!saveDir.exists()) {
			saveDir.mkdirs();
		}
		initSave();

		// Attempt to get client path
		if (hasEntry("general", "clientDirectory")) {
			clientDirectory = getString("general", "clientDirectory");
			clientPath = clientDirectory + "Client.txt";
			validClientPath = true;
		} else {
			int clientCount = 0;
			String validDirectory = null;
			String[] stubs = { steamStub, steamStubx86, standAlone, standAlonex86 };
			for (File s : File.listRoots()) {
				for (String stub : stubs) {
					File f = new File(s.toString() + stub + sep + poeLogs);
					if (f.exists() && f.isDirectory()) {
						clientCount++;
						validDirectory = f.getPath();
						putObject(f.getPath(), "general", "clientDirectory");
					}
				}
			}
			if (clientCount == 1) {
				validClientPath = true;
				clientDirectory = validDirectory;
				clientPath = validDirectory + sep + "Client.txt";
			}
		}
	}

	private void initSave() {
		File saveFile = new File(savePath);
		if (saveFile.exists() && saveFile.isFile()) {
			try {
				fr = new FileReader(saveFile);
				br = new BufferedReader(fr);
				StringBuilder in = new StringBuilder();
				while (br.ready()) {
					in.append(br.readLine());
				}
				br.close();
				saveData = new JSONObject(in.toString());
			} catch (IOException | JSONException e) {
				saveData = new JSONObject();
			}
		} else {
			saveData = new JSONObject();
		}
	}

	public void saveToDisk() {
		File saveFile = new File(savePath);
		try {
			fw = new FileWriter(saveFile);
			fw.write(saveData.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getString(String... keys) {
		Object obj = getObject(keys);
		if (obj instanceof String) {
			return (String) obj;
		}
		return null;
	}

	// If keys result in array, array is returned as string. Not sure if worth
	// fixing
	public String getStringDefault(String defaultValue, String... keys) {
		if (!hasEntry(keys)) {
			return defaultValue;
		}
		try {
			String value = getObject(keys).toString();
			return value;
		} catch (NullPointerException e) {
			return defaultValue;
		}
	}

	public void putStringDefault(String defaultValue, String... keys) {
		boolean addEntry = false;
		if (hasEntry(keys)) {
			Object o = getObject(keys);
			if (!(o instanceof String)) {
				addEntry = true;
			}
		} else {
			addEntry = true;
		}
		if (addEntry) {
			putObject(defaultValue, keys);
		}
	}

	public int getInt(String... keys) {
		Object obj = getObject(keys);
		if (obj instanceof Integer) {
			return (int) obj;
		}
		return Integer.MIN_VALUE;
	}

	public int getDefaultInt(int min, int max, int def, String... keys) {
		int i = getInt(keys);
		if (i < min || i > max) {
			return def;
		}
		return i;
	}

	public void putDefaultInt(int defaultValue, String... keys) {
		boolean addEntry = false;
		if (hasEntry(keys)) {
			Object o = getObject(keys);
			if (!(o instanceof Integer)) {
				addEntry = true;
			}
		} else {
			addEntry = true;
		}
		if (addEntry) {
			putObject(defaultValue, keys);
		}
	}

	public void putDefaultBool(boolean defaultValue, String... keys) {
		boolean addEntry = false;
		if (hasEntry(keys)) {
			Object o = getObject(keys);
			if (!(o instanceof Boolean)) {
				addEntry = true;
			}
		} else {
			addEntry = true;
		}
		if (addEntry) {
			putObject(defaultValue, keys);
		}
	}

	public boolean getBool(String... keys) {
		Object obj = getObject(keys);
		if (obj instanceof Boolean) {
			return (boolean) obj;
		}
		return false;
	}

	public String getEnumValue(Class<?> c, String... keys) {
		return getEnumValue(c, false, keys);
	}

	public String getEnumValue(Class<?> c, boolean allowNull, String... keys) {
		Object val = getObject(keys);
		String name = c.getSimpleName();
		String defaultValue = null;
		for (Object o : c.getFields()) {
			String compare = o.toString().replaceAll(".+" + name + "\\.", "");
			if (!allowNull && defaultValue == null) {
				defaultValue = compare;
			}
			if (val != null && val.toString().equals(compare)) {
				return compare;
			}
		}
		if (log) {
			Main.logger.log(Level.WARNING, "Could not find enum value " + Arrays.toString(keys) + "\nReturning default value");
		}
		return defaultValue;
	}

	public boolean hasEntry(String... keys) {
		JSONObject curArr = saveData;
		for (int i = 0; i < keys.length; i++) {
			if (curArr.has(keys[i])) {
				try {
					curArr = curArr.getJSONObject(keys[i]);
				} catch (JSONException e) {
					if (curArr.has(keys[i])) {
						// TODO : Change to break?
						continue;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public void putObject(Object value, String... keys) {
		// Object value = obj;
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		String key = keys[keys.length - 1];
		JSONObject activeArr;
		if (keys.length == 1) {
			// Single key handling
			try {
				saveData.put(key, value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (keys.length > 1) {
			// Get existing arrays, or create new ones
			activeArr = saveData;
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						arr.add(activeArr.getJSONObject(keys[i]));
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						JSONObject newArr = new JSONObject();
						arr.add(newArr);
						activeArr = newArr;
					}
				} else {
					arr.add(new JSONObject());
				}
			}
			// Add Final value to deepest nested array
			try {
				arr.get(arr.size() - 1).put(key, value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// Rebuild into single array
			for (int i = keys.length - 2; i >= 1; i--) {
				try {
					arr.get(i - 1).put(keys[i], arr.get(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				saveData.put(keys[0], arr.get(0));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private Object getObject(String... keys) {
		String key = keys[keys.length - 1];
		Object value = null;
		JSONObject activeArr = saveData;
		if (keys.length == 1) {
			if (saveData.has(key)) {
				try {
					value = saveData.get(key);
				} catch (JSONException e) {
					if (log) {
						Main.logger.log(Level.WARNING, "Failed to get value from single key \"" + key + "\"");
					}
					return null;
				}
			}
		} else if (keys.length > 1) {
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						return null;
					}
				} else {
					return null;
				}
			}
			try {
				value = activeArr.get(key);
			} catch (JSONException e) {
				if (log) {
					Main.logger.log(Level.WARNING, "Failed to get value from nested keys " + Arrays.toString(keys) + "");
				}
				return null;
			}
		}
		return value;
	}

	public void deleteObject(String... keys) {
		JSONObject curArr = saveData;
		for (int i = 0; i < keys.length - 1; i++) {
			if (curArr.has(keys[i])) {
				try {
					curArr = curArr.getJSONObject(keys[i]);
				} catch (JSONException e) {
					if (curArr.has(keys[i])) {
						curArr.remove(keys[i]);
						return;
					}
				}
			} else {
				return;
			}
		}
		curArr.remove(keys[keys.length - 1]);
	}

	public boolean isValidClientPath() {
		return validClientPath;
	}

	public String getClientDirectory() {
		return this.clientDirectory;
	}

	public String getClientPath() {
		return this.clientPath;
	}

}
