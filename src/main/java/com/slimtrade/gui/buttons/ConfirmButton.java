package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

public class ConfirmButton extends BasicButton {

	private static final long serialVersionUID = 1L;

	public ConfirmButton(String text){
		super(text);
		primaryColor = ColorManager.GREEN_APPROVE;
		secondaryColor = ColorManager.BACKGROUND;
		this.updateColor();
	}
	
	@Override
	public void updateColor() {
		super.updateColor();
	}
	
}
