package com.slimtrade.core.trading;

import java.util.concurrent.ThreadLocalRandom;

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

    public static TradeOffer getExampleTrade(TradeOfferType type) {
        TradeOffer exampleTrade = new TradeOffer();
        exampleTrade.offerType = type;
        exampleTrade.playerName = "ExamplePlayer123";
        exampleTrade.itemName = "Example Item";
        int zero = ThreadLocalRandom.current().nextInt(0, 1);
        if (zero == 1) {
            exampleTrade.itemQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        }
        exampleTrade.priceTypeString = "Chaos";
        exampleTrade.priceQuantity = ThreadLocalRandom.current().nextInt(1, 100);
        return exampleTrade;
    }

}
