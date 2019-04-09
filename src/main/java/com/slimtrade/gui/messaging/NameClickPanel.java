package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;

import javax.swing.BorderFactory;

import main.java.com.slimtrade.core.managers.OLD_ColorManager;

public class NameClickPanel extends ClickPanel {

	private static final long serialVersionUID = 1L;

	public NameClickPanel() {
		backgroudDefault = Color.LIGHT_GRAY;
		this.setBackground(Color.LIGHT_GRAY);
		backgroundHover = OLD_ColorManager.modify(backgroudDefault, 40);
		textDefault = Color.BLACK;
		textHover = Color.BLACK;
		borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	}

}
