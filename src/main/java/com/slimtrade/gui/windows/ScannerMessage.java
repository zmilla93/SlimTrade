package main.java.com.slimtrade.gui.windows;

public class ScannerMessage {
	
	String name;
	String searchTerms;
	String ignoreTerms;
	
	public ScannerMessage(String name, String searchTerms, String ignoreTerms) {
		this.name = name;
		this.searchTerms = searchTerms;
		this.ignoreTerms = ignoreTerms;
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
	}
	public String getIgnoreTerms() {
		return ignoreTerms;
	}
	public void setIgnoreTerms(String ignoreTerms) {
		this.ignoreTerms = ignoreTerms;
	}
	
}
