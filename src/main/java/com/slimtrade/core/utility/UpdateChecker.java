package com.slimtrade.core.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;
import com.slimtrade.App;
import org.json.JSONObject;

public class UpdateChecker {

	// TODO : Switch to /releases/latest with new versionMatchString in order to
	// avoid detecting prereleases
    private static VersionNumber currentVersion;
    private static VersionNumber latestRelease;
    private static VersionNumber latestPreRelease;

    //Github URLs
	private final String latestReleaseURL = "https://github.com/zmilla93/SlimTrade/releases/latest";
    private final String allReleases = "https://github.com/zmilla93/SlimTrade/releases";

    //Regex match strings
	private final String latestReleaseMatchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";
	private final String allReleasesMatchString = "zmilla93.SlimTrade.tree.(v\\d+\\.\\d+\\.\\d+)\"";

	// private ArrayList<String> versions = new ArrayList<String>();

	private boolean newVersion = false;
	private VersionNumber latestVersion;

	public UpdateChecker() {

	}

	public VersionNumber getLatestVersion() {
		return latestVersion;
	}

	public boolean checkForUpdate(){
		return checkForUpdate(false);
	}

	public boolean checkForUpdate(boolean allowPrereleases) {
        URL url = null;
        try {
            System.out.println("Checking for updates...");
            url = new URL("https://api.github.com/repos/zmilla93/slimtrade/tags");
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            while (br.ready()) {
                builder.append(br.readLine());
            }
            System.out.println(builder.toString());
            JsonArray obj = new Gson().fromJson(builder.toString(), JsonArray.class);
            System.out.println("HMM");
            for(int i = 0; i<obj.size(); i++) {
                // Returns all tag names
                JsonElement tag = obj.get(i).getAsJsonObject().get("name");
                System.out.println("tag: " + tag);
                VersionNumber v = new VersionNumber(tag.toString());
                System.out.println(v.toString());
                //TODO : CLEAN + PRE
                if (VersionNumber.isNewVersion(v)) {
                    newVersion = true;
                    if (latestVersion != null) {
                        if (VersionNumber.isNewVersion(v, latestVersion)) {
                            latestVersion = v;
                        }
                    } else {
                        latestVersion = v;
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Checked!");
        return newVersion;
    }

	public boolean checkForUpdateOld(boolean allowPrereleases) {
		InputStream inputStream = null;
		try {
			URL url;
			if (allowPrereleases || App.debugMode) {
                System.out.println("debug check");
				url = new URL(allReleases);
			} else {
				url = new URL(latestReleaseURL);
			}
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			App.logger.log(Level.WARNING, "Error while connecting to github.");
			return false;
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder webText = new StringBuilder();
		try {
			while (br.ready()) {
				webText.append(br.readLine());
			}
		} catch (IOException e) {
			App.logger.log(Level.WARNING, "Error while parsing data from github.");
			return false;
		}
		Pattern pattern = null;
		if (allowPrereleases) {
			pattern = Pattern.compile(allReleasesMatchString);
		} else {
			pattern = Pattern.compile(latestReleaseMatchString);
		}
		Matcher matcher = pattern.matcher(webText.toString());
		System.out.println(matcher.matches());
		while (matcher.find()) {
			VersionNumber v = new VersionNumber(matcher.group(1));
			System.out.println(v.toString());
			if (VersionNumber.isNewVersion(v)) {
				newVersion = true;
				if (latestVersion != null) {
					if (VersionNumber.isNewVersion(v, latestVersion)) {
						latestVersion = v;
					}
				} else {
					latestVersion = v;
				}
			}
		}


		//TODO : Implement new update checker
//        URL conn = null;
//        try {
//            conn = new URL("https://api.github.com/repos/zmilla93/slimtrade/tags");
//            URLConnection yc = conn.openConnection();
//            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



		return newVersion;
	}

}
