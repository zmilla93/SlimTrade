package main.java.com.slimtrade.gui.scanner;

public class ScannerMessage {
	
	protected String name;
	protected String searchTerms;
	protected String ignoreTerms;
	protected String[] searchTermsArray;
	protected String[] ignoreTermsArray;
	protected String clickLeft;
	protected String clickRight;
	
	public ScannerMessage(String name, String searchTerms, String ignoreTerms, String clickLeft, String clickRight) {
		this.name = name;
		this.searchTerms = searchTerms;
		this.ignoreTerms = ignoreTerms;
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
	public String getSearchTerms() {
		return searchTerms;
	}
	public void setSearchTerms(String searchTerms) {
		this.searchTerms = searchTerms;
		this.searchTermsArray = searchTerms.split("\\s+");
	}
	public String getIgnoreTerms() {
		return ignoreTerms;
	}
	public void setIgnoreTerms(String ignoreTerms) {
		this.ignoreTerms = ignoreTerms;
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
			arr[i] = s.trim().replaceAll("\\s+", " ");
			i++;
		}
		return arr;
	}
	
}
