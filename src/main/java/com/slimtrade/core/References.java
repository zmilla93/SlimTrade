package com.slimtrade.core;

import java.awt.*;

public class References {

    // Regex
    public static final String REGEX_CLIENT_PREFIX = "((?<date>\\d{4}\\/\\d{2}\\/\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}))?.*@(?<messageType>От кого|\\S+) (?<guildName><.+> )?(?<playerName>.+):(\\s+)(?<message>";
    public static final String REGEX_QUICK_PASTE_PREFIX = "@(?<guildName><.+> )?(?<playerName>.+)(\\s+)(?<message>";
    public static final String REGEX_JOINED_AREA_PREFIX = "(.+ : (?<playerName>.+) ";
    public static final String REGEX_SUFFIX = ")";

    public static final int BUTTON_SPACER = 5;

    public static final Point DEFAULT_MESSAGE_LOCATION = new Point(800, 0);

}
