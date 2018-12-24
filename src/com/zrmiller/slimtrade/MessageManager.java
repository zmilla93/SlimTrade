package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.windows.MessageWindow;

public class MessageManager extends JPanel{

	private static final long serialVersionUID = 1L;
	private int msgGapSize = 1;
	Robot robot;
	
	public MessageManager(){
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setBounds(1000, 5, 0, 0);
		this.setBackground(ColorManager.CLEAR);
//		this.addMessage(new TradeOffer(MessageType.INCOMING_TRADE, null, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
//		this.addMessage(new TradeOffer(MessageType.OUTGOING_TRADE, null, "StabbyMcDaggerCloud", "ITEM NAME", 3.5, CurrencyType.CHAOS_ORB, 3.5, "STASHTAB", 1, 1));
//		this.setVisible(true);
	}
	
	private void refresh(){
		Dimension d = new Dimension(MessageWindow.totalWidth, MessageWindow.totalHeight*(this.getComponentCount()/2) + msgGapSize*(this.getComponentCount()/2));
		this.setPreferredSize(d);
		this.setSize(d);
		this.revalidate();
		this.repaint();
	}
	
	//TODO : This may be causing a memory leak...
	//Should move messages to another spot in memory and limit how much can be used
	public void addMessage(TradeOffer trade){
		MessageWindow msg = new MessageWindow(trade);
		BasicPanel buffer = new BasicPanel(MessageWindow.totalWidth, msgGapSize, ColorManager.CLEAR);
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	remove(msg);
		    	remove(buffer);
		    	refresh();
		    	focusPoe();
		    }
		});
		this.add(msg);
		this.add(buffer);
		refresh();
	}
	
	 private void focusPoe() {
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
		
	 private void pasteIntoPoe(String s){
		//Move object creation?
		StringSelection pasteString = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(pasteString, null);
		this.focusPoe();
		robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	    robot.keyPress(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_V);
	    robot.keyRelease(KeyEvent.VK_CONTROL);
	    robot.keyPress(KeyEvent.VK_ENTER);
	    robot.keyRelease(KeyEvent.VK_ENTER);
	}
}
