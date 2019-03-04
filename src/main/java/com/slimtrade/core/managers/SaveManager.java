package main.java.com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.filechooser.FileSystemView;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.dialogs.InfoDialog;

public class SaveManager {

	public boolean validClientPath = false;
	public boolean validSaveDirectory = false;
	public String clientPathString;
	public String savePathString;

	private boolean clientConflict = false;

	private String saveStub = "/settings.json";
	private String steamStub = ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt";
	private String standAloneStub = ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";
//	private String[] commonDrives = File.listRoots();
	private String[] commonDrives = { "C", "D", "E", "F", "G", "H" };
	// private String[] commonDrives = { "H" };
	private String user = System.getProperty("user.name");

	private JSONObject saveData;
	private JSONObject saveDataSnapshot;
	private boolean hasUnsavedChanges = false;

	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;

	public SaveManager() {
		//TEST
		File[] paths = File.listRoots();
		FileSystemView fsv = FileSystemView.getFileSystemView();
//		paths = File.listRoots();
		for(File p : paths){
			System.out.println(p);
			System.out.println(fsv.getSystemTypeDescription(p));
		}
		System.out.println(System.getenv("LocalAppData"));
		
		// Steam Path
		for (String drive : commonDrives) {
			File clientFile = new File(drive + steamStub);
			if (clientFile.exists() && clientFile.isFile()) {
				clientPathString = clientFile.getPath();
				validClientPath = true;
			}
		}
		// StandAlone Path
		for (String drive : commonDrives) {
			File clientFile = new File(drive + standAloneStub);
			if (clientFile.exists() && clientFile.isFile()) {
				if (validClientPath) {
					clientPathString = null;
					validClientPath = false;
					clientConflict = true;
				}
				clientPathString = clientFile.getPath();
				validClientPath = true;
			}
		}
		// Save Directory
		for (String drive : commonDrives) {
			File localDirectory = new File(drive + ":/Users/" + user + "/AppData/Local");
			if (localDirectory.exists() && localDirectory.isDirectory()) {
				validSaveDirectory = true;
				File saveDirectory = new File(drive + ":/Users/" + user + "/AppData/Local/SlimTrade");
				savePathString = saveDirectory.getPath() + saveStub;
				if (!saveDirectory.exists()) {
					saveDirectory.mkdir();
				} else {

				}

			} else {
				// TODO : Prompt user to set a save directory
			}
		}
		initSaveData();
		if (clientConflict) {
			System.err.println("Conflict");
			InfoDialog conflictDialog = new InfoDialog("SlimTrade - Warning");
			conflictDialog.addSectionHeader("Multiple Client Paths Found");
			conflictDialog.addText("Client.txt files found for both STEAM and STANDALONE versions of POE.");
			conflictDialog.addText("Please manually select a valid path in the options menu.");
			conflictDialog.finalizeDialog();
		} else if (!validClientPath) {
			InfoDialog conflictDialog = new InfoDialog("SlimTrade - Warning");
			conflictDialog.addSectionHeader("No Client Path Found");
			conflictDialog.addText("Unable to find a valid path for POE's client.txt file.");
			conflictDialog.addText("Please manually select a valid path in the options menu.");
			conflictDialog.addBuffer();

			conflictDialog.addSectionHeader("Multiple Client Paths Found");
			conflictDialog.addText("Client.txt files found for both STEAM and STANDALONE versions of POE.");
			conflictDialog.addText("Please manually select a valid path in the options menu.");
			conflictDialog.addBuffer();

			conflictDialog.addSectionHeader("SlimTrade will not function properly until all issues are resolved.");
			conflictDialog.addBuffer();

			conflictDialog.finalizeDialog();
		}
	}

	private void initSaveData() {
		if (!validSaveDirectory) {
			saveData = new JSONObject();
			return;
		}
		File save = new File(savePathString);
		if (!save.exists()) {
			try {
				fw = new FileWriter(savePathString);
				JSONObject json = new JSONObject();
				fw.write(json.toString());
				fw.close();
				saveData = json;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			refreshFromDisk();
		}
	}

	private void refreshFromDisk() {
		if (!validSaveDirectory) {
			return;
		}
		try {
			fr = new FileReader(savePathString);
			br = new BufferedReader(fr);
			if (!br.ready()) {
				saveData = new JSONObject();
			} else {
				saveData = new JSONObject(br.readLine());
			}
			br.close();
		} catch (JSONException | IOException e) {
			System.err.println("JSON File Corrupted. Deleting old save data.");
			saveData = new JSONObject();
			try {
				br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	public void saveToDisk() {
		if (!validSaveDirectory) {
			return;
		}
		try {
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
			hasUnsavedChanges = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void putString(String value, String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given for value \"" + value + "\"\n");
			return;
		}
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
						// class Local {};
						System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tError while trying to save \"" + key + "\" : \"" + value + "\"\n" + "\tArray key expected : \"" + keys[i] + "\"\n" + "\t" + activeArr + "\n");
						return;
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
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		hasUnsavedChanges = true;
	}

//	public String getStringEnum(String... keys) {
//		return getString(keys).toUpperCase().replaceAll("\\s+", "");
//	}
	
	public String getEnumValue(Class<?> c, boolean allowDefault, String... keys){
		System.out.println("ENUM VALUES ::: " + c.getSimpleName());
		for(Field s : c.getFields()){
			System.out.println(s.toString().replaceAll(".+enums\\..+\\.", ""));
		}
		String s = this.getString(keys);
		System.out.println("VALUE ::: " + s);
		
		for(Object o : c.getFields()){
			if(s.equals(o.toString().replaceAll(".+enums\\..+\\.", ""))){
				return s;
			}
		}
		return null;
	}

	public String getString(String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return null;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given\n");
			return null;
		}
		String key = keys[keys.length - 1];
		String value = null;
		JSONObject activeArr = saveData;
		if (keys.length == 1) {
			if (saveData.has(key)) {
				try {
					value = saveData.getString(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else if (keys.length > 1) {
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					StringBuilder chain = new StringBuilder();
					for (String s : keys) {
						chain.append("\"" + s + "\" ");
					}
					System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid key within chain received \"" + keys[i] + "\"\n\tIn chain " + chain + "\n");
					return null;
				}
			}
			try {
				value = activeArr.getString(key);
			} catch (JSONException e) {
				StringBuilder chain = new StringBuilder();
				for (String s : keys) {
					chain.append("\"" + s + "\" ");
				}
				System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid final key received \"" + key + "\"\n\tIn chain " + chain + "\n");
			}
		}
		return value;
	}

	public void putInt(Integer value, String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given for value \"" + value + "\"\n");
			return;
		}
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		String key = keys[keys.length - 1];
		JSONObject activeArr;
		if (keys.length == 1) {
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
						// class Local {};
						System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tError while trying to save \"" + key + "\" : \"" + value + "\"\n" + "\tArray key expected : \"" + keys[i] + "\"\n" + "\t" + activeArr + "\n");
						return;
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
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		hasUnsavedChanges = true;
	}

	public int getInt(String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return Integer.MIN_VALUE;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given\n");
			return Integer.MIN_VALUE;
		}
		String key = keys[keys.length - 1];
		int value = Integer.MIN_VALUE;
		JSONObject activeArr = saveData;
		if (keys.length == 1) {
			if (saveData.has(key)) {
				try {
					value = saveData.getInt(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else if (keys.length > 1) {
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					StringBuilder chain = new StringBuilder();
					for (String s : keys) {
						chain.append("\"" + s + "\" ");
					}
					System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid key within chain received \"" + keys[i] + "\"\n\tIn chain " + chain + "\n");
					return Integer.MIN_VALUE;
				}
			}
			try {
				value = activeArr.getInt(key);
			} catch (JSONException e) {
				StringBuilder chain = new StringBuilder();
				for (String s : keys) {
					chain.append("\"" + s + "\" ");
				}
				System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid final key received \"" + key + "\"\n\tIn chain " + chain + "\n");
			}
		}
		return value;
	}

	public void putDouble(double value, String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given for value \"" + value + "\"\n");
			return;
		}
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		String key = keys[keys.length - 1];
		JSONObject activeArr;
		if (keys.length == 1) {
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
						// class Local {};
						System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tError while trying to save \"" + key + "\" : \"" + value + "\"\n" + "\tArray key expected : \"" + keys[i] + "\"\n" + "\t" + activeArr + "\n");
						return;
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
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		hasUnsavedChanges = true;
	}

	public double getDouble(String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return Integer.MIN_VALUE;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given\n");
			return Integer.MIN_VALUE;
		}
		String key = keys[keys.length - 1];
		double value = Integer.MIN_VALUE;
		JSONObject activeArr = saveData;
		if (keys.length == 1) {
			if (saveData.has(key)) {
				try {
					value = saveData.getDouble(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else if (keys.length > 1) {
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					StringBuilder chain = new StringBuilder();
					for (String s : keys) {
						chain.append("\"" + s + "\" ");
					}
					System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid key within chain received \"" + keys[i] + "\"\n\tIn chain " + chain + "\n");
					return Integer.MIN_VALUE;
				}
			}
			try {
				value = activeArr.getDouble(key);
			} catch (JSONException e) {
				StringBuilder chain = new StringBuilder();
				for (String s : keys) {
					chain.append("\"" + s + "\" ");
				}
				System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid final key received \"" + key + "\"\n\tIn chain " + chain + "\n");
			}
		}
		return value;
	}

	public void putBool(boolean value, String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given for value \"" + value + "\"\n");
			return;
		}
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		String key = keys[keys.length - 1];
		JSONObject activeArr;
		if (keys.length == 1) {
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
						// class Local {};
						System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tError while trying to save \"" + key + "\" : \"" + value + "\"\n" + "\tArray key expected : \"" + keys[i] + "\"\n" + "\t" + activeArr + "\n");
						return;
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
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		}
		hasUnsavedChanges = true;
	}

	public boolean getBool(String... keys) {
		class Local {
		}
		;
		if (!validSaveDirectory) {
			return false;
		} else if (keys.length == 0) {
			System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tKey excepted, no key given\n");
			return false;
		}
		String key = keys[keys.length - 1];
		boolean value = false;
		JSONObject activeArr = saveData;
		if (keys.length == 1) {
			if (saveData.has(key)) {
				try {
					value = saveData.getBoolean(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		} else if (keys.length > 1) {
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					StringBuilder chain = new StringBuilder();
					for (String s : keys) {
						chain.append("\"" + s + "\" ");
					}
					System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid key within chain received \"" + keys[i] + "\"\n\tIn chain " + chain + "\n");
					return false;
				}
			}
			try {
				value = activeArr.getBoolean(key);
			} catch (JSONException e) {
				StringBuilder chain = new StringBuilder();
				for (String s : keys) {
					chain.append("\"" + s + "\" ");
				}
				System.err.println(this.getClass() + " : " + Local.class.getEnclosingMethod().getName() + "\n" + "\tInvalid final key received \"" + key + "\"\n\tIn chain " + chain + "\n");
			}
		}
		return value;
	}

	public void deleteArray(String... keys) {
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

	public boolean hasEntry(String... keys) {
		// if(keys.length==0 || !saveData.has(keys[0])){
		// return false;
		// }
		JSONObject curArr = saveData;
		// System.out.println(saveData);
		// saveData.has(keys[0]);
		for (int i = 0; i < keys.length; i++) {
			if (curArr.has(keys[i])) {
				try {
					curArr = curArr.getJSONObject(keys[i]);
				} catch (JSONException e) {
					if (curArr.has(keys[i])) {
						continue;
					}
				}
			} else {
				return false;
			}
		}
		return true;
	}

	public void putStringDefault(String value, String... keys) {
		if (!hasEntry(keys)) {
			putString(value, keys);
		}
	}

	public void putIntDefault(int value, String... keys) {
		if (getInt(keys) == Integer.MIN_VALUE) {
			Main.logger.log(Level.INFO, "ADDING NEW DEFAULT VALUE");
			putInt(value, keys);
		}
	}

	public void putBoolDefault(boolean value, String... keys) {
		if (!hasEntry(keys)) {
			putBool(value, keys);
		}
	}

	public void putDoubleDefault(double value, String... keys) {
		if (!hasEntry(keys)) {
			putDouble(value, keys);
		}
	}

//	public void snapshot() {
//		this.saveDataSnapshot = saveData;
//	}
//
//	public void reloadSnapshot() {
//		this.saveData = saveDataSnapshot;
//	}

	public boolean hasUnsavedChanges() {
		return hasUnsavedChanges;
	}

	public String getClientPath() {
		return this.clientPathString;
	}

}
