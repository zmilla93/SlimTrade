package com.slimtrade.core.observing;

import java.awt.event.MouseEvent;

import com.slimtrade.App;
import com.slimtrade.core.observing.poe.PoeInteractionEvent;
import com.slimtrade.core.observing.poe.PoeInteractionListener;
import com.slimtrade.enums.MessageType;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;

public class MacroEventManager implements PoeInteractionListener {

    private static String characterName = null;

    public void poeInteractionPerformed(PoeInteractionEvent e) {

        int mouseButton = e.getMouseButton();
        TradeOffer trade = e.getTrade();
        ButtonType type = e.getButtonType();

        switch (type) {
            case HIDEOUT:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    if(App.saveManager.saveFile.closeOnKick) {
                        FrameManager.messageManager.closeTrade(trade);
                    }
                    PoeInterface.paste("/hideout");
                }
                break;
            case INVITE:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    PoeInterface.paste("/invite " + trade.playerName);
                }
                break;
            case KICK:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    if(App.saveManager.saveFile.closeOnKick) {
                        FrameManager.messageManager.closeTrade(trade);
                    }
                    PoeInterface.paste("/kick " + trade.playerName);
                }
                break;
            case LEAVE:
                if (characterName != null && characterName != "") {
                    if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                        PoeInterface.paste("/kick " + characterName);
                    }
                }
                break;
            case NAME_PANEL:
                if (mouseButton == MouseEvent.BUTTON1) {
                    PoeInterface.paste("/whois " + trade.playerName);
                } else if (mouseButton == MouseEvent.BUTTON3) {
                    PoeInterface.paste("@" + trade.playerName + " ", false);
                }
                break;
            case REFRESH:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    if (trade.messageType == MessageType.INCOMING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " Hi, are you still interested in my "
                                + TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, false) + " listed for "
                                + TradeUtility.getFixedDouble(trade.priceCount, false) + " " + trade.priceTypeString + "?");
                    } else if (trade.messageType == MessageType.OUTGOING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " " + trade.sentMessage);
                    }
                }
                break;
            case SEARCH:
                PoeInterface.findInStash(TradeUtility.cleanItemName(trade.itemName));
                break;
            case THANK:
                if (mouseButton == MouseEvent.BUTTON1) {
                    if (trade.messageType == MessageType.INCOMING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " " + App.saveManager.saveFile.thankIncomingLMB);
                    } else if (trade.messageType == MessageType.OUTGOING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " " + App.saveManager.saveFile.thankOutgoingLMB);
                    }
                } else if (mouseButton == MouseEvent.BUTTON3) {
                    if (trade.messageType == MessageType.INCOMING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " " + App.saveManager.saveFile.thankIncomingRMB);
                    } else if (trade.messageType == MessageType.OUTGOING_TRADE) {
                        PoeInterface.paste("@" + trade.playerName + " " + App.saveManager.saveFile.thankOutgoingRMB);
                    }
                }
                break;
            case TRADE:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    PoeInterface.paste("/tradewith " + trade.playerName);
                }
                break;
            case WAIT:
                if (mouseButton == MouseEvent.BUTTON1) {
                    PoeInterface.paste("@" + trade.playerName + " one sec");
                } else if (mouseButton == MouseEvent.BUTTON3) {
                    PoeInterface.paste("@" + trade.playerName + " one min");
                }
                break;
            case WARP:
                if (mouseButton == MouseEvent.BUTTON1 || mouseButton == MouseEvent.BUTTON3) {
                    PoeInterface.paste("/hideout " + trade.playerName);
                }
                break;
            case WHISPER:
                if (mouseButton == MouseEvent.BUTTON1 && !e.getClickLeft().replaceAll("\\s", "").equals("")) {
                    PoeInterface.paste("@" + e.getPlayerName() + " " + e.getClickLeft());
                } else if (mouseButton == MouseEvent.BUTTON3 && !e.getClickRight().replaceAll("\\s", "").equals("")) {
                    PoeInterface.paste("@" + e.getPlayerName() + " " + e.getClickRight());
                }
                break;
        }
    }

    public static void setCharacterName(String characterName) {
        MacroEventManager.characterName = characterName;
    }

}
