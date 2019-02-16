package main.java.com.slimtrade.gui.stash;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.windows.AbstractWindow;

public class ResizableWindow extends AbstractWindow {

	static final long serialVersionUID = 1L;
	private JPanel pullRight = new JPanel();
	private JPanel pullBottom = new JPanel();
	protected JPanel container = new JPanel();

	private int width = 100;
	private int height = 100;

	private int startingX;
	private int startingY;
	private int startingWidth;
	private int startingHeight;
	private boolean mousePressed = false;
	private AbstractWindow local;

	public ResizableWindow() {
		super("Stash Overlay", true);
		pullRight.setBackground(Color.PINK);
		pullBottom.setBackground(Color.CYAN);
		center.setLayout(new BorderLayout());
		// container.add(new BufferPanel(0, bufferSize),
		// BorderLayout.PAGE_START);
		// container.add(new BufferPanel(bufferSize, 0),
		// BorderLayout.LINE_START);
		center.add(pullRight, BorderLayout.LINE_END);
		center.add(pullBottom, BorderLayout.PAGE_END);
		center.setPreferredSize(new Dimension(width, height));
		center.add(container, BorderLayout.CENTER);
		local = this;
		pullRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
		pullBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
		container.setBackground(Color.green);
		pullRight.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startingX = e.getXOnScreen();
				startingY = e.getY();
				startingWidth = local.getWidth();
				startingHeight = local.getHeight();
				mousePressed = true;
			}

			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}
			
//			public void mouseEntered(MouseEvent e){
//				pullRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
//			}
		});

		pullBottom.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// bufferX = e.getX();
				startingX = e.getXOnScreen();
				startingY = e.getYOnScreen();
				startingWidth = local.getWidth();
				startingHeight = local.getHeight();
				mousePressed = true;
			}

			public void mouseReleased(MouseEvent e) {
				mousePressed = false;
			}

			public void mouseDragged(MouseEvent e) {
				
			}
		});

		pullRight.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				new Thread(runnerRight).start();
			}

			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		pullBottom.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				new Thread(runnerBottom).start();
			}

			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	protected Runnable runnerRight = new Runnable() {
		public void run() {
			int mouseX = MouseInfo.getPointerInfo().getLocation().x;
			System.out.println(mouseX);
			int width = startingWidth - (startingX - mouseX);
			if (width % 2 != 0)
				width++;
			local.setPreferredSize(new Dimension(width, startingHeight));
			local.pack();
		}
	};

	protected Runnable runnerBottom = new Runnable() {
		public void run() {
			int mouseY = MouseInfo.getPointerInfo().getLocation().y;
			System.out.println(mouseY);
			int height = startingHeight - (startingY - mouseY);
			if (height % 2 != 0)
				height++;
			local.setPreferredSize(new Dimension(startingWidth, height));
			local.pack();
		}
	};

}
