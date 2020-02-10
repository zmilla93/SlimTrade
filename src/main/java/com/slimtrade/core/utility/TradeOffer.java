package com.slimtrade.core.utility;

import com.slimtrade.App;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.enums.POEImage;

//TODO : Would be nice to seperate this into several offer types, but would require a fairly large refactory to do so
//TODO : Could switch to getting/setting to make constructor less horrible, but would lose constants

public class TradeOffer {

    public final String date;
    public final String time;
    public final MessageType messageType;
    public final String guildName;
    public final String playerName;
    public final String itemName;
    public final Double itemCount;
    public final String priceTypeString;
    public final Double priceCount;
    public final String stashtabName;
    public final int stashtabX;
    public final int stashtabY;
    public final String bonusText;
    public final String sentMessage;

    public POEImage priceType;
    public StashTabType stashType = StashTabType.NORMAL;

    public final String searchName;
    public final String searchMessage;
    public final String searchResponseLeft;
    public final String searchResponseRight;

    //Dummy
    public TradeOffer(String date, MessageType msgType, String playerName, String itemName, double itemCount, String priceTypeString, double priceCount) {
        this.date = date;
        this.time = null;
        this.messageType = msgType;
        this.guildName = null;
        this.playerName = playerName;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.priceType = TradeUtility.getPOEImage(priceTypeString);
        this.priceTypeString = priceTypeString;
        this.priceCount = priceCount;
        this.stashtabName = null;
        this.stashtabX = 0;
        this.stashtabY = 0;
        this.bonusText = null;
        this.sentMessage = null;
        this.searchName = null;
        this.searchMessage = null;
        this.searchResponseLeft = null;
        this.searchResponseRight = null;
    }

    //Chat Scanner Search
    public TradeOffer(String date, String time, MessageType msgType, String guildName, String playerName, String searchName, String searchMessage, String responseLeft, String responseRight) {
        this.date = date;
        this.time = null;
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
        this.searchResponseLeft = responseLeft;
        this.searchResponseRight = responseRight;
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
        this.bonusText = bonusText;
        this.sentMessage = sentMessage;
        this.searchName = null;
        this.searchMessage = null;
        this.searchResponseLeft = null;
        this.searchResponseRight = null;
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
