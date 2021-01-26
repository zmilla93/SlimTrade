package com.slimtrade.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class References {

    public static final String APP_NAME = "SlimTrade";
    public static final String AUTHOR_NAME = "zmilla93";
    public static final String POE_WINDOW_TITLE = "Path of Exile";
    private static String APP_VERSION = null;
    public static final int DEFAULT_IMAGE_SIZE = 18;
    // Currently the russian from message (От кого) is handled separately from other to/from messages due to being two words
    public static final String REGEX_CLIENT_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*@(?<messageType>От кого|\\S+) (?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String REGEX_QUICK_PASTE_PREFIX = "@(?<guildName><.+> )?(?<playerName>.+)(\\s+)(?<message>";
    public static final String REGEX_SCANNER_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*(#|\\$)(?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String GITHUB = "https://github.com/zmilla93/SlimTrade";
    public static final String DISCORD = "https://discord.gg/yKdExMe";
    public static final String PAYPAL = "https://www.paypal.me/zmilla93";

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
