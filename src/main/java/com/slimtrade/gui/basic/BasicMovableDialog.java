package main.java.com.slimtrade.gui.basic;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.java.com.slimtrade.core.utility.TradeUtility;

//TODO : REVIEW THIS CODE!!
public class BasicMovableDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;
	
	protected int offsetX;
	protected int offsetY;
	protected boolean screenLock = false;
	protected boolean mouseDown = false;
	private int borderOffset = 0;
	
	private JPanel mover;
	
	public BasicMovableDialog(){
//		createListeners((JPanel)this.getContentPane());
	}
	
	public BasicMovableDialog(boolean createListeners){
		if(createListeners){
			createListeners((JPanel) this.getContentPane());
		}
	}
	
	public void createListeners(JPanel p){
		mover = p;
		p.addMouseListener(new MouseAdapter() {
		    public void mousePressed(MouseEvent e) {
		    	if(e.getButton() == MouseEvent.BUTTON1){
		    		offsetX = e.getX();
		    		offsetY = e.getY();
		    		mouseDown = true;
		    		runWindowMover();
		    	}
		    }
		});
		
		p.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					mouseDown = false;
				}
			}
		});
	}
	
	//TODO : Make this function cleaner, then use it for runWindowMover
	public void forceOntoScreen(int x, int y){
		int targetX = x;
		int targetY = y;
		if(targetX<0) targetX = 0;
		if(targetX>TradeUtility.screenSize.width-mover.getWidth()) targetX = TradeUtility.screenSize.width-mover.getWidth();
		if(targetY<0) targetY = 0;
		if(targetY>TradeUtility.screenSize.height-mover.getHeight()) targetY = TradeUtility.screenSize.height-mover.getHeight();
		moveWindow(new Point(targetX, targetY));
	}
	
	public void setScreenLock(boolean state){
		screenLock = state;
	}
	
	public void toggleScreenLock(){
		screenLock = !screenLock;
		if(screenLock){
			forceOntoScreen(this.getX(), this.getY());
		}
	}
	
	public void setBorderOffset(int borderOffset){
		this.borderOffset = borderOffset;
	}
	
	public boolean getScreenLock(){
		return screenLock;
	}
	
	private void moveWindow(Point p){
		this.setLocation(p);
	}
	
	private void runWindowMover(){
		new Thread(){
			public void run(){
				while(mouseDown){
					int targetX = MouseInfo.getPointerInfo().getLocation().x-offsetX-borderOffset;
					int targetY = MouseInfo.getPointerInfo().getLocation().y-offsetY-borderOffset;
					if(screenLock){
						if(targetX<0) targetX = 0;
						if(targetX>TradeUtility.screenSize.width-mover.getWidth()-borderOffset*2) targetX = TradeUtility.screenSize.width-mover.getWidth()-borderOffset*2;
						if(targetY<0) targetY = 0;
						if(targetY>TradeUtility.screenSize.height-mover.getHeight()-borderOffset*2) targetY = TradeUtility.screenSize.height-mover.getHeight()-borderOffset*2;
					}
					moveWindow(new Point(targetX, targetY));
				}
			}
		}.start();
	}
	

}
