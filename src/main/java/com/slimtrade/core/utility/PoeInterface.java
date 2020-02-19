package com.slimtrade.core.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.logging.Level;

import com.slimtrade.gui.FrameManager;
import com.sun.jna.Native;
import com.sun.jna.PointerType;

import com.slimtrade.App;
import com.slimtrade.core.References;
//import main.java.com.slimtrade.core.utility.User32;
import com.sun.jna.platform.win32.User32;

public class PoeInterface extends Robot {

	private static StringSelection pasteString;
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	private static Robot robot;

	public PoeInterface() throws AWTException {
		try {
			robot = new Robot();
			// robot.setAutoDelay(100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	public static void paste(String s, boolean... send) {
		pasteString = new StringSelection(s);
		clipboard.setContents(pasteString, null);
		PoeInterface.focus();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		if (send.length == 0 || send[0] == true) {
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		}
		FrameManager.forceAllToTop();
	}

	public static void pasteWithFocus(String s) {
		new Thread(new Runnable() {
			public void run() {
				focus();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
//				robot.mouseMove(100, 100);
//				robot.mousePress(InputEvent.BUTTON1_MASK);
//				robot.mouseRelease(InputEvent.BUTTON1_MASK);
				PointerType hwnd = null;
				byte[] windowText = new byte[512];
				int i = 0;
				String curWindowTitle = null;
				do {
					hwnd = User32.INSTANCE.GetForegroundWindow();
					if (hwnd != null) {
						User32Custom.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
						curWindowTitle = Native.toString(windowText);
						if(i>1000 || curWindowTitle.equals(References.POE_WINDOW_TITLE)){
							break;
						}
					} else {
						try {
							Thread.sleep(1);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
					i++;
				} while (true);
//				System.out.println("FOCUS TIME : " + i);
				if (curWindowTitle.equals("Path of Exile")) {
					FrameManager.forceAllToTop();
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_V);
					robot.keyRelease(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);
				}else{
//					App.logger.log(Level.WARNING, "Bad Window ::: " + curWindowTitle);
				}
			}
		}).start();
	}

	public static void findInStash(String s) {
		focus();
		pasteString = new StringSelection(s);
		clipboard.setContents(pasteString, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		FrameManager.forceAllToTop();
	}

	public static void focus() {
		User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
			char[] className = new char[512];
			User32.INSTANCE.GetClassName(hWnd, className, 512);
			String wText = Native.toString(className);
			if (wText.isEmpty()) {
				return true;
			}
			if (wText.equals("POEWindowClass")) {
				User32.INSTANCE.SetForegroundWindow(hWnd);
				return false;
			}
			return true;
		}, null);
	}
}
