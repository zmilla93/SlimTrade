package main.java.com.slimtrade.enums;

import java.awt.Color;

import main.java.com.slimtrade.core.managers.OLD_ColorManager;

public enum StashTabColor {
	
	ZERO(Color.WHITE, Color.BLACK),
	
	ONE(new Color(124, 81, 50), OLD_ColorManager.stashLightText),
	TWO(new Color(191, 91, 0), OLD_ColorManager.stashLightText),
	THREE(new Color(254, 191, 128), OLD_ColorManager.stashDarkText),
	
	FOUR(new Color(86, 0, 0), OLD_ColorManager.stashLightText),
	FIVE(new Color(191, 0, 0), OLD_ColorManager.stashLightText),
	SIX(new Color(254, 128, 128), OLD_ColorManager.stashDarkText),
	
	SEVEN(new Color(114, 0, 83), OLD_ColorManager.stashLightText),
	EIGHT(new Color(204, 0, 154), OLD_ColorManager.stashLightText),
	NINE(new Color(254, 128, 222), OLD_ColorManager.stashDarkText),
	
	TEN(new Color(38, 0, 86), OLD_ColorManager.stashLightText),
	ELEVEN(new Color(88, 0, 179), OLD_ColorManager.stashLightText),
	TWELVE(new Color(192, 128, 254), OLD_ColorManager.stashDarkText),
	
	THIRTEEN(new Color(0, 0, 128), OLD_ColorManager.stashLightText),
	FOURTEEN(new Color(0, 0, 254), OLD_ColorManager.stashLightText),
	FIFTEEN(new Color(128, 179, 254), OLD_ColorManager.stashDarkText),
	
	SIXTEEN(new Color(0, 73, 0), OLD_ColorManager.stashLightText),
	SEVENTEEN(new Color(0, 191, 0), OLD_ColorManager.stashDarkText),
	EIGHTEEN(new Color(128, 254, 128), OLD_ColorManager.stashDarkText),
	
	NINETEEN(new Color(98, 128, 0), OLD_ColorManager.stashLightText),
	TWENTY(new Color(191, 244, 0), OLD_ColorManager.stashDarkText),
	TWENTYONE(new Color(239, 254, 128), OLD_ColorManager.stashDarkText),
	
	TWENTYTWO(new Color(254, 170, 0), OLD_ColorManager.stashDarkText),
	TWENTYTRHEE(new Color(254, 213, 0), OLD_ColorManager.stashDarkText),
	TWENTYFOUR(new Color(254, 254, 153), OLD_ColorManager.stashDarkText),
	
	TWENTYFIVE(new Color(42, 42, 42), OLD_ColorManager.stashLightText),
	TWENTYSIX(new Color(135, 135, 135), OLD_ColorManager.stashDarkText),
	TWENTYSEVEN(new Color(221, 221, 221), OLD_ColorManager.stashDarkText),
	;
	
	private Color backgroundColor;
	private Color textColor;	
	
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
	
	public static StashTabColor getValueFromColor(Color color){
		for(StashTabColor c : StashTabColor.values()){
			if(color.equals(c.getBackground())){
				return c;
			}
		}
		return null;
	}
	
}
