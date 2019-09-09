package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

public class DenyButton extends BasicButton {

	private static final long serialVersionUID = 1L;

	public DenyButton(String text){
		super(text);
	}
	
	@Override
	public void updateColor() {
		primaryColor = ColorManager.RED_DENY;
		secondaryColor = ColorManager.BACKGROUND;
	}
	
}
