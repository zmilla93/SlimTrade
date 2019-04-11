package main.java.com.slimtrade.gui.buttons;

import main.java.com.slimtrade.core.managers.ColorManager;

public class ConfirmButton extends BasicButton {

	private static final long serialVersionUID = 1L;

	public ConfirmButton(String text){
		super(text);
		this.mainColor = ColorManager.GREEN_APPROVE;
	}
	
}
