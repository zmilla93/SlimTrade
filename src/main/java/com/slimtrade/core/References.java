package com.slimtrade.core;

import java.awt.*;
import java.util.regex.Pattern;

public class References {

    // Regex
    public static final String REGEX_CLIENT_DATA = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*] ";
    public static final String REGEX_CLIENT_PREFIX = REGEX_CLIENT_DATA + "@(?<messageType>От кого|\\S+) (?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String REGEX_CLIENT_CHAT_PREFIX = REGEX_CLIENT_DATA + "[#$]?(?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>.+";
    public static final String REGEX_QUICK_PASTE_PREFIX = "@(?<guildName><.+> )?(?<playerName>.+)(\\s+)(?<message>";
    public static final String REGEX_JOINED_AREA_PREFIX = "(.+ : (?<playerName>.+) ";
    public static final String REGEX_SUFFIX = ")";
    public static final String APP_PREFIX = "SLIMTRADEAPP::";

    public static final Pattern chatPatten = Pattern.compile(REGEX_CLIENT_CHAT_PREFIX + REGEX_SUFFIX);
    public static final Pattern whisperPattern = Pattern.compile(REGEX_CLIENT_PREFIX + ".*" + REGEX_SUFFIX);

    public static final int BUTTON_SPACER = 5;
    public static final int COMPONENT_SPACER = 10;

    public static final Point DEFAULT_MESSAGE_LOCATION = new Point(800, 0);
    public static final Point DEFAULT_MENUBAR_LOCATION = new Point(0, 0);

}
