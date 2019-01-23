package main.java.com.slimtrade.core;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ColorManager {
		
	public static Color CLEAR = new Color(1.0F, 1.0F, 1.0F, 0.0F);
	
	
	public static class MsgWindow{
		public static Color borderOuter;
		public static Color borderInner;
		public static Color panelBorder;
		public static Color panelBorder_hover;
		public static Color text;
		public static Color text_hover;
		public static Color buttonBG;
		public static Color buttonBG_next;
		public static Color buttonBG_completed;
		public static Color buttonBG_hover;
		public static Border buttonBorder;
		public static Border buttonBorder_next;
		public static Border buttonBorder_hover;
		public static Color nameBG;
		public static Color priceBG_in;
		public static Color priceBG_out;
		public static Color itemBG;
	}
	
	public static class GenericWindow{
		public static Color titlebarBG;
		public static Color titlebarText;
		public static Color closeButtonBG;
		public static Color buttonBG;
		public static Color buttonBG_hover;
		public static Border buttonBorder;
		public static Border buttonBorder_hover;
	}
	
	public static class HistoryWindow{
		public static Color buttonBG_active;
		public static Color buttonBG_inactive;
		public static Color buttonBG_hover;
		public static Border buttonBorder_active;
		public static Border buttonBorder_inactive;
		public static Border buttonBorder_hover;
	}
	
	public static void setMessageTheme(){
		MsgWindow.borderOuter = new Color(40, 20, 0);
		MsgWindow.borderInner = new Color(102, 53, 0);
//		MsgWindow.panelBorder = new Color();
//		MsgWindow.panelBorder_hover = new Color();
		
		MsgWindow.text = Color.WHITE;
//		MsgWindow.text = new Color(197,179,88);
		
//		MsgWindow.text_hover = new Color();
		MsgWindow.buttonBG = Color.LIGHT_GRAY;
		MsgWindow.buttonBG_next = Color.WHITE;
		MsgWindow.buttonBG_completed = Color.DARK_GRAY;
		MsgWindow.buttonBG_hover = Color.WHITE;
//		MsgWindow.buttonBorder = new Color();
//		MsgWindow.buttonBorder_hover = new Color();
		MsgWindow.nameBG = Color.GRAY;
		MsgWindow.priceBG_in = new Color(0, 100, 0);
		MsgWindow.priceBG_out = new Color(100, 0, 0);
		MsgWindow.itemBG = Color.DARK_GRAY;
		MsgWindow.buttonBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		MsgWindow.buttonBorder_next = BorderFactory.createLineBorder(Color.GREEN);
		MsgWindow.buttonBorder_hover = BorderFactory.createLineBorder(Color.BLACK);
				
		GenericWindow.titlebarBG = Color.LIGHT_GRAY;
		GenericWindow.closeButtonBG = Color.LIGHT_GRAY;
		GenericWindow.titlebarText = Color.WHITE;
		GenericWindow.buttonBG = Color.LIGHT_GRAY;
		GenericWindow.buttonBG_hover = Color.WHITE;
		GenericWindow.buttonBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		GenericWindow.buttonBorder_hover = BorderFactory.createLineBorder(Color.BLACK);
		
		HistoryWindow.buttonBG_active = Color.LIGHT_GRAY;
		HistoryWindow.buttonBG_inactive = Color.GRAY;
		HistoryWindow.buttonBG_hover = Color.WHITE;
		HistoryWindow.buttonBorder_active = BorderFactory.createLoweredSoftBevelBorder();
		HistoryWindow.buttonBorder_inactive = BorderFactory.createRaisedSoftBevelBorder();
//		HistoryWindow.buttonBorder_active = BorderFactory.createLoweredSoftBevelBorder();
	}

}
