package com.slimtrade.enums;

import java.util.regex.Pattern;

import static com.slimtrade.core.References.REGEX_PREFIX;

public enum LangRegex {

    ENGLISH("like to buy", new Pattern[]{
            Pattern.compile(REGEX_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "Hi, I'd like to buy your ((?<itemQuantity>\\d+(.\\d+)?) (?<itemName>.+)) for my ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+)."),
            Pattern.compile(REGEX_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) in (?<league>.+)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?"),
    }),
    //    FRENCH,
//    GERMAN,
//    KOREAN,
//    PORTUGUESE,
    RUSSIAN("хочу купить у вас", new Pattern[]{
            Pattern.compile(REGEX_PREFIX + "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "Здравствуйте, хочу купить у вас ((?<itemQuantity>\\d+(.\\d+)?) (?<itemName>.+)) за ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+(.\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?"),
            Pattern.compile(REGEX_PREFIX + "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+)\\.?(?<bonusText>.+)?"),
    }),
//    SPANISH,
//    THAI,
    ;

    public final String CONTAINS_TEXT;
    public final Pattern[] PATTERNS;

    LangRegex(String text, Pattern[] patterns) {
        this.CONTAINS_TEXT = text;
        this.PATTERNS = patterns;
    }


}
