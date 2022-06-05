package com.slimtrade.core.trading;

import com.slimtrade.core.data.SaleItem;
import com.slimtrade.core.data.StashTabData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeOffer {

    public enum TradeOfferType {UNKNOWN, INCOMING, OUTGOING, CHAT_SCANNER, QUICK_PASTE}

    public TradeOfferType offerType;
    public String date;
    public String time;
    public String guildName;
    public String playerName;
    public String itemName;
    public String itemNameLower;
    public int itemQuantity;
    public String itemQuantityString;
    public String priceTypeString;
    public double priceQuantity;
    public String stashTabName;
    public int stashTabX = 0;
    public int stashTabY = 0;
    public String bonusText;
    private int stashColorIndex = -1;
    private StashTabColor stashTabColor;
    public boolean isBulkTrade;

    private static DecimalFormat formatter = new DecimalFormat("0.00");

    // Static function instead of constructors to avoid allocation if no trade is found.
    public static TradeOffer getTradeFromMessage(String input) {
        for (LangRegex l : LangRegex.values()) {
            if (!input.contains(l.wantToBuy)) continue;
            for (Pattern pattern : l.tradeOfferPatterns) {
                Matcher matcher = pattern.matcher(input);
                if (!matcher.matches()) continue;
                return getTradeFromMatcher(matcher);
            }
        }
        return null;
    }

    public static TradeOffer getTradeFromQuickPaste(String input) {
        for (LangRegex l : LangRegex.values()) {
            if (!input.contains(l.wantToBuy)) continue;
            for (Pattern pattern : l.quickPastePatterns) {
                Matcher matcher = pattern.matcher(input);
                if (!matcher.matches()) continue;
                return getTradeFromMatcher(matcher);
            }
        }
        return null;
    }

    public static String cleanItemQuantity(double itemQuantity) {
        if (itemQuantity % 1 == 0) return String.format("%.0f", itemQuantity);
        else return Double.toString(itemQuantity);
    }

    private static TradeOffer getTradeFromMatcher(Matcher matcher) {
        TradeOffer trade;
        trade = new TradeOffer();
        trade.date = cleanResult(matcher, "date");
        if (trade.date != null) trade.date = trade.date.replaceAll("/", "-");
        trade.time = cleanResult(matcher, "time");
        trade.offerType = getMessageType(cleanResult(matcher, "messageType"));
        trade.guildName = matcher.group("guildName");
        trade.playerName = matcher.group("playerName");
        trade.itemName = matcher.group("itemName");
        trade.itemNameLower = trade.itemName.toLowerCase();
        trade.itemQuantity = cleanInt(cleanResult(matcher, "itemQuantity"));
        trade.itemQuantityString = cleanItemQuantity(trade.itemQuantity);
        trade.priceTypeString = cleanResult(matcher, "priceType");
        trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
        trade.stashTabName = cleanResult(matcher, "stashtabName");
        trade.stashTabX = cleanInt(cleanResult(matcher, "stashX"));
        trade.stashTabY = cleanInt(cleanResult(matcher, "stashY"));
        trade.bonusText = cleanResult(matcher, "bonusText");
        trade.isBulkTrade = trade.itemName.matches(".+, \\d+ .+");
        return trade;
    }

    public ArrayList<SaleItem> getItems() {
        if (isBulkTrade) {
//            String quantity;
//            if (itemQuantity % 1 == 0) quantity = String.format("%,.0f", itemQuantity);
//            else quantity = String.format("%,.2f", itemQuantity);
            String fixed = itemQuantity + " " + itemName;
            return SaleItem.getItems(fixed);
        } else {
            ArrayList<SaleItem> items = new ArrayList<>(1);
            items.add(new SaleItem(itemName, itemQuantity));
            return items;
        }
    }

    private static TradeOffer.TradeOfferType getMessageType(String s) {
        if (s == null) return TradeOfferType.QUICK_PASTE;
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
        // FIXME : Caching this value makes history reloads not apply the new color, so its been removed for now
        stashColorIndex = 0;
        stashTabColor = StashTabColor.ZERO;
        if (stashTabName != null) {
            for (StashTabData data : SaveManager.settingsSaveFile.data.stashTabs) {
                if (data.stashTabName == null || ZUtil.isEmptyString(data.stashTabName)) continue;
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
        exampleTrade.itemName = "Tabula Rasa Simple Robe";
        exampleTrade.itemNameLower = exampleTrade.itemName.toLowerCase();
        exampleTrade.priceTypeString = "Chaos Orb";
        exampleTrade.priceQuantity = 100;
        exampleTrade.itemQuantity = 1;
        exampleTrade.stashTabName = "~price 5 chaos";
        exampleTrade.stashTabX = 3;
        exampleTrade.stashTabY = 5;
        return exampleTrade;
    }

}
