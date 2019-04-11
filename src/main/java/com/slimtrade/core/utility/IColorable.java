package main.java.com.slimtrade.core.utility;

import java.awt.Component;

import javax.swing.JPanel;

import main.java.com.slimtrade.enums.ColorTheme;

public interface IColorable {

	public void applyColor(ColorTheme theme);
	
	public default void paintChildren(ColorTheme theme){
		if(this instanceof JPanel){
			JPanel panel = (JPanel) this;
			for(Component c : panel.getComponents()){
				if(c instanceof IColorable){
					IColorable child = (IColorable)c;
					child.applyColor(theme);
				}
			}
		}
	}
	
}
