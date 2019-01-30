package main.java.com.slimtrade.core.observing;

import main.java.com.slimtrade.core.managers.PoeInterface;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;
import main.java.com.slimtrade.core.utility.TradeOffer;

public class EventManager implements PoeInteractionListener {

	public void poeInteractionPerformed(PoeInteractionEvent e) {
		//MUTUAL
		int mouseButton = e.getMouseButton();
		TradeOffer trade = e.getTrade();
		ButtonType type = e.getButtonType();
		
		switch(type){
		case CALLBACK:
			break;
		case HIDEOUT:
			break;
		case INVITE:
			break;
		case KICK:
			break;
		case LEAVE:
			break;
		case REFRESH:
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
		
		
		switch(trade.msgType){
		case INCOMING_TRADE:
			break;
		case OUTGOING_TRADE:
			break;
		default:
			break;
		
		}
		System.out.println("POE Interaction");
		System.out.println(e.getMouseButton());
		System.out.println(e.getButtonType());
		System.out.println(e.getTrade().playerName);
	}

}
