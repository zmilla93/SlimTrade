package main.java.com.slimtrade.core.observing;

import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;

public class EventManager implements PoeInteractionListener {

	public EventManager() {

	}

	public void addPoeObservable(PoeInteractionListener l) {

	}

	public void poeInteractionPerformed(PoeInteractionEvent e) {
		System.out.println("POE Interaction");
		System.out.println(e.getMouseButton());
		System.out.println(e.getButtonType());
		System.out.println(e.getTrade().playerName);
	}

}
