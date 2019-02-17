package main.java.com.slimtrade.gui.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicPanel;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;
import main.java.com.slimtrade.gui.panels.GridPanel;
import main.java.com.slimtrade.gui.stash.helper.ItemHighlighter;


public class StashGridOverlay extends AbstractWindowDialog{
	
	private static final long serialVersionUID = 1L;
	//STATICS
	private static Point windowPos = new Point(0, 0);
	private static Dimension windowSize = new Dimension(400,400);
	private static Point gridPos = new Point(0, 0);
	private static Dimension gridSize;
	
	//SIZES
	private final int minSize = 200;
	private int infoPanelHeight = 32;
	private int buttonWidth = 80;
	private int buttonHeight = 20;
	private int buttonSpacingX = 20;
	private int buttonMarginTop = (infoPanelHeight-buttonHeight)/2;

	//RESIZING
	private int startingX;
	private int startingY;
	private int startingContainerWidth;
	private int startingContainerHeight;
	
	//GRID
	private GridPanel grid;
	private int bufferThin = 5;
	private int bufferThick = 12;
	
	//TODO : Improve naming conventions
	//TODO : Get rid of all the getters and setters and have methods automatically update these instead
	public StashGridOverlay(Point winPos, int winWidth, int winHeight){
		super("Stash Overlay");
		this.setVisible(false);
		this.setLocation(winPos);
		this.setMinimumSize(new Dimension(minSize, minSize));
		this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.1f));
		windowPos = winPos;
//		this.getContentPane().setBackground(ColorManager.CLEAR);
//		container.setBackground(Color.blue);
		this.repaint();
//		gridWidth = winWidth-bufferThin-bufferThick;
//		gridHeight = winHeight-bufferThin-bufferThick-infoPanelHeight;
//		StashGridOverlay.setDefaultGridSize(new Dimension(gridWidth, gridHeight));
		
		container.setLayout(new BorderLayout());
		//TODO : Move clear background to BasicMenuWindow?
		container.setBackground(new Color(1.0f,1.0f,1.0f,0.25f));
		container.setBounds(winPos.x, winPos.y, winWidth, winHeight);
		
		grid = new GridPanel();
		grid.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		grid.setLineColor(Color.GREEN);
		gridPos.setLocation(winPos.x+bufferThin, winPos.y+bufferThin+AbstractWindowDialog.titlebarHeight);
		container.add(grid, BorderLayout.CENTER);
		
		BasicPanel topSpacer = new BasicPanel((int)0+bufferThick+bufferThin, bufferThin);
		container.add(topSpacer, BorderLayout.PAGE_START);
		
		BasicPanel leftSpacer = new BasicPanel();
		container.add(leftSpacer, BorderLayout.LINE_START);
		
		BasicPanel rightPullBar = new BasicPanel(bufferThick, (int)0);
		rightPullBar.setBackground(Color.DARK_GRAY);
		Border b = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		rightPullBar.setBorder(b);
		container.add(rightPullBar, BorderLayout.LINE_END);
		
		//BOTTOM
		BasicPanel bottomPullBar = new BasicPanel(0, bufferThick);
		bottomPullBar.setBackground(Color.DARK_GRAY);
		BasicPanel infoPanel = new BasicPanel(winWidth, infoPanelHeight);
		infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, buttonSpacingX, buttonMarginTop));
		JButton resetButton = new JButton("Reset");
		infoPanel.add(resetButton);
		resetButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		JButton saveButton = new JButton("Save");
		saveButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
		
		infoPanel.add(saveButton);
		
		BasicPanel bottomContainer = new BasicPanel(winWidth, bufferThick+infoPanelHeight);
		bottomPullBar.setBorder(b);
		bottomContainer.setLayout(new BorderLayout());
		bottomContainer.add(bottomPullBar, BorderLayout.PAGE_START);
		bottomContainer.add(infoPanel, BorderLayout.PAGE_END);
		container.add(bottomContainer, BorderLayout.PAGE_END);
		
//		this.resizeStashWindow(minSize*2, minSize*2);

		this.resizeStashWindow(winWidth, winHeight);
//		saveDataLocally();
//		FrameManager.debug.log("WinX : " + winPos.x + "", winPos.y + "", winWidth + "");
//		FrameManager.stashHelperContainer.updateBounds(winPos.x, winPos.y, winWidth);
//		FrameManager.stashHelperContainer.updateBounds();
		
		
		//TODO : Could change to threading
		//Width Adjust
		rightPullBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				startingX = e.getXOnScreen();
		    	startingContainerWidth = getContainerWidth();
		    	startingContainerHeight = getContainerHeight();
		    }
		});
		
		rightPullBar.addMouseMotionListener(new MouseAdapter() {
		    public void mouseDragged(MouseEvent e) {
		    	int dis = startingX-e.getXOnScreen();
		    	resizeStashWindow(startingContainerWidth-dis, startingContainerHeight);
		    }
		});
		
		//Height Adjust
		bottomPullBar.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
		    	startingY = e.getYOnScreen();
		    	startingContainerWidth = getContainerWidth();
		    	startingContainerHeight = getContainerHeight();
		    }
		});
		
		bottomPullBar.addMouseMotionListener(new MouseAdapter() {
		    public void mouseDragged(MouseEvent e) {
		    	int dis = startingY-e.getYOnScreen();
		    	resizeStashWindow(startingContainerWidth, startingContainerHeight-dis);
		    }
		});
		
		//Reset Button
		resetButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				reset();
			}
		});
		
		//Save Button
		saveButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				saveDataLocally();
				Main.fileManager.saveStashData(windowPos.x, windowPos.y, windowSize.width, windowSize.height, gridPos.x, gridPos.y, gridSize.width, gridSize.height);
				FrameManager.stashHelperContainer.updateBounds();
//				ItemHighlighter.saveGridInfo(gridPos.x, gridPos.y, (gridSize.width/12.0), (gridSize.height/12.0));
		    }
		});
		
		closeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				reset();
				hideStashWindow();
				FrameManager.menubar.refreshVisibility();
				FrameManager.menubarToggle.refreshVisibility();
				FrameManager.messageManager.setVisible(true);
				FrameManager.optionsWindow.setVisible(true);
		    }
		});
	}
	
	private int getContainerWidth(){
		return container.getWidth();
	}
	
	private int getContainerHeight(){
		return container.getHeight();
	}
	
	private void hideStashWindow(){
		this.setVisible(false);
	}
	
	//WINDOW GETTERS/SETTERS
	public static void setDefaultWinPos(Point pos){
		StashGridOverlay.windowPos = pos;
	}
	
	public static void setDefaultWinSize(Dimension size){
		StashGridOverlay.windowSize = size;
	}
	
	public static Point getWinPos(){
		return StashGridOverlay.windowPos;
	}
	
	public static Dimension getwindowSize(){
		return StashGridOverlay.windowSize;
	}
	
	//GRID GETTERS/SETTERS
	public static void setDefaultGridPos(Point pos){
		StashGridOverlay.gridPos = pos;
	}
	
	public static void setDefaultGridSize(Dimension size){
		StashGridOverlay.gridSize = size;
	}
	
	public static Point getGridPos(){
		return StashGridOverlay.gridPos;
	}
	
	public static Dimension getGridSize(){
		return StashGridOverlay.gridSize;
	}
	
	private void saveDataLocally(){
		setDefaultWinPos(this.getLocationOnScreen());
		setDefaultWinSize(new Dimension(this.getSize().width, this.getSize().height-AbstractWindowDialog.titlebarHeight));
		setDefaultGridPos(grid.getLocationOnScreen());
		setDefaultGridSize(grid.getSize());
//		ItemHighlighter
	}
	
	public void reset(){
		this.setLocation(windowPos);
		this.resizeStashWindow(windowSize.width, windowSize.height);
	}
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
	public void resizeStashWindow(int width, int height){
		int w = width<this.getMinimumSize().width ? this.getMinimumSize().width : width;
		int h = height<this.getMinimumSize().height ? this.getMinimumSize().height : height;
		double gridWidth = w-bufferThin-bufferThick;
		double gridHeight = h-bufferThin-bufferThick-infoPanelHeight;
		grid.resizeGrid((int)gridWidth, (int)gridHeight);
		StashGridOverlay.setDefaultGridSize(new Dimension((int)gridWidth, (int)gridHeight));
//		gridSize.setSize((int)gridWidth, (int)gridHeight);
		this.resizeWindow(w, h);
		this.revalidate();
		this.repaint();
	}
}
