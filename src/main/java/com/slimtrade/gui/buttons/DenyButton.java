package main.java.com.slimtrade.gui.buttons;

import main.java.com.slimtrade.core.managers.ColorManager;

public class DenyButton extends BasicButton {

	private static final long serialVersionUID = 1L;

	public DenyButton(String text){
		super(text);
		this.mainColor = ColorManager.RED_DENY;
	}
	
}
