package main.java.com.slimtrade.gui.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.utility.TradeUtility;

public class ResizableDialog extends BasicDialog {

	private static final long serialVersionUID = 1L;

	private int offsetX;
	private int offsetY;
	private boolean screenLock = false;
	private boolean mouseDown = false;

	private final JPanel topPanel = new JPanel();
	private final JPanel leftPanel = new JPanel();
	private final JPanel rightPanel = new JPanel();
	private final JPanel bottomPanel = new JPanel();
	protected final JPanel centerPanel = new JPanel();

	protected final int borderSize = 10;

	private Point startingPos;
	
	private int startingX;
	private int startingY;
	private int startingWidth;
	private int startingHeight;

	protected int minWidth = 100;
	protected int minHeight = 100;
	private int maxWidth = 1000;
	private int maxHeight = 1000;

	public ResizableDialog() {
		
		Container c = this.getContentPane();
		
		this.setLayout(new BorderLayout());
		c.setLayout(new BorderLayout());
		centerPanel.setLayout(new GridBagLayout());
		
		
		this.setBackground(ColorManager.CLEAR);
		
		centerPanel.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.2f));
		topPanel.setBackground(Color.GREEN);
		leftPanel.setBackground(Color.ORANGE);
		rightPanel.setBackground(Color.ORANGE);
		bottomPanel.setBackground(Color.GREEN);		

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
		
		createListeners(topPanel);
		createListeners(rightPanel);
		createListeners(leftPanel);
		createListeners(bottomPanel);

		this.pack();
		this.setSize(500, 500);
	}
	
	@Override
	public void update(Graphics g){
		System.out.println("!");
	}

	private void createListeners(JPanel p) {
		p.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					startingPos = getWindowPos();
					startingX = e.getXOnScreen();
					startingY = e.getYOnScreen();
					startingWidth = getWindowWidth();
					startingHeight = getWindowHeight();
					offsetX = e.getX() + borderSize;
					offsetY = e.getY() + borderSize;
					mouseDown = true;

					if(p == centerPanel) runWindowMover();
					else if(p == topPanel) runResizerTop();
					else if(p == leftPanel) runResizerLeft();
					else if(p == rightPanel) runResizerRight();
					else if(p == bottomPanel) runResizerBottom();
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
	//TODO : Could consolidate into a single function
	private void runResizerLeft() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int dis = MouseInfo.getPointerInfo().getLocation().x - startingX;
					int targetWidth = startingWidth - dis;
					Point targetPos = new Point(startingPos.x + dis, startingPos.y);
					if (targetWidth < minWidth) {
						targetWidth = minWidth;
						targetPos.x = startingPos.x - minWidth + startingWidth;
					}

					if (targetWidth > maxWidth) {
						targetWidth = maxWidth;
						targetPos.x = startingPos.x - maxWidth + startingWidth;
					}
					setWindowBounds(targetPos.x, targetPos.y, targetWidth, startingHeight);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Resize done");
			}
		}.start();
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
				System.out.println("Resize done");
			}
		}.start();
	}
	
	private void runResizerTop() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int dis = MouseInfo.getPointerInfo().getLocation().y - startingY;
					int targetHeight = startingHeight - dis;
					Point targetPos = new Point(startingPos.x, startingPos.y + dis);
					if (targetHeight < minHeight) {
						targetHeight = minHeight;
						targetPos.y = startingPos.y - minHeight + startingHeight;
					}

					if (targetHeight > maxHeight) {
						targetHeight = maxHeight;
						targetPos.y = startingPos.y - maxHeight + startingHeight;
					}
					setWindowBounds(targetPos.x, targetPos.y, startingWidth, targetHeight);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Resize done");
			}
		}.start();
	}
	
	private void runResizerBottom() {
		new Thread() {
			public void run() {
				while (mouseDown) {
					int dis = MouseInfo.getPointerInfo().getLocation().y - startingY;
					int targetHeight = startingHeight + dis;
					if (targetHeight < minHeight)
						targetHeight = minHeight;
					if (targetHeight > maxHeight)
						targetHeight = maxHeight;
					resizeWindow(startingWidth, targetHeight);
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Resize done");
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

	private void setWindowBounds(int x, int y, int width, int height) {
		this.setBounds(x, y, width, height);
	}

	public void setScreenLock(boolean state) {
		screenLock = state;
	}

	private void resizeWindow(int width, int height) {
		this.setSize(width, height);
	}

	private void moveWindow(Point p) {
		this.setLocation(p);
	}

	private Point getWindowPos() {
		return this.getLocation();
	}

	private int getWindowWidth() {
		return this.getWidth();
	}

	private int getWindowHeight() {
		return this.getHeight();
	}

}
