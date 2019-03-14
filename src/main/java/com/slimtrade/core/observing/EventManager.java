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

	private static String characterName = null;
	
	public void poeInteractionPerformed(PoeInteractionEvent e) {

		int mouseButton = e.getMouseButton();
		TradeOffer trade = e.getTrade();
		ButtonType type = e.getButtonType();

		switch (type) {
		case CALLBACK:
			if(mouseButton == MouseEvent.BUTTON1){
//				PoeInterface.paste("@" + trade.playerName + " " + Main.saveManager.getString("macros", "in", "preset", "callback", "left"));
			}
			break;
		case HIDEOUT:
			PoeInterface.paste("/hideout");
			break;
		case INVITE:
			System.out.println("INVITE");
			PoeInterface.paste("/invite " + trade.playerName);
			break;
		case KICK:
			PoeInterface.paste("/kick " + trade.playerName);
			break;
		case LEAVE:
			if(characterName != null){
				PoeInterface.paste("/kick " + characterName);
			}
			break;
		case NAME_PANEL:
			if(mouseButton == MouseEvent.BUTTON1){
				PoeInterface.paste("/whois " + trade.playerName);
			}else if(mouseButton == MouseEvent.BUTTON3){
				PoeInterface.paste("@" + trade.playerName + " ", false);
			}
			break;
		case REFRESH:
			//FIX FRONT END IMPORT
//			System.out.println("REFRESHING BUTTON : " + trade.msgType);
			if (trade.messageType == MessageType.INCOMING_TRADE) {
//				System.out.println("Still interested?");
				PoeInterface.paste("@" + trade.playerName + " Hi, are you still interested in my "
						+ TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, false) + " listed for "
						+ TradeUtility.getFixedDouble(trade.priceCount, false) + " " + trade.priceTypeString + "?");
			} else if (trade.messageType == MessageType.OUTGOING_TRADE) {
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
			System.out.println("L : " + e.getClickLeft());
			System.out.println(e.getClickRight());
			if(mouseButton==MouseEvent.BUTTON1 && !e.getClickLeft().replaceAll("\\s", "").equals("")){
				PoeInterface.paste("@"+e.getPlayerName() + " " + e.getClickLeft());
			}else if(mouseButton==MouseEvent.BUTTON3 && !e.getClickRight().replaceAll("\\s", "").equals("")){
				PoeInterface.paste("@"+e.getPlayerName() + " " + e.getClickRight());
			}
			
			break;
		default:
			break;
		}

//		System.out.println("POE Interaction");
//		System.out.println(e.getMouseButton());
//		System.out.println(e.getButtonType());
//		System.out.println(e.getTrade().playerName);
		
	}

	public static String getCharacterName() {
		return characterName;
	}

	public static void setCharacterName(String characterName) {
		EventManager.characterName = characterName;
	}

}
