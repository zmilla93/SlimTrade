package main.java.com.slimtrade.core.observing;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.References;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.sun.jna.Native;
import com.sun.jna.PointerType;

import main.java.com.slimtrade.core.utility.User32Custom;
import main.java.com.slimtrade.enums.WindowType;
import main.java.com.slimtrade.gui.FrameManager;

public class GlobalMouseListener implements NativeMouseInputListener {

	public void nativeMouseClicked(NativeMouseEvent e) {

	}

	//TODO : This throws an error if mouse is clicked during loading
	public void nativeMousePressed(NativeMouseEvent e) {
//		new Thread(refreshRunner).start();
		
		try {
			Thread.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		PointerType hwnd = null;
		byte[] windowText = new byte[512];
		do {
			hwnd = User32Custom.INSTANCE.GetForegroundWindow();
			if(hwnd!=null){
				break;
			}else{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (true);
		User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
		String curWindowTitle = Native.toString(windowText);
        System.out.println("window : " +  curWindowTitle);
		if (curWindowTitle.equals(References.POE_WINDOW_TITLE) || curWindowTitle.startsWith(References.APP_NAME) || Main.debugMode) {
            FrameManager.showVisibleFrames();
			FrameManager.forceAllToTop();
		}else{
		    FrameManager.hideAllFrames();
        }
	}

	public void nativeMouseReleased(NativeMouseEvent e) {

	}

	public void nativeMouseDragged(NativeMouseEvent e) {

	}

	public void nativeMouseMoved(NativeMouseEvent e) {

	}

	private WindowType getWindowType(String win) {
		if (win.equals("Path of Exile")) {
			return WindowType.POE;
		} else if (win.contains("SlimTrade")) {
			return WindowType.SLIMTRADE;
		} else {
			return WindowType.OTHER;
		}
	}

}
