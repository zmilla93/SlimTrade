package main.java.com.slimtrade.core.observing.poe;

import main.java.com.slimtrade.core.observing.ButtonType;
import main.java.com.slimtrade.core.utility.TradeOffer;

public final class PoeInteractionEvent {

	private int mouseButton;
	private ButtonType buttonType;
	private TradeOffer trade;
	private String playerName;
	private String clickLeft;
	private String clickRight;

	public PoeInteractionEvent(int mouseButton, ButtonType buttonType, TradeOffer trade) {
		super();
		this.mouseButton = mouseButton;
		this.buttonType = buttonType;
		this.trade = trade;
		// this.message = message;
	}

	public PoeInteractionEvent(int mouseButton, ButtonType buttonType, String playerName, String clickLeft, String clickRight) {
		super();
		this.mouseButton = mouseButton;
		this.buttonType = buttonType;
		// this.trade = trade;
		this.playerName = playerName;
		this.clickLeft = clickLeft;
		this.clickRight = clickRight;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public ButtonType getButtonType() {
		return buttonType;
	}

	public TradeOffer getTrade() {
		return trade;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getClickLeft() {
		return clickLeft;
	}

	public String getClickRight() {
		return clickRight;
	}
	
}
