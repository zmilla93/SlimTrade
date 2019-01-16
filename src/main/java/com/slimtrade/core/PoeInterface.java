package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

public class PoeInterface extends Robot{
	
	private static StringSelection pasteString;
	private static Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	private static Robot robot;
	
	
	public PoeInterface() throws AWTException{
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public static void paste(String s){
		pasteString = new StringSelection(s);
		clipboard.setContents(pasteString, null);
		PoeInterface.focus();
		robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	public static void findInStash(String s){
		pasteString = new StringSelection(s);
		clipboard.setContents(pasteString, null);
		PoeInterface.focus();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_F);
		robot.keyRelease(KeyEvent.VK_F);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
	
	//TODO: modify to remove lambda? or store it somewhere else if it will actually be reused
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
