package main.java.com.slimtrade.gui.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.windows.AbstractWindow;

public abstract class AbstractResizableWindow extends AbstractWindow {

	static final long serialVersionUID = 1L;
	private JPanel pullRight = new JPanel();
	private JPanel pullBottom = new JPanel();
	protected JPanel container = new JPanel();

	private int pullbarSize = 5;

	private int startingX;
	private int startingY;
	private int startingWidth;
	private int startingHeight;
	private boolean mousePressed = false;
	private AbstractWindow local;

	private final int SLEEP_DURATION = 20;

	public AbstractResizableWindow(String title) {
		super(title, true);
		buildWindow(title, true);
	}

	public AbstractResizableWindow(String title, boolean closeButton) {
		super(title, closeButton);
		buildWindow(title, closeButton);
	}

	private void buildWindow(String title, boolean closeButton) {
		// container.setOpaque(false);
		this.setMinimumSize(new Dimension(200, 200));
		pullRight.setBackground(Color.DARK_GRAY);
		pullBottom.setBackground(Color.DARK_GRAY);
		pullRight.setPreferredSize(new Dimension(pullbarSize, 0));
		pullBottom.setPreferredSize(new Dimension(0, pullbarSize));
		center.setLayout(new BorderLayout());
		center.add(pullRight, BorderLayout.LINE_END);
		center.add(pullBottom, BorderLayout.PAGE_END);
		center.add(container, BorderLayout.CENTER);
		pullRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		pullBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		local = this;
		pullRight.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startingX = e.getXOnScreen();
				startingY = e.getY();
				startingWidth = local.getWidth();
				startingHeight = local.getHeight();
				mousePressed = true;
				new Thread(runnerRight).start();
			}

			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}
		});

		pullBottom.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// bufferX = e.getX();
				startingX = e.getXOnScreen();
				startingY = e.getYOnScreen();
				startingWidth = local.getWidth();
				startingHeight = local.getHeight();
				mousePressed = true;
				new Thread(runnerBottom).start();
			}

			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}
		});
	}

	public void autoResize() {
		Dimension size = container.getPreferredSize();
		size.width += this.BORDER_THICKNESS * 2 + this.pullbarSize;
		size.height += this.TITLEBAR_HEIGHT + this.BORDER_THICKNESS + this.pullbarSize;
		this.setPreferredSize(size);
	}

	protected Runnable runnerRight = new Runnable() {
		// JPanel local = this;
		public void run() {
			while (mousePressed) {
				int mouseX = MouseInfo.getPointerInfo().getLocation().x;
				// System.out.println(mouseX);
				int width = startingWidth - (startingX - mouseX);
				if (width % 2 != 0)
					width++;
				// int maxWidth = local.getMaximumSize().width;
				// if(width>maxWidth){
				// width = maxWidth;
				// }
				local.setPreferredSize(new Dimension(width, startingHeight));
				local.pack();
				local.repaint();
				try {
					Thread.sleep(SLEEP_DURATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	protected Runnable runnerBottom = new Runnable() {
		public void run() {
			while (mousePressed) {
				int mouseY = MouseInfo.getPointerInfo().getLocation().y;
				// System.out.println(mouseY);
				int height = startingHeight - (startingY - mouseY);
				if (height % 2 != 0)
					height++;
				local.setPreferredSize(new Dimension(startingWidth, height));
				local.pack();
				local.repaint();
				try {
					Thread.sleep(SLEEP_DURATION);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

}
