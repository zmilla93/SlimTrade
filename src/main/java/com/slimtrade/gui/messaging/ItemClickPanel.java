package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;

import javax.swing.BorderFactory;

public class ItemClickPanel extends ClickPanel {

	private static final long serialVersionUID = 1L;

	public ItemClickPanel() {
		backgroudDefault = Color.black;
		backgroundHover = Color.yellow;
		textDefault = Color.yellow;
		textHover = Color.black;
		borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
		
//		this.addMouseListener();
	}

}
