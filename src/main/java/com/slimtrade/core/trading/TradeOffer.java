package com.slimtrade.core.trading;

import com.slimtrade.App;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeOffer {

    public enum TradeOfferType {INCOMING, OUTGOING, CHAT_SCANNER, UNKNOWN}

    public TradeOfferType offerType;
    public String date;
    public String time;
    public String guildName;
    public String playerName;
    public String itemName;
    public double itemQuantity;
    public String priceTypeString;
    public double priceQuantity;
    public String stashtabName;
    public int stashtabX = 0;
    public int stashtabY = 0;
    public String bonusText;

    public static TradeOffer getTradeOffer(String input) {
        TradeOffer trade = null;
        for (LangRegex l : App.languageManager.langList) {
            if (input.contains(l.wantToBuy)) {
                for (Pattern pattern : l.quickPastePatterns) {
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.matches()) {
                        trade = new TradeOffer();
                        trade.guildName = matcher.group("guildName");
                        trade.playerName = matcher.group("playerName");
                        trade.itemName = matcher.group("itemName");
                        trade.itemQuantity = cleanDouble(cleanResult(matcher, "itemQuantity"));
                        trade.priceTypeString = cleanResult(matcher, "priceType");
                        trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
                        trade.stashtabName = cleanResult(matcher, "stashtabName");
                        trade.stashtabX = cleanInt(cleanResult(matcher, "stashX"));
                        trade.stashtabY = cleanInt(cleanResult(matcher, "stashY"));
                        trade.bonusText = cleanResult(matcher, "bonusText");
                        break;
                    }
                }
            }
        }
        return trade;
    }

    public static TradeOffer getExampleTrade(TradeOfferType type) {
        TradeOffer exampleTrade = new TradeOffer();
        exampleTrade.offerType = type;
        exampleTrade.playerName = "ExamplePlayer123";
        exampleTrade.itemName = "Example Item";
        exampleTrade.stashtabName = "~price 1 chaos";
        int zero = ThreadLocalRandom.current().nextInt(0, 1);
        if (zero == 1) {
            exampleTrade.itemQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        }
        exampleTrade.priceTypeString = "Chaos";
        exampleTrade.priceQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        return exampleTrade;
    }

    private static TradeOffer.TradeOfferType getMessageType(String s) {
        // TODO : Move to LangRegex
        switch (s.toLowerCase()) {
            case "from":
            case "來自":     // Chinese
            case "de":      // French, Portuguese & Spanish
            case "von":     // German
            case "от кого": // Russian
            case "จาก":     // Thai
                return TradeOffer.TradeOfferType.INCOMING;
            case "to":
            case "向":      // Chinese
            case "à":       // French
            case "an":      // German
            case "para":    // Portuguese & Spanish
            case "кому":    // Russian
            case "ถึง":      // Thai
                return TradeOffer.TradeOfferType.OUTGOING;

            default:
                return TradeOffer.TradeOfferType.UNKNOWN;
        }
    }

    private static String cleanResult(Matcher matcher, String text) {
        try {
            return matcher.group(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private static int cleanInt(String text) {
        if (text == null) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    private static double cleanDouble(String text) {
        if (text == null) {
            return 0;
        }
        text = text.replaceAll(",", ".");
        return Double.parseDouble(text);
    }

}
