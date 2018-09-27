package com.zrmiller.slimtrade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.zrmiller.slimtrade.datatypes.MessageType;

public class REF_MSG_WINDOW {
		//SETTINGS
	
		//OFFSETS
		final int offsetX = 1255;
		final int offsetY = 4;
		final int labelBufferX = 5;
		
		//BORDERS
		final int borderWidthTop = 2;
		final int borderWidthLeft = 2;
		final int borderWidthRight = 2;
		final int borderWidthBottom = 2;
		
		//PANELS
		final int msgWidth = 380;
		final int msgHeight = 50;
		final int totalWidth = msgWidth+borderWidthLeft+borderWidthRight;
		final int totalHeight = msgHeight+borderWidthTop+borderWidthBottom;

		//BUTTONS
		final int buttonCountRow1 = 2;
		int buttonCountRow2 = 4;
		
		//PANELS
		final int buttonWidth = msgHeight/2;
		final int buttonHeight = msgHeight/2;
		final double itemWidthPercent = 1;
		final double nameWidthPercent = 0.7;
		final double priceWidthPercent = 0.3;
		double nWidth = (double)(msgWidth-buttonWidth*buttonCountRow1)*nameWidthPercent;
		double iWidth = (double)(msgWidth-buttonWidth*buttonCountRow2)*itemWidthPercent;
		double pWidth =	(double)(msgWidth-buttonWidth*buttonCountRow1)*priceWidthPercent;
		int nameWidth = (int) nWidth;
		int itemWidth = (int) iWidth;
		final int priceWidth = (int) pWidth;
		//final int priceWidth = msgWidth-nameWidth-(buttonWidth*buttonCountRow1);
		final int nameHeight = msgHeight/2;
		final int itemHeight = msgHeight/2;
		final int priceHeight = msgHeight/2;
		
		//SCREEN SIZE
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int screenWidth = (int) Math.round(screenSize.getWidth());
		final int screenHeight = (int) Math.round(screenSize.getHeight());
		
		//COLORS
		
		//INCOMING TRADE
		Color nameBgColor;
		Color itemBgColor;
		Color priceBgColor;
		String defaultTextColor;
		Color borderColor1;
		//Color borderColor2 = Color.white;
		
		//OUTGOING TRADE
		public REF_MSG_WINDOW(){
			//Fix rounding error. Better way to do?
			while(nameWidth + priceWidth + (buttonWidth*buttonCountRow1) < msgWidth){
				this.nameWidth++;
				System.out.println("nameWidth++");
			}
			System.out.println("NAME FIXED");
			while(itemWidth + (buttonWidth*buttonCountRow2) < msgWidth){
				this.itemWidth++;
				System.out.println("itemWidth++");
			}
			System.out.println("ITEM FIXED");
		}
		
		//COLORS
		public void setMessageType(MessageType t){
			switch(t){
			case INCOMING_TRADE:
				this.buttonCountRow2 = 4;
				nameBgColor = new Color(200, 200, 200);
				itemBgColor = new Color(150, 150, 150);
				priceBgColor = new Color(120, 120, 120);
				defaultTextColor = "#FFFF00";
				borderColor1 = Color.orange;
				break;
			case OUTGOING_TRADE:
				this.buttonCountRow2 = 5;
				nameBgColor = new Color(200, 200, 200);
				itemBgColor = new Color(150, 150, 150);
				priceBgColor = new Color(120, 120, 120);
				defaultTextColor = "#FFFF00";
				borderColor1 = Color.black;
				break;
			default:
				break;
			}
		}
}
