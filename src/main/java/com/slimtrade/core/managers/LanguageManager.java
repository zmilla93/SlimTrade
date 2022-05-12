package com.slimtrade.core.managers;

import com.slimtrade.core.trading.LangRegex;

import java.util.ArrayList;

public class LanguageManager {

    public ArrayList<LangRegex> langList;

    public LanguageManager() {
        langList = new ArrayList<>();

        // English
        LangRegex englishKeywords = new LangRegex();
        englishKeywords.wantToBuy = "like to buy";
        englishKeywords.joinedArea = "has joined the area";
        englishKeywords.tradeOffers = new String[]{
                "Hi, I'd like to buy your ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) for my ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+).",
                "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
                "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
                "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+)\\.?(?<bonusText>.+)?",
                "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?",
        };
        englishKeywords.compile();
        langList.add(englishKeywords);

    }

}
