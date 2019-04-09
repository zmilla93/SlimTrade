package main.java.com.slimtrade.core.utility;

import java.awt.Point;
import java.awt.Rectangle;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import main.java.com.slimtrade.core.References;

public class POEWindowInfo {

	boolean isOpen;
	boolean isVisible;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private Point centerPoint;
	
	public POEWindowInfo(){
		Rectangle r = null;
		//TODO : Throws Invalid Window Handle sometimes?
		//		JNA / WindowUtils error
		for(DesktopWindow w : WindowUtils.getAllWindows(true)){
			if(w.getTitle().equals(References.POE_WINDOW_TITLE)){
				isOpen = true;
				r  = w.getLocAndSize();
			}
		}
		if(r == null){
			return;
		}else{
			if(r.x != -32000 && r.y != -32000){
				isVisible = true;
				x = r.x;
				y = r.y;
				width = r.width;
				height = r.height;
				centerPoint = new Point(x+width/2, y+height/2);
			}
		}
	}
	
	public boolean getIsOpen(){
		return isOpen;
	}
	
	public boolean getIsVisible(){
		return isVisible;
	}
	
	public Point getCenterPoint(){
		return centerPoint;
	}
	
}
