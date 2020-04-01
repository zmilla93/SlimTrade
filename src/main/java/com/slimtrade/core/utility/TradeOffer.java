package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.enums.POEImage;

//TODO : Would be nice to seperate this into several offer types, but would require a fairly large refactory to do so
//TODO : Could switch to getting/setting to make constructor less horrible, but would lose constants

public class TradeOffer {

    public String date;
    public String time;
    public MessageType messageType;
    public String guildName;
    public String playerName;
    public String itemName;
    public Double itemCount;
    public String priceTypeString;
    public Double priceCount;
    public String stashtabName;
    public int stashtabX;
    public int stashtabY;
    public String bonusText;
    public String sentMessage;

    public POEImage priceType;
    public StashTabType stashType = StashTabType.NORMAL;

    public String searchName;
    public String searchMessage;

    // Empty Trade
    public TradeOffer() {

    }

    // Dummy Trade
    public TradeOffer(MessageType messageType, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount) {
        this.messageType = messageType;
        this.playerName = playerName;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.priceTypeString = priceTypeString;
        this.priceType = TradeUtility.getPOEImage(priceTypeString);
        this.priceCount = priceCount;
    }

//    // Full Trade
//    public TradeOffer(String date, MessageType msgType, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount) {
//        this.date = date;
//        this.time = null;
//        this.messageType = msgType;
//        this.guildName = null;
//        this.playerName = playerName;
//        this.itemName = itemName;
//        this.itemCount = itemCount;
//        this.priceTypeString = priceTypeString;
//        this.priceType = TradeUtility.getPOEImage(priceTypeString);
//        this.priceCount = priceCount;
//        this.stashtabName = null;
//        this.stashtabX = 0;
//        this.stashtabY = 0;
//        this.bonusText = null;
//        this.sentMessage = null;
//        this.searchName = null;
//        this.searchMessage = null;
//    }

    //Chat Scanner Search
    public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String searchName, String searchMessage) {
        this.date = date;
        this.time = time;
        this.messageType = msgType;
        this.guildName = guildName;
        this.playerName = playerName;
        this.itemName = null;
        this.itemCount = null;
        this.priceTypeString = null;
        this.priceCount = null;
        this.stashtabName = null;
        this.stashtabX = 0;
        this.stashtabY = 0;
        this.bonusText = null;
        this.sentMessage = null;
        this.searchName = searchName;
        this.searchMessage = searchMessage;
    }

    //Trade
    public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount, String stashtabName, int stashtabX, int stashtabY, String bonusText, String sentMessage) {
        this.date = date;
        this.time = time;
        this.messageType = msgType;
        this.guildName = guildName;
        this.playerName = playerName;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.priceType = TradeUtility.getPOEImage(priceTypeString);
        this.priceTypeString = priceTypeString;
        this.priceCount = priceCount;
        this.stashtabName = stashtabName;
        this.stashtabX = stashtabX;
        this.stashtabY = stashtabY;
        if (bonusText != null) {
            this.bonusText = bonusText.trim();
        } else {
            this.bonusText = null;
        }
        this.sentMessage = sentMessage;
        this.searchName = null;
        this.searchMessage = null;
//        this.searchResponseLeft = null;
//        this.searchResponseRight = null;
    }

    public void playSound() {
        switch (this.messageType) {
            case CHAT_SCANNER:
                AudioManager.play(App.saveManager.saveFile.scannerMessageSound);
                break;
            case INCOMING_TRADE:
                AudioManager.play(App.saveManager.saveFile.incomingMessageSound);
                break;
            case OUTGOING_TRADE:
                AudioManager.play(App.saveManager.saveFile.outgoingMessageSound);
                break;
        }
    }
}
