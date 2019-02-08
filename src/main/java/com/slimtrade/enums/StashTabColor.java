package main.java.com.slimtrade.enums;

import java.awt.Color;

import main.java.com.slimtrade.core.managers.ColorManager;

public enum StashTabColor {
	
	ZERO(Color.WHITE, Color.BLACK),
	
	ONE(new Color(124, 81, 50), ColorManager.stashLightText),
	TWO(new Color(191, 91, 0), ColorManager.stashLightText),
	THREE(new Color(254, 191, 128), ColorManager.stashDarkText),
	
	FOUR(new Color(86, 0, 0), ColorManager.stashLightText),
	FIVE(new Color(191, 0, 0), ColorManager.stashLightText),
	SIX(new Color(254, 128, 128), ColorManager.stashDarkText),
	
	SEVEN(new Color(114, 0, 83), ColorManager.stashLightText),
	EIGHT(new Color(204, 0, 154), ColorManager.stashLightText),
	NINE(new Color(254, 128, 222), ColorManager.stashDarkText),
	
	TEN(new Color(38, 0, 86), ColorManager.stashLightText),
	ELEVEN(new Color(88, 0, 179), ColorManager.stashLightText),
	TWELVE(new Color(192, 128, 254), ColorManager.stashDarkText),
	
	THIRTEEN(new Color(0, 0, 128), ColorManager.stashLightText),
	FOURTEEN(new Color(0, 0, 254), ColorManager.stashLightText),
	FIFTEEN(new Color(128, 179, 254), ColorManager.stashDarkText),
	
	SIXTEEN(new Color(0, 73, 0), ColorManager.stashLightText),
	SEVENTEEN(new Color(0, 191, 0), ColorManager.stashDarkText),
	EIGHTEEN(new Color(128, 254, 128), ColorManager.stashDarkText),
	
	NINETEEN(new Color(98, 128, 0), ColorManager.stashLightText),
	TWENTY(new Color(191, 244, 0), ColorManager.stashDarkText),
	TWENTYONE(new Color(239, 254, 128), ColorManager.stashDarkText),
	
	TWENTYTWO(new Color(254, 170, 0), ColorManager.stashDarkText),
	TWENTYTRHEE(new Color(254, 213, 0), ColorManager.stashDarkText),
	TWENTYFOUR(new Color(254, 254, 153), ColorManager.stashDarkText),
	
	TWENTYFIVE(new Color(42, 42, 42), ColorManager.stashLightText),
	TWENTYSIX(new Color(135, 135, 135), ColorManager.stashDarkText),
	TWENTYSEVEN(new Color(221, 221, 221), ColorManager.stashDarkText),
	;
	
	Color backgroundColor;
	Color textColor;	
	
	StashTabColor(Color bg){
		this.backgroundColor = bg;
		this.textColor = Color.WHITE;
	}
	
	StashTabColor(Color bg, Color text){
		this.backgroundColor = bg;
		this.textColor = text;
	}
	
	public Color getBackground(){
		return this.backgroundColor;
	}
	
	public Color getForeground(){
		return this.textColor;
	}
	
}
