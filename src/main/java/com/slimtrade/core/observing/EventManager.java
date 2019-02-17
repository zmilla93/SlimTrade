package main.java.com.slimtrade.core.observing;

import java.awt.event.MouseEvent;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.MessageType;

public class EventManager implements PoeInteractionListener {

	public void poeInteractionPerformed(PoeInteractionEvent e) {

		int mouseButton = e.getMouseButton();
		TradeOffer trade = e.getTrade();
		ButtonType type = e.getButtonType();

		switch (type) {
		case CALLBACK:
			if(mouseButton == MouseEvent.BUTTON1){
				PoeInterface.paste("@" + trade.playerName + " " + Main.saveManager.getString("macros", "in", "preset", "callback", "left"));
			}
			break;
		case HIDEOUT:
			PoeInterface.paste("/hideout");
			break;
		case INVITE:
			PoeInterface.paste("/invite " + trade.playerName);
			break;
		case KICK:
			PoeInterface.paste("/kick " + trade.playerName);
			break;
		case LEAVE:
			// TODO : Kick self
			break;
		case REFRESH:
			//FIX FRONT END IMPORT
//			System.out.println("REFRESHING BUTTON : " + trade.msgType);
			if (trade.msgType == MessageType.INCOMING_TRADE) {
//				System.out.println("Still interested?");
				PoeInterface.paste("@" + trade.playerName + " Hi, are you still interested in my "
						+ TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, false) + " listed for "
						+ TradeUtility.getFixedDouble(trade.priceCount, false) + " " + trade.priceTypeString + "?");
			} else if (trade.msgType == MessageType.OUTGOING_TRADE) {
				//TODO : sent message doesn't have player name
				PoeInterface.paste("@" + trade.playerName + " " + trade.sentMessage);
			}
			break;
		case SEARCH:
			PoeInterface.findInStash(trade.itemName.replaceAll("(?i)superior( )?", "").replaceAll("( )?\\(.+\\)", ""));
			break;
		case THANK:
			PoeInterface.paste("@" + trade.playerName + " thanks");
			break;
		case TRADE:
			PoeInterface.paste("/tradewith " + trade.playerName);
			break;
		case WAIT:
			if(mouseButton == MouseEvent.BUTTON1){
				PoeInterface.paste("@" + trade.playerName + " one sec");
			}else if (mouseButton == MouseEvent.BUTTON3){
				PoeInterface.paste("@" + trade.playerName + " one min");
			}
			break;
		case WARP:
			PoeInterface.paste("/hideout " + trade.playerName);
			break;
		case WHISPER:
			PoeInterface.paste("@"+e.getPlayerName() + " " + e.getMessage());
			break;
		default:
			break;
		}

//		System.out.println("POE Interaction");
//		System.out.println(e.getMouseButton());
//		System.out.println(e.getButtonType());
//		System.out.println(e.getTrade().playerName);
		
	}

}
