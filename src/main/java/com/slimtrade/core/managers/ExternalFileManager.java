package com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.slimtrade.Main;


public class ExternalFileManager {

	//User Specific Data
	public String clientPath;
	public String savePath;
	public boolean validClientPath = false;
	public boolean validSaveDirectory = false;
	public boolean hasCharacterData = false;
	public boolean hasStashData = false;
	
	//Paths
	private String characterStub = "/character.json";
	private String stashStub = "/stash.json";
	private String clientSteamStub =  ":/Program Files (x86)/Steam/steamapps/common/Path of Exile/logs/Client.txt";
	private String clientStandAloneStub =  ":/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt";
	private String[] commonDrives = {"C", "D", "E", "F"};
	private String user = System.getProperty("user.name");
	
	//Internal
	private File file;
	private FileReader fr;
	private BufferedReader br;
	private FileWriter fw;
	private JSONObject json;
	
	public ExternalFileManager(){
		//WINDOWS
		//TODO : 	Inform user of which path is chosen
		//			Warn user if multiple valid paths are found
		//			Add support for 32 bit client
		for(String drive : commonDrives){
			File clientFile = new File(drive + clientSteamStub);
			if(clientFile.exists() && clientFile.isFile()){
				validClientPath = true;
				clientPath = drive + clientSteamStub;
				Main.debugger.log("Valid client path found on " + drive + " drive. (Steam)");
			}
		}
		for(String drive : commonDrives){
			File clientFile = new File(drive + clientStandAloneStub);
			if(clientFile.exists() && clientFile.isFile()){
				validClientPath = true;
				clientPath = drive + clientStandAloneStub;
				Main.debugger.log("Valid client path found on " + drive + " drive. (Stand Alone)");
			}
		}
		//TODO : Switch to something viewable by user later on
		if(!validClientPath){
			Main.debugger.log("Could not find client path.");
		}

		for(String drive : commonDrives){
			File localDirectory = new File(drive + ":/Users/" + user + "/AppData/Local");
			if(localDirectory.exists() && localDirectory.isDirectory()){
				validSaveDirectory = true;
				File saveDirectory = new File(drive + ":/Users/" + user + "/AppData/Local/SlimTrade");
				savePath = saveDirectory.getPath();
				if(!saveDirectory.exists()){
					saveDirectory.mkdirs();
					Main.debugger.log("No save directory found.\nCreating new save directory at " + saveDirectory.getPath());
				}else{
					Main.debugger.log("Previous save directory found.");
					file = new File(savePath + characterStub);
					if(file.exists()) hasCharacterData = true;
					file = new File(savePath + stashStub);
					if(file.exists()) hasStashData = true;
				}
			}
		}

		
		//TODO : Support for other operating systems
		
	}
	
	public void saveCharacterData(String character, String league){
		if(validSaveDirectory){
			json = new JSONObject();
			try {
				json.put("Character", character);
				json.put("League", league);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			try {
				fw = new FileWriter(savePath + characterStub);
				fw.write(json.toString());;
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String[] getCharacterData(){
		file = new File(savePath + characterStub);
		if(!file.exists()){
			return null;
		}
		String[] character = new String[2];
		try {
			fr = new FileReader(savePath + characterStub);
			br = new BufferedReader(fr);
			json = new JSONObject(br.readLine());
			character[0] = json.getString("Character");
			character[1] = json.getString("League");
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return character;
	}
	
	public void saveStashData(int windowX, int windowY, int windowWidth, int windowHeight, int gridX, int gridY, int gridWidth, int gridHeight){
		json = new JSONObject();
		try {
			json.put("windowX", windowX);
			json.put("windowY", windowY);
			json.put("windowWidth", windowWidth);
			json.put("windowHeight", windowHeight);
			json.put("gridX", gridX);
			json.put("gridY", gridY);
			json.put("gridWidth", gridWidth);
			json.put("gridHeight", gridHeight);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			fw = new FileWriter(savePath + stashStub);
			fw.write(json.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getStashData(){
		file = new File(savePath + stashStub);
		if(!file.exists()){
			return null;
		}
		int[] data = new int[8];
		try {
			fr = new FileReader(savePath + stashStub);
			br = new BufferedReader(fr);
			json = new JSONObject(br.readLine());
			data[0] = json.getInt("windowX");
			data[1] = json.getInt("windowY");
			data[2] = json.getInt("windowWidth");
			data[3] = json.getInt("windowHeight");
			data[4] = json.getInt("gridX");
			data[5] = json.getInt("gridY");
			data[6] = json.getInt("gridWidth");
			data[7] = json.getInt("gridHeight");
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return data;
	}

	

}
