package com.zrmiller.slimtrade;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class ColorManager {
	
	private Hashtable<String, Color> color = new Hashtable<String, Color>();
	
	public static Color CLEAR = new Color(1.0F, 1.0F, 1.0F, 0.0F);
	
	
	public static class MsgWindow{
		public static Color borderOuter;
		public static Color borderInner;
		public static Color panelBorder;
		public static Color panelBorder_hover;
		public static Color text;
		public static Color text_hover;
		public static Color buttonBG;
		public static Color buttonBG_hover;
//		public static Color buttonBorder;
		public static Border buttonBorder;
		public static Border buttonBorder_hover;
		public static Color nameBG;
		public static Color priceBG_in;
		public static Color priceBG_out;
		public static Color itemBG;
	}
	
	public static class GenericWindow{
		
	}
	
	public static void setMessageTheme(){
		MsgWindow.borderOuter = new Color(40, 20, 0);
		MsgWindow.borderInner = new Color(102, 53, 0);
//		MsgWindow.panelBorder = new Color();
//		MsgWindow.panelBorder_hover = new Color();
		MsgWindow.text = Color.WHITE;
//		MsgWindow.text_hover = new Color();
		MsgWindow.buttonBG = Color.LIGHT_GRAY;
		MsgWindow.buttonBG_hover = Color.WHITE;
//		MsgWindow.buttonBorder = new Color();
//		MsgWindow.buttonBorder_hover = new Color();
		MsgWindow.nameBG = Color.GRAY;
		MsgWindow.priceBG_in = new Color(0, 100, 0);
		MsgWindow.priceBG_out = new Color(100, 0, 0);
		MsgWindow.itemBG = Color.DARK_GRAY;
		MsgWindow.buttonBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		MsgWindow.buttonBorder_hover = BorderFactory.createLineBorder(Color.BLACK);
	}

}
