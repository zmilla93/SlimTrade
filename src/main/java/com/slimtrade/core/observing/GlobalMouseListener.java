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
	private Runnable refreshRunner = new Runnable() {
		public void run() {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			PointerType hwnd = null;
			byte[] windowText = new byte[512];
			int i = 0;
			do {
				hwnd = User32.INSTANCE.GetForegroundWindow();
				if(hwnd!=null){
					break;
				}else{
					i++;
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} while (true);
//			System.out.println("TIME : " + i);
			User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
			String curWindowTitle = Native.toString(windowText);
//			System.out.println("PRESSED : " + curWindowTitle);
			if (curWindowTitle.equals("Path of Exile") || curWindowTitle.matches("SlimTrade*+")) {
				FrameManager.forceAllToTop();
			}
		}
	};

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
		int i = 0;
		do {
			hwnd = User32.INSTANCE.GetForegroundWindow();
			if(hwnd!=null){
				break;
			}else{
				i++;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		} while (true);
//		System.out.println("TIME : " + i);
		User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
		String curWindowTitle = Native.toString(windowText);
//		System.out.println("PRESSED : " + curWindowTitle);
		if (curWindowTitle.equals("Path of Exile") || curWindowTitle.matches("SlimTrade*+")) {
			FrameManager.forceAllToTop();
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
