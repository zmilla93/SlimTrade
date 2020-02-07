package com.slimtrade.core.utility;

import com.slimtrade.core.References;
import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import java.awt.*;

public class POEWindowInfo {

	boolean isOpen;
	boolean isVisible;
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	private static Point centerPoint;

	//TODO : Seperate actions
//	public POEWindowInfo(){
//		Rectangle r = null;
//		//TODO : Throws Invalid Window Handle sometimes?
//		//		JNA / WindowUtils error
//		for(DesktopWindow w : WindowUtils.getAllWindows(true)){
////            System.out.println("Window : " + w.getTitle());
//			if(w.getTitle().equals(References.POE_WINDOW_TITLE)){
//				isOpen = true;
//				r  = w.getLocAndSize();
////                System.out.println("LOCATION : " + r.toString());
//			}
//		}
//		if(r == null){
//			return;
//		}else{
//			if(r.x != -32000 && r.y != -32000){
//				isVisible = true;
//				x = r.x;
//				y = r.y;
//				windowWidth = r.windowWidth;
//				windowHeight = r.windowHeight;
//				centerPoint = new Point(x+windowWidth/2, y+windowHeight/2);
//			}
//		}
//	}
	
	public static boolean getIsOpen(){
	    if(getWindow() != null){
	        return true;
        }
        return false;
	}
	
	public static boolean getIsVisible(){
        DesktopWindow window = getWindow();
        if(window != null){
            Rectangle windowBounds = window.getLocAndSize();
            if(windowBounds.x != -32000 && windowBounds.y != -32000){
                return true;
            }
        }
        return false;
	}
	
	public static Point getCenterPoint(){
		return centerPoint;
	}

	private static DesktopWindow getWindow(){
        for(DesktopWindow w : WindowUtils.getAllWindows(true)){
            if(w.getTitle().equals(References.POE_WINDOW_TITLE)){
                return w;
            }
        }
        return null;
    }
	
}
