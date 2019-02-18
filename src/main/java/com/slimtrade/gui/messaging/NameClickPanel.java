package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;

import javax.swing.BorderFactory;

public class NameClickPanel extends ClickPanel {

	private static final long serialVersionUID = 1L;

	public NameClickPanel() {
		backgroudDefault = Color.orange;
		backgroundHover = Color.green;
		textDefault = Color.green;
		textHover = Color.orange;
		borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	}

}
