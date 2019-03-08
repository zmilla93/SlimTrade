package main.java.com.slimtrade.core.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.slimtrade.core.Main;

public class UpdateChecker {

	private final String releases = "https://github.com/zmilla93/SlimTrade/releases/";
	private final String versionMatchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";

	private ArrayList<String> versions = new ArrayList<String>();

	private boolean newVersion = false;
	private VersionNumber latestVersion;

	public UpdateChecker() {
		
	}
	
	public VersionNumber getLatestVersion(){
		return latestVersion;
	}

	public boolean checkForUpdate() {
		InputStream inputStream = null;

		try {
			URL url = new URL(releases);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			Main.logger.log(Level.WARNING, "Error while connecting to github.");
			return false;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder webText = new StringBuilder();
		try {
			while (br.ready()) {
				webText.append(br.readLine());
			}
		} catch (IOException e) {
			Main.logger.log(Level.WARNING, "Error while parsing data from github.");
			return false;
		}
		Pattern pattern = Pattern.compile(versionMatchString);
		Matcher matcher = pattern.matcher(webText.toString());
		// System.out.println(webText.toString());
		System.out.println(matcher.matches());
		while (matcher.find()) {
			VersionNumber v = new VersionNumber(matcher.group(1));
			System.out.println(v.toString());
			if (VersionNumber.isNewVersion(v)) {
				newVersion = true;
				if(latestVersion != null){
					if(VersionNumber.isNewVersion(v, latestVersion)){
						latestVersion = v;
					}
				}else{
					latestVersion = v;
				}
			}
			// versions.add(matcher.group(1));
			// System.out.println("Version Found + " + matcher.group(1));
		}
		 return newVersion;
	}

}
