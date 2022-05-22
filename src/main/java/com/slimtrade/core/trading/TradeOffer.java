package com.slimtrade.core.trading;

import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;

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
    public String stashTabName;
    public int stashTabX = 0;
    public int stashTabY = 0;
    public String bonusText;
    private int stashColorIndex = -1;
    private StashTabColor stashTabColor;
    public boolean isBulkTrade;

    // Static function instead of constructors to avoid allocation if no trade is found.
    public static TradeOffer getTradeFromMessage(String input) {
        TradeOffer trade = null;
        for (LangRegex l : LangRegex.values()) {
            if (!input.contains(l.wantToBuy)) continue;
            for (Pattern pattern : l.tradeOfferPatterns) {
                Matcher matcher = pattern.matcher(input);
                if (!matcher.matches()) continue;
                trade = getTradeFromMatcher(matcher);
                if (trade.isBulkTrade) System.out.println("BULK:" + trade.itemName);
                break;
            }
        }
        return trade;
    }

    public static TradeOffer getTradeFromQuickPaste(String input) {
        TradeOffer trade = null;
        for (LangRegex l : LangRegex.values()) {
            if (!input.contains(l.wantToBuy)) continue;
            for (Pattern pattern : l.quickPastePatterns) {
                Matcher matcher = pattern.matcher(input);
                if (!matcher.matches()) continue;
                trade = getTradeFromMatcher(matcher);
                break;
            }
        }
        return trade;
    }

    private static TradeOffer getTradeFromMatcher(Matcher matcher) {
        TradeOffer trade;
        trade = new TradeOffer();
        trade.date = matcher.group("date").replaceAll("/", "-");
        trade.time = matcher.group("time");
        trade.offerType = getMessageType(matcher.group("messageType"));
        trade.guildName = matcher.group("guildName");
        trade.playerName = matcher.group("playerName");
        trade.itemName = matcher.group("itemName");
        trade.itemQuantity = cleanDouble(cleanResult(matcher, "itemQuantity"));
        trade.priceTypeString = cleanResult(matcher, "priceType");
        trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
        trade.stashTabName = cleanResult(matcher, "stashtabName");
        trade.stashTabX = cleanInt(cleanResult(matcher, "stashX"));
        trade.stashTabY = cleanInt(cleanResult(matcher, "stashY"));
        trade.bonusText = cleanResult(matcher, "bonusText");
        trade.isBulkTrade = trade.itemName.contains(",");
        return trade;
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

    public StashTabColor getStashTabColor() {
        // FIXME : Switch to hashmap
        if (stashColorIndex == -1) {
            stashColorIndex = 0;
            stashTabColor = StashTabColor.ZERO;
            if (stashTabName != null) {
                for (StashTabData data : SaveManager.settingsSaveFile.data.stashTabs) {
                    if (data.stashTabName == null || data.stashTabName.isBlank()) continue;
                    if (data.matchType == MatchType.EXACT_MATCH) {
                        if (stashTabName.equals(data.stashTabName)) {
                            stashColorIndex = data.stashColorIndex;
                            stashTabColor = StashTabColor.values()[data.stashColorIndex];
                            break;
                        }
                    } else if (data.matchType == MatchType.CONTAINS_TEXT) {
                        if (stashTabName.contains(data.stashTabName)) {
                            stashColorIndex = data.stashColorIndex;
                            stashTabColor = StashTabColor.values()[data.stashColorIndex];
                            break;
                        }
                    }
                }
            }
        }
        return stashTabColor;
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

    public static TradeOffer getExampleTrade(TradeOfferType type) {
        TradeOffer exampleTrade = new TradeOffer();
        exampleTrade.offerType = type;
        exampleTrade.playerName = "ExamplePlayer123";
        exampleTrade.itemName = "Example Item";
        exampleTrade.stashTabName = "~price 1 chaos";
        int zero = ThreadLocalRandom.current().nextInt(0, 1);
        if (zero == 1) {
            exampleTrade.itemQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        }
        exampleTrade.priceTypeString = "Chaos";
        exampleTrade.priceQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        return exampleTrade;
    }

}
