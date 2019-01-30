package main.java.com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.slimtrade.gui.windows.InfoDialog;

public class SaveManager {

	public boolean validClientPath = false;
	public boolean validSaveDirectory = false;
	public String clientPathString;
	public String savePathString;
	
	private boolean clientConflict = false;
	
	private String saveStub = "/settings.json";
	private String steamStub =  ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt";
	private String standAloneStub =  ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";
	private String[] commonDrives = {"C", "D", "E", "F", "G", "H"};
//	private String[] commonDrives = {"H"};
	private String user = System.getProperty("user.name");
	
	private JSONObject saveData;
	
	private FileWriter fw;
	private FileReader fr;
	private BufferedReader br;
	
	public SaveManager(){
		//Steam Path
		for(String drive : commonDrives){
			File clientFile = new File(drive + steamStub);
			if(clientFile.exists() && clientFile.isFile()){
				clientPathString = clientFile.getPath();
				validClientPath = true;
			}
		}
		//StandAlone Path
		for(String drive : commonDrives){
			File clientFile = new File(drive + standAloneStub);
			if(clientFile.exists() && clientFile.isFile()){
				if(validClientPath){
					clientPathString = null;
					validClientPath = false;
					clientConflict = true;
				}
				clientPathString = clientFile.getPath();
				validClientPath = true;
			}
		}
		//Save Directory
		for(String drive : commonDrives){
			File localDirectory = new File(drive + ":/Users/" + user + "/AppData/Local");
			if(localDirectory.exists() && localDirectory.isDirectory()){
				validSaveDirectory = true;
				File saveDirectory = new File(drive + ":/Users/" + user + "/AppData/Local/SlimTrade");
				savePathString = saveDirectory.getPath() + saveStub;
				if(!saveDirectory.exists()){
					saveDirectory.mkdir();
				}else{
					
				}
				initSaveData();
			}else{
				//TODO : Prompt user to set a save directory
			}
		}
		
		if(clientConflict){
			System.err.println("Conflict");
			InfoDialog conflictDialog = new InfoDialog("SlimTrade - Warning");
			conflictDialog.addSectionHeader("Multiple Client Paths Found");
			conflictDialog.addText("Client.txt files found for both STEAM and STANDALONE versions of POE.");
			conflictDialog.addText("Please manually select a valid path in the options menu.");
			conflictDialog.finalizeDialog();
		}else if(!validClientPath){
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
	
	private void initSaveData(){
		File save = new File(savePathString);
		if(!save.exists()){
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
	
	private void refreshSaveData(){
		if(!validSaveDirectory){
			return;
		}
		FileReader fr;
		BufferedReader br;
		try {
			System.out.println(savePathString);
			fr = new FileReader(savePathString);
			br = new BufferedReader(fr);
			saveData = new JSONObject(br.readLine());
			br.close();
		} catch (JSONException | IOException e) {
			//TODO : Show user an error here in case they manually corrupt the settings.json file
			//Or just deal with file corruption automatically
			System.err.println("JSON File Corrupted");
			e.printStackTrace();
		}
	}
	
	public void saveString(String key, String value){
		if(!validSaveDirectory){
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
	
	public void saveInt(String key, int value){
		if(!validSaveDirectory){
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
	
	public void saveDouble(String key, double value){
		if(!validSaveDirectory){
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
	
	public void test(){
		if(!validSaveDirectory){
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
	
	public String getNestedString(String array, String key){
		if(!validSaveDirectory){
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
	
	public String getString(String key){
		if(!validSaveDirectory){
			return null;
		}
		refreshSaveData();
		String value = null;
		try {
			value = saveData.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public int getInt(String key){
		if(!validSaveDirectory){
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
	
	public Double getDouble(String key){
		if(!validSaveDirectory){
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
