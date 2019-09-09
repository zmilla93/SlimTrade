package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

public class ConfirmButton extends BasicButton {

	private static final long serialVersionUID = 1L;

	public ConfirmButton(String text){
		super(text);
//		this.primaryColor = ColorManager.GREEN_APPROVE;
	}
	
	@Override
	public void updateColor() {
		primaryColor = ColorManager.GREEN_APPROVE;
		secondaryColor = ColorManager.BACKGROUND;
	}
	
}
