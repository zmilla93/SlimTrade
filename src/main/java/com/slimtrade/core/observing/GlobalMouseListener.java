package main.java.com.slimtrade.core.observing;

import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.sun.jna.Native;
import com.sun.jna.PointerType;

import main.java.com.slimtrade.core.utility.User32;
import main.java.com.slimtrade.enums.WindowType;
import main.java.com.slimtrade.gui.FrameManager;

public class GlobalMouseListener implements NativeMouseInputListener {

	// private WindowType lastWindow = null;
	private String lastWindow;

	public void nativeMouseClicked(NativeMouseEvent e) {

	}

	public void nativeMousePressed(NativeMouseEvent e) {
		//TODO : move to independent thread
		try {
			Thread.sleep(1);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PointerType hwnd = null;
		byte[] windowText = new byte[512];
		int i = 0;
		do{
//			byte[] windowText = new byte[512];
			hwnd = User32.INSTANCE.GetForegroundWindow();
			i++;
			try {
				Thread.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}while(hwnd == null);
		System.out.println("TIME : " + i);
		User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
		String curWindowTitle = Native.toString(windowText);
		System.out.println("PRESSED : "  + curWindowTitle);
		if (curWindowTitle.equals("Path of Exile")) {
			FrameManager.forceAllToTop();
		}
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
		// System.out.println("Click");
		// TODO : Adding check for slimtrade messes with drop downs
//		byte[] windowText = new byte[512];
//		PointerType hwnd = User32.INSTANCE.GetForegroundWindow();
//		User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
//		String curWindowTitle = Native.toString(windowText);
//		if (curWindowTitle.equals("Path of Exile")) {
//			FrameManager.forceAllToTop();
//		}
		// System.out.println(curWindowTitle);
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
