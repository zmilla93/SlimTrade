package github.zmilla93.core.utility;

import github.zmilla93.modules.stopwatch.Stopwatch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Can make http requests using the proper headers. Also supports cookies.
 */
public class HttpRequester {

    private HttpRequester() {

    }

    public static String getPageContents(String url) {
        return getPageContents(url, null);
    }

    public static String getPageContents(String url, String[] cookies) {
        Stopwatch.start();
        try {
            URLConnection conn = getUrlConnection(url, cookies);
            InputStream inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            while (reader.ready()) builder.append(reader.readLine());
            return builder.toString();
        } catch (IOException e) {
            return null;
        }
    }

    private static URLConnection getUrlConnection(String url, String[] cookies) throws IOException {
        URLConnection conn = new URL(url).openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");
        if (cookies != null) {
            StringBuilder cookieBuilder = new StringBuilder();
            for (int i = 0; i < cookies.length; i += 2) {
                cookieBuilder.append(cookies[i]);
                cookieBuilder.append("=");
                cookieBuilder.append(cookies[i + 1]);
                if (i + 2 < cookies.length) cookieBuilder.append("; ");
            }
            String cookie = cookieBuilder.toString();
            conn.setRequestProperty("Cookie", cookie);
        }
        int timeout = 5000;
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setUseCaches(false);
        return conn;
    }

}
