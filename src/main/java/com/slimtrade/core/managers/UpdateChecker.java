package main.java.com.slimtrade.core.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.slimtrade.core.Main;

public class UpdateChecker {

	public UpdateChecker() {
		URL url = null;
		URLConnection connection = null;
		InputStream in = null;

		try {
			url = new URL("https://github.com/zmilla93/SlimTrade/releases/");
			connection = url.openConnection();
			connection.setConnectTimeout(5000);
			System.out.println(connection);
			in = connection.getInputStream();

		} catch (IOException e) {
			Main.logger.log(Level.WARNING, "Error while attempting to get version list from github.");
			return;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in));
//		FileWriter fw = null;
//		try {
//			fw = new FileWriter("D:/Programming/Test/test.txt");
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}

		StringBuilder webText = new StringBuilder();
		try {
			while (br.ready()) {
				webText.append(br.readLine());
			}
//			fw.write(webText.toString());
//			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		String matchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";
		Pattern pattern = Pattern.compile(matchString);
		Matcher matcher = pattern.matcher(webText.toString());

		while(matcher.find()){
			System.out.println("Version Found + " + matcher.group(1));
		}

	}

}
