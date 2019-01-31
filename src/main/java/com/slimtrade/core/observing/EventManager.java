package main.java.com.slimtrade.core.observing;

import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.datatypes.MessageType;

public class EventManager implements PoeInteractionListener {

	public void poeInteractionPerformed(PoeInteractionEvent e) {

		int mouseButton = e.getMouseButton();
		TradeOffer trade = e.getTrade();
		ButtonType type = e.getButtonType();

		switch (type) {
		case CALLBACK:
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
			System.out.println("REFRESHING BUTTON : " + trade.msgType);
			if (trade.msgType == MessageType.INCOMING_TRADE) {
				System.out.println("Still interested?");
				PoeInterface.paste("Hi, are you still interested in my "
						+ TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, false) + " listed for "
						+ TradeUtility.getFixedDouble(trade.priceCount) + " " + trade.priceTypeString + "?");
			} else if (trade.msgType == MessageType.OUTGOING_TRADE) {
				PoeInterface.paste(trade.sentMessage);
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
			break;
		case WARP:
			break;
		default:
			break;

		}

		switch (trade.msgType) {
		case INCOMING_TRADE:
			break;
		case OUTGOING_TRADE:
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
