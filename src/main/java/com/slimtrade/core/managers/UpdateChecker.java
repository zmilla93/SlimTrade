package main.java.com.slimtrade.core.managers;

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
	
	public UpdateChecker() {

		InputStream inputStream = null;

		try {
			URL url = new URL(releases);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			Main.logger.log(Level.WARNING, "Error while connecting to github.");
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder webText = new StringBuilder();
		try {
			while (br.ready()) {
				webText.append(br.readLine());
			}
		} catch (IOException e) {
			Main.logger.log(Level.WARNING, "Error while parsing data from github.");
			return;
		}
		Pattern pattern = Pattern.compile(versionMatchString);
		Matcher matcher = pattern.matcher(webText.toString());
		while(matcher.find()){
			versions.add(matcher.group(1));
			System.out.println("Version Found + " + matcher.group(1));
		}
	}
	
	

}
