package com.slimtrade.core.trading;

import com.slimtrade.core.References;

import java.util.regex.Pattern;

public class LangRegex {

    // Input
    public String joinedArea;
    public String wantToBuy;
    public String[] tradeOffers;

    // Generated
    public Pattern[] tradeOfferPatterns;
    public Pattern[] quickPastePatterns;

    public void compile() {
        tradeOfferPatterns = new Pattern[tradeOffers.length];
        quickPastePatterns = new Pattern[tradeOffers.length];
        for (int i = 0; i < tradeOffers.length; i++) {
            String s = References.REGEX_CLIENT_PREFIX + tradeOffers[i] + References.REGEX_SUFFIX;
            tradeOfferPatterns[i] = Pattern.compile(s);
            quickPastePatterns[i] = Pattern.compile(References.REGEX_QUICK_PASTE_PREFIX + tradeOffers[i] + References.REGEX_SUFFIX);
        }
    }

}
