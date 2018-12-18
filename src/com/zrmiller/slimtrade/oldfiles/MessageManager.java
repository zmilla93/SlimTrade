package com.zrmiller.slimtrade.oldfiles;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

@SuppressWarnings("serial")
public class MessageManager extends JPanel{
	
	Robot robot;
	REF_MSG_WINDOW ref = new REF_MSG_WINDOW();
	int messageCount = 0;
	//TODO: move thankMsg and user to better location once options are added
	String thankMsg = "thanks";
	String user = "SmashyMcFireBalls_Delve";
	//JFrame screenFrame;
	JPanel stashPane = new JPanel();
	JPanel outlinePane = new JPanel();
	
	public MessageManager(JFrame frame) throws AWTException{
		robot = new Robot();
		//temp
		stashPane.setLayout(null);
		stashPane.setBounds(18, 162, 630, 630);
		stashPane.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		stashPane.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {System.out.println(evt.getXOnScreen() + " : " + evt.getYOnScreen());}
		});
		//outlinePane.setBackground(new Color(1.0f, 1.0f, 1.0f, 1f));
		outlinePane.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.5f));
		outlinePane.setBorder(BorderFactory.createLineBorder(Color.red));
		stashPane.add(outlinePane);
		frame.add(stashPane);
		
		
		//Stuff
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setLocation(ref.offsetX, ref.offsetY);
		//this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0f));
		this.setBackground(Color.red);
		refresh();
	}
	
	public void refresh(){
		this.setSize(ref.totalWidth,ref.totalHeight*messageCount);
		this.revalidate();
		this.repaint();
	}
	
	public void addMessage(TradeOffer trade){
		MessageWindow msg = new MessageWindow(trade);
		add(msg);
		//TODO: add switch for msgtype
		msg.itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {outlineItem(trade.stashtabX, trade.stashtabY);}
		});
		msg.itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseExited(java.awt.event.MouseEvent evt) {hideItemOutline();}
		});
		msg.closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {removeMessage(msg);}
		});
		msg.inviteButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.inviteButton);pasteIntoPOE("/invite " + trade.name);}
		});
		msg.tpToPlayerButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.tpToPlayerButton);pasteIntoPOE("/hideout " + trade.name);}
		});
		msg.tradeButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.tradeButton);pasteIntoPOE("/tradewith " + trade.name);}
		});
		msg.thankButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.thankButton);pasteIntoPOE("@" + trade.name + " " + thankMsg);}
		});
		msg.kickButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.kickButton);pasteIntoPOE("/kick " + trade.name);}
		});
		msg.leaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.leaveButton);pasteIntoPOE("/kick " + user);}
		});
		msg.tpHomeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {buttonMutual(msg.tpHomeButton);pasteIntoPOE("/hideout");}
		});
		messageCount++;
		refresh();
	}
	
	private void buttonMutual(JButton b){
		b.setBackground(ref.buttonCompleteColor);
		focusPOE();
	}
	
	private void removeMessage(MessageWindow m){
		messageCount--;
		remove(m);
		refresh();
	}
	
	private void outlineItem(int x, int y){
		System.out.println("Showing item outline...");
		double width = ((double)stashPane.getWidth()/12);
		double height = ((double)stashPane.getHeight()/12);
		outlinePane.setBounds((int)(width*(x-1)), (int)(height*(y-1)), (int)width, (int)height);
		stashPane.setVisible(true);
	}
	
	private void hideItemOutline(){
		System.out.println("Hiding item outline...");
		stashPane.setVisible(false);
		//outlinePane.revalidate();
		//outlinePane.repaint();
	}
	
	private void pasteIntoPOE(String s){
		//Move object creation?
		StringSelection pasteString = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(pasteString, null);
		this.focusPOE();
		robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
	}

	 private void focusPOE() {
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
