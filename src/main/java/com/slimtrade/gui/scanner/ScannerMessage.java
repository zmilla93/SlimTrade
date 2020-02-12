package com.slimtrade.gui.scanner;

import com.slimtrade.core.SaveSystem.MacroButton;

import java.util.ArrayList;

public class ScannerMessage {

	public String name;
	public String searchTermsRaw;
	public String ignoreTermsRaw;
	public String thankLeft;
	public String thankRight;
	public String[] searchTermsArray;
	public String[] ignoreTermsArray;
	public ArrayList<MacroButton> macroButtons;
	
	public ScannerMessage(String name, String searchTermsRaw, String ignoreTermsRaw, String thankLeft, String thankRight, ArrayList<MacroButton> macroButtons) {
		this.name = name;
		this.searchTermsRaw = searchTermsRaw;
		this.ignoreTermsRaw = ignoreTermsRaw;
		this.thankLeft = thankLeft;
		this.thankRight = thankRight;
		this.macroButtons = macroButtons;
		this.searchTermsArray = cleanArray(searchTermsRaw);
		this.ignoreTermsArray = cleanArray(ignoreTermsRaw);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public void setSearchTermsRaw(String searchTermsRaw) {
		this.searchTermsRaw = searchTermsRaw;
		this.searchTermsArray = searchTermsRaw.split("\\s+");
	}
	public String getIgnoreTermsRaw() {
		return ignoreTermsRaw;
	}
	public void setIgnoreTermsRaw(String ignoreTermsRaw) {
		this.ignoreTermsRaw = ignoreTermsRaw;
	}
	
	public String toString(){
		return this.name;
	}
	
	private String[] cleanArray(String input){
		if(input.replaceAll("\\s+", "").equals("")){
			return null;
		}
		String[] arr = input.split("\\n|,|;");
		int i = 0;
		for(String s : arr){
			String curTerm = s.trim().replaceAll("\\s+", " ");
			if(!curTerm.replaceAll("\\s+", "").equals("")){
				arr[i] = curTerm;
			}
			i++;
		}
		return arr;
	}
	
}
