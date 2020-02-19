package com.slimtrade.core.observing;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdvancedMouseAdapter extends MouseAdapter {

	private MouseHoverState hoverState;

	public void mousePressed(MouseEvent e) {
		hoverState = MouseHoverState.INBOUNDS;
	}

	public void mouseReleased(MouseEvent e) {
		if (hoverState == MouseHoverState.INBOUNDS) {
			click(e);
		}
	}

	public void mouseEntered(MouseEvent e) {
		hoverState = MouseHoverState.INBOUNDS;
	}

	public void mouseExited(MouseEvent e) {
		hoverState = MouseHoverState.OUT_OF_BOUNDS;
	}

	public void click(MouseEvent e) {

	}

}
