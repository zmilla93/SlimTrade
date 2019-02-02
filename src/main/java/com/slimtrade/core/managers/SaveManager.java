package main.java.com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

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
	private String[] commonDrives = { "C", "D", "E", "F", "G", "H" };
	// private String[] commonDrives = {"H"};
	private String user = System.getProperty("user.name");

	private JSONObject saveData;

	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;

	public SaveManager() {
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
				initSaveData();
			} else {
				// TODO : Prompt user to set a save directory
			}
		}

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
		File save = new File(savePathString);
		if (!save.exists()) {
			try {
				fw = new FileWriter(save);
				JSONObject json = new JSONObject();
				fw.write(json.toString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void refreshSaveData() {
		if (!validSaveDirectory) {
			return;
		}
		FileReader fr;
		BufferedReader br;
		try {
			// System.out.println(savePathString);
			fr = new FileReader(savePathString);
			br = new BufferedReader(fr);
			saveData = new JSONObject(br.readLine());
			br.close();
		} catch (JSONException | IOException e) {
			// TODO : Show user an error here in case they manually corrupt the
			// settings.json file
			// Or just deal with file corruption automatically
			System.err.println("JSON File Corrupted");
			e.printStackTrace();
		}
	}

	public void saveString(String value, String... keys) {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		String key = keys[keys.length - 1];
		JSONObject activeArr;
		if (keys.length > 1) {
			// Get existing arrays, or build new ones
			activeArr = saveData;
			for (int i = 0; i < keys.length - 1; i++) {
				if (activeArr.has(keys[i])) {
					try {
						arr.add(activeArr.getJSONObject(keys[i]));
						activeArr = activeArr.getJSONObject(keys[i]);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else {
					arr.add(new JSONObject());
				}
				System.out.println(keys[i]);
			}
			try {
				arr.get(arr.size() - 1).put(key, value);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			for (int i = keys.length - 2; i >= 1; i--) {
				System.out.println(arr.get(i));
				try {
					arr.get(i - 1).put(keys[i], arr.get(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// System.out.println(keys[i]);
			}
			// System.out.println("Saved Arrays : ");
			for (JSONObject o : arr) {
				System.out.println(o);
			}
			System.out.println("FINAL ARRAY : ");
			System.out.println(arr.get(0));
			try {
				saveData.put(keys[0], arr.get(0));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			try {
				saveData.put(key, value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveStringProper(String... strings) {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		String key = strings[strings.length - 2];
		String value = strings[strings.length - 1];
		ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
		if (strings.length > 2) {
			try {
				if (saveData.has(strings[0])) {
					arr.add((JSONObject) saveData.get(strings[0]));
					for (int i = 1; i < strings.length - 2; i++) {
						if (arr.get(i - 1).has(strings[i])) {
							arr.add(arr.get(i - 1).getJSONObject(strings[i]));
						} else {
							arr.add(new JSONObject());
						}
					}
				} else {
					for (int i = 0; i < strings.length - 2; i++) {
						arr.add(new JSONObject());
					}
				}
				System.out.println("Looping...");
				System.out.println(arr.get(strings.length - 3));
				arr.get(strings.length - 3).put(key, value);
				for (int i = strings.length - 3; i > 2; i--) {
					System.out.println(arr.get(i));
					System.out.println(strings[i]);
					arr.get(i - 1).put(strings[i], arr.get(i));
				}
				saveData.put(strings[0], arr.get(0));

				System.out.println("FINAL : " + arr.get(0));
				System.out.println("KEY : " + strings[0]);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				saveData.put(strings[0], strings[1]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getStringProper(String... strings) {
		if (!validSaveDirectory) {
			return null;
		}
		refreshSaveData();
		String key = strings[strings.length - 1];
		String value = null;
		JSONObject curArr = null;
		if (strings.length > 1) {
			try {
				curArr = saveData.getJSONObject(strings[0]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			for (int i = 1; i < strings.length - 1; i++) {
				try {
					curArr = curArr.getJSONObject(strings[i]);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			try {
				value = curArr.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				value = saveData.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public void saveArray(String key, JSONObject value) {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		try {
			saveData.put(key, value);
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JSONObject getArray(String key) {
		if (!validSaveDirectory) {
			return null;
		}
		refreshSaveData();
		JSONObject arr = null;
		try {
			arr = (JSONObject) saveData.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return arr;
	}

	// public void saveString(String key, String value) {
	// if (!validSaveDirectory) {
	// return;
	// }
	// refreshSaveData();
	// try {
	// saveData.put(key, value);
	// fw = new FileWriter(savePathString);
	// fw.write(saveData.toString());
	// fw.close();
	// } catch (JSONException | IOException e) {
	// e.printStackTrace();
	// }
	// }

	public void saveInt(String key, int value) {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		try {
			saveData.put(key, value);
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	public void saveDouble(String key, double value) {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		try {
			saveData.put(key, value);
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}

	public void exampleArray() {
		if (!validSaveDirectory) {
			return;
		}
		refreshSaveData();
		JSONObject arr1 = new JSONObject();
		JSONObject arr2 = new JSONObject();
		JSONObject arr3 = new JSONObject();
		JSONObject arr4 = new JSONObject();
		JSONObject arr5 = new JSONObject();
		try {
			arr5.put("Example Key", "Example Value");
			arr4.put("Array4", arr5);
			arr3.put("Array3", arr4);
			arr2.put("Array2", arr3);
			arr1.put("Array1", arr2);
			saveData.put("Example Nest", arr1);
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void test() {
		if (!validSaveDirectory) {
			return;
		}
		JSONObject subArr = new JSONObject();
		refreshSaveData();
		try {
			subArr.put("bool", true);
			subArr.put("test", "TEST");
			saveData.put("Sub", subArr);
			fw = new FileWriter(savePathString);
			fw.write(saveData.toString());
			fw.close();
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNestedString(String array, String key) {
		if (!validSaveDirectory) {
			return null;
		}
		refreshSaveData();
		String value = null;
		try {
			JSONObject arr = saveData.getJSONObject(array);
			value = arr.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

	// public String getString(String key) {
	// if (!validSaveDirectory) {
	// return null;
	// }
	// refreshSaveData();
	// String value = null;
	// try {
	// value = saveData.getString(key);
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// return value;
	// }

	public int getInt(String key) {
		if (!validSaveDirectory) {
			return -1;
		}
		refreshSaveData();
		int value = -1;
		try {
			value = saveData.getInt(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

	public Double getDouble(String key) {
		if (!validSaveDirectory) {
			return null;
		}
		refreshSaveData();
		Double value = null;
		try {
			value = saveData.getDouble(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}

}
