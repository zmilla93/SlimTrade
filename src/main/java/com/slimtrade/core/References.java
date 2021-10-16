package com.slimtrade.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class References {

    // App Data
    public static final String APP_NAME = "SlimTrade";
    public static final String AUTHOR_NAME = "zmilla93";
    private static String APP_VERSION = null;

    // Internal
    public static final String POE_WINDOW_TITLE = "Path of Exile";
    public static final int DEFAULT_IMAGE_SIZE = 18;
    // Currently the russian from message (От кого) is handled separately from other to/from messages due to being two words
    public static final String REGEX_CLIENT_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*@(?<messageType>От кого|\\S+) (?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String REGEX_QUICK_PASTE_PREFIX = "@(?<guildName><.+> )?(?<playerName>.+)(\\s+)(?<message>";
    public static final String REGEX_SCANNER_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*(#|\\$)(?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String GITHUB = "https://github.com/zmilla93/SlimTrade";
    public static final String DISCORD = "https://discord.gg/yKdExMe";
    public static final String PAYPAL = "https://www.paypal.me/zmilla93";

    public static final String KOREAN_CHAR_REGEX = ".*[\\u3040-\\u30ff\\u3400-\\u4dbf\\u4e00-\\u9fff\\uf900-\\ufaff\\uff66-\\uff9f\\u3131-\\uD79D].*";
    public static final String THAI_CHAR_REGEX = ".*[\\u0E00-\\u0E7F].*";

    public static String getAppVersion() {
        if (APP_VERSION == null) {
            final Properties properties = new Properties();
            try {
                InputStream stream = References.class.getClassLoader().getResourceAsStream("project.properties");
                properties.load(stream);
                assert stream != null;
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            APP_VERSION = "v" + properties.getProperty("version");
        }
        return APP_VERSION;
    }

}
