package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class REF_MSG_WINDOW {
		//SETTINGS
	
		//OFFSETS
		final int offsetX = 0;
		final int offsetY = 0;
		
		//BORDERS
		final int borderWidthTop = 4;
		final int borderWidthLeft = 4;
		final int borderWidthRight = 4;
		final int borderWidthBottom = 4;
		
		//PANELS
		final int msgWidth = 400;
		final int msgHeight = 44;
		final int totalWidth = msgWidth+borderWidthLeft+borderWidthRight;
		final int totalHeight = msgHeight+borderWidthTop+borderWidthBottom;

		//BUTTONS
		final int buttonCountRow1 = 2;
		final int buttonCountRow2 = 0;
		
		//PANELS
		final int buttonWidth = msgHeight/2;
		final int buttonHeight = msgHeight/2;
		final double itemWidthPercent = 1;
		//BUG: Math is inaccurate without 0.005 buffer
		final double nameWidthPercent = 0.705;
		final double priceWidthPercent = 0.3;
		final int nameWidth = (int) ((msgWidth-buttonWidth*buttonCountRow1)*nameWidthPercent);
		final int itemWidth = (int) ((msgWidth-buttonWidth*buttonCountRow2)*itemWidthPercent);
		final int priceWidth = (int) ((msgWidth-buttonWidth*buttonCountRow1)*priceWidthPercent);
		final int nameHeight = msgHeight/2;
		final int itemHeight = msgHeight/2;
		final int priceHeight = msgHeight/2;
		
		//SCREEN SIZE
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenWidth = (int) Math.round(screenSize.getWidth());
		final int screenHeight = (int) Math.round(screenSize.getHeight());
		
		//Colors
		Color nameBgColor = new Color(200, 200, 200);
		Color itemBgColor = new Color(150, 150, 150);
		Color priceBgColor = new Color(150, 150, 150);
		String defaultTextColor = "#FFFF00";
		Color borderColor1 = Color.orange;
		//Color borderColor2 = Color.white;
		
		public REF_MSG_WINDOW(){
		}
		
		public void setMessageColor(String msgType){
			switch(msgType){
			case "From":
			case "in":
				borderColor1 = Color.orange;
				break;
			case "To":
			case "out":
				borderColor1 = new Color(102, 51, 0);
				break;
			}
		}
}
