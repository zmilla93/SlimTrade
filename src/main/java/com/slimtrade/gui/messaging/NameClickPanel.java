package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;

import javax.swing.BorderFactory;

public class NameClickPanel extends AbstractClickPanel {

	private static final long serialVersionUID = 1L;

	public NameClickPanel() {
		bgDefault = Color.orange;
		bgHover = Color.green;
		textDefault = Color.green;
		textHover = Color.orange;
		borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	}

}
