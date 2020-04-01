package com.slimtrade.enums;

import java.util.regex.Pattern;

public enum ParserRegex {

//    SEARCH("", "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2})) \\d+ [\\d\\w]+ \\[[\\w\\s\\d]+\\] [#$](<.+> )?(\\S+): (.+)"),
    ENGLISH_1("like to buy", "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(.+): (((Hi, )?(I would|I'd) like to buy your|wtb) ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\\?\\\")?((.+)\\\\?\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)"),
    ENGLISH_2("wtb", "((\\d{4}\\/\\d{2}\\/\\d{2}) (\\d{2}:\\d{2}:\\d{2}))?.*@(To|From) (<.+> )?(.+): (((Hi, )?(I would|I'd) like to buy your|wtb) ([\\d.]+)? ?(.+) (NULL)?(NULL)?(NULL)?in (\\w+( \\w+)?) ?([(]stash tab \\\\?\\\")?((.+)\\\\?\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)"),
    ;

    private String contains;
    private Pattern pattern;

    ParserRegex (String contains, String regex) {
        this.contains = contains;
        this.pattern = Pattern.compile(regex);
    }

    public String getContains() {
        return this.contains;
    }


    public Pattern getPattern() {
        return this.pattern;
    }

}
