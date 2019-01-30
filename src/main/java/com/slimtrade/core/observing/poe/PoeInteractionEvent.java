package main.java.com.slimtrade.core.observing.poe;

import main.java.com.slimtrade.core.observing.ButtonType;
import main.java.com.slimtrade.core.utility.TradeOffer;

public class PoeInteractionEvent {

	private int mouseButton;
	private ButtonType buttonType;
	private TradeOffer trade;

	public PoeInteractionEvent(int mouseButton, ButtonType buttonType, TradeOffer trade) {
		super();
		this.mouseButton = mouseButton;
		this.buttonType = buttonType;
		this.trade = trade;
	}

	public int getMouseButton() {
		return mouseButton;
	}

	public void setMouseButton(int mouseButton) {
		this.mouseButton = mouseButton;
	}

	public ButtonType getButtonType() {
		return buttonType;
	}

	public void setButtonType(ButtonType buttonType) {
		this.buttonType = buttonType;
	}

	public TradeOffer getTrade() {
		return trade;
	}

	public void setTrade(TradeOffer trade) {
		this.trade = trade;
	}

}
