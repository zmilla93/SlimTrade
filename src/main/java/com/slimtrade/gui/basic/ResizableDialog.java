package main.java.com.slimtrade.gui.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.utility.TradeUtility;

public class ResizableDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;

	protected int offsetX;
	protected int offsetY;
	protected boolean screenLock = false;
	protected boolean mouseDown = false;

	private final JPanel topPanel = new JPanel();
	private final JPanel leftPanel = new JPanel();
	private final JPanel rightPanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	private final JPanel centerPanel = new JPanel();

	private final int borderSize = 10;

	private Point startingWindowPos;
	
	private int startingX;
	private int startingY;
	private int startingWidth;
	private int startingHeight;
	

	private int minWidth = 100;
	private int minHeight = 100;
	private int maxWidth = 1000;
	private int maxHeight = 1000;

	public ResizableDialog() {

		centerPanel.setBackground(Color.YELLOW);
		topPanel.setBackground(Color.GREEN);
		leftPanel.setBackground(Color.ORANGE);
		rightPanel.setBackground(Color.ORANGE);
		bottomPanel.setBackground(Color.GREEN);

		centerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		JLabel text = new JLabel("COOL!");
		centerPanel.add(text, gc);

		Container c = this.getContentPane();
		c.setLayout(new BorderLayout());

		topPanel.setPreferredSize(new Dimension(0, borderSize));
		leftPanel.setPreferredSize(new Dimension(borderSize, 0));
		rightPanel.setPreferredSize(new Dimension(borderSize, 0));
		bottomPanel.setPreferredSize(new Dimension(0, borderSize));

		c.add(centerPanel, BorderLayout.CENTER);
		c.add(topPanel, BorderLayout.PAGE_START);
		c.add(leftPanel, BorderLayout.LINE_START);
		c.add(rightPanel, BorderLayout.LINE_END);
		c.add(bottomPanel, BorderLayout.PAGE_END);

		createListeners(centerPanel);
		
		createListeners(rightPanel);
		createListeners(leftPanel);

	}
	
//    @Override
//    public void paint(Graphics g)
//    {
//        super.paint(g);
//        g.clearRect(0, 0, getWidth(), getHeight());
//        g.setColor(Color.MAGENTA);
//        g.fillOval(0, 0, 100, 100);
//    }

	private void createListeners(JPanel p) {
		p.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					startingWindowPos = getWindowPos();
					startingX = e.getXOnScreen();
					startingY = e.getYOnScreen();
					startingWidth = getWindowWidth();
					startingHeight = getWindowHeight();
					offsetX = e.getX() + borderSize;
					offsetY = e.getY() + borderSize;
					mouseDown = true;
//					runResizerLeft();
					if(p == centerPanel) runWindowMover();
					else if(p == topPanel);
					else if(p == leftPanel) runResizerLeft();
					else if(p == rightPanel) runResizerRight();
					else if(p == bottomPanel);
				}
			}
		});
		
		p.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					mouseDown = false;
				}
			}
		});
	}

	// Threads
	private void runResizerLeft() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int dis = MouseInfo.getPointerInfo().getLocation().x - startingX;
					int targetWidth = startingWidth - dis;
					Point targetPos = new Point(startingWindowPos.x+dis, startingWindowPos.y);
					if (targetWidth < minWidth){
						targetWidth = minWidth;
						targetPos = getWindowPos();
					}
						
					if (targetWidth > maxWidth)
						targetWidth = maxWidth;
					
					moveWindow(targetPos);
					resizeWindow(targetWidth, startingHeight);
					
					refresh();
					
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	private void runResizerRight() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int dis = MouseInfo.getPointerInfo().getLocation().x - startingX;
					int targetWidth = startingWidth + dis;
					if (targetWidth < minWidth)
						targetWidth = minWidth;
					if (targetWidth > maxWidth)
						targetWidth = maxWidth;
					resizeWindow(targetWidth, startingHeight);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private void runWindowMover() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int targetX = MouseInfo.getPointerInfo().getLocation().x - offsetX;
					int targetY = MouseInfo.getPointerInfo().getLocation().y - offsetY;
					if (screenLock) {
						if (targetX < 0) targetX = 0;
						if (targetX > TradeUtility.screenSize.width - getWindowWidth()) targetX = TradeUtility.screenSize.width - getWindowWidth();
						if (targetY < 0) targetY = 0;
						if (targetY > TradeUtility.screenSize.height - getWindowHeight()) targetY = TradeUtility.screenSize.height - getWindowHeight();
					}
					moveWindow(new Point(targetX, targetY));
				}
			}
		}.start();
	}

	// Utility
	
	public void setScreenLock(boolean state) {
		screenLock = state;
	}

	private void resizeWindow(int width, int height) {
		this.setSize(width, height);
	}

	private void moveWindow(Point p) {
		this.setLocation(p);
	}
	
	private Point getWindowPos(){
		return this.getLocation();
	}

	private int getWindowWidth() {
		return this.getWidth();
	}

	private int getWindowHeight() {
		return this.getHeight();
	}

}
