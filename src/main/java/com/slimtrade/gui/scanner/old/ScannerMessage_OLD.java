package com.slimtrade.gui.scanner.old;

public class ScannerMessage_OLD {

	protected String name;
	protected String searchTermsRaw;
	protected String ignoreTermsRaw;
	protected String[] searchTermsArray;
	protected String[] ignoreTermsArray;
	protected String clickLeft;
	protected String clickRight;

	public ScannerMessage_OLD(String name, String searchTerms, String ignoreTerms) {
		this.name = name;
		this.searchTermsRaw = searchTerms;
		this.ignoreTermsRaw = ignoreTerms;
		this.clickLeft = clickLeft;
		this.clickRight = clickRight;
		
		this.searchTermsArray = cleanArray(searchTerms);
		this.ignoreTermsArray = cleanArray(ignoreTerms);
	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public void setSearchTerms(String searchTerms) {
		this.searchTermsRaw = searchTerms;
		this.searchTermsArray = searchTerms.split("\\s+");
	}
	public String getIgnoreTerms() {
		return ignoreTermsRaw;
	}
	public void setIgnoreTerms(String ignoreTerms) {
		this.ignoreTermsRaw = ignoreTerms;
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
