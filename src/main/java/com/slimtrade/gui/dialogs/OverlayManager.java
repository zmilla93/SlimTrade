package main.java.com.slimtrade.gui.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.basic.BasicMovableDialog;
import main.java.com.slimtrade.gui.buttons.MenubarButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.MessagePanel;

public class OverlayManager {

	LayoutManager gridbag = new GridBagLayout();

	private BasicDialog helpDialog = new BasicDialog();
	private BasicMovableDialog menubarDialog = new BasicMovableDialog();
	private BasicMovableDialog messageDialog = new BasicMovableDialog();
	private JPanel menubarExpandButton = new JPanel();
	private JPanel menubarPanelTop = new JPanel();
	private JPanel menubarPanelBottom = new JPanel();

	private final int BORDER_SIZE = 2;
	private final int MENUBAR_BUTTON_SIZE = MenubarButton.height - BORDER_SIZE * 2;

	Border borderDefault = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, Color.BLACK);
	Border borderScreenLock = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, Color.RED);

	Border borderNW = BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, BORDER_SIZE, Color.BLACK);
	Border borderNE = BorderFactory.createMatteBorder(0, BORDER_SIZE, BORDER_SIZE, 0, Color.BLACK);
	Border borderSW = BorderFactory.createMatteBorder(BORDER_SIZE, 0, 0, BORDER_SIZE, Color.BLACK);
	Border borderSE = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, 0, Color.BLACK);

	Border borderNWLock = BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, BORDER_SIZE, Color.RED);
	Border borderNELock = BorderFactory.createMatteBorder(0, BORDER_SIZE, BORDER_SIZE, 0, Color.RED);
	Border borderSWLock = BorderFactory.createMatteBorder(BORDER_SIZE, 0, 0, BORDER_SIZE, Color.RED);
	Border borderSELock = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, 0, Color.RED);

	Color tempBGColor = new Color(100, 100, 100, 200);

	JComboBox<String> menubarCombo = new JComboBox<String>();
	JComboBox<String> msgPanelCombo = new JComboBox<String>();
	
	GridBagConstraints gcLeft = new GridBagConstraints();
	GridBagConstraints gcRight = new GridBagConstraints();
	
	public OverlayManager() {

		menubarExpandButton.setBackground(Color.LIGHT_GRAY);
		menubarExpandButton.setBorder(borderNW);

		helpDialog.setSize(500, 150);
		menubarDialog.setSize(MenubarDialog.TOTAL_WIDTH, MenubarDialog.TOTAL_HEIGHT);
		messageDialog.setSize(MessagePanel.totalWidth, MessagePanel.totalHeight);
		menubarPanelTop.setPreferredSize(new Dimension(0, MENUBAR_BUTTON_SIZE));
		menubarPanelBottom.setPreferredSize(new Dimension(0, MENUBAR_BUTTON_SIZE));
		menubarExpandButton.setPreferredSize(new Dimension(MENUBAR_BUTTON_SIZE, MENUBAR_BUTTON_SIZE));

		helpDialog.setLayout(gridbag);
		menubarDialog.setLayout(new BorderLayout());
		messageDialog.setLayout(gridbag);
		menubarPanelTop.setLayout(gridbag);
		menubarPanelBottom.setLayout(gridbag);

		helpDialog.getContentPane().setBackground(Color.LIGHT_GRAY);
		menubarDialog.setBackground(tempBGColor);
		messageDialog.setBackground(tempBGColor);
		menubarPanelTop.setOpaque(false);
		menubarPanelBottom.setOpaque(false);
//		menubarPanelTop.setBackground(ColorManager.CLEAR);
//		menubarPanelBottom.setBackground(ColorManager.CLEAR);

		menubarDialog.getRootPane().setBorder(borderDefault);
		messageDialog.getRootPane().setBorder(borderDefault);

		menubarDialog.setBorderOffset(BORDER_SIZE);
		messageDialog.setBorderOffset(BORDER_SIZE);

		GridBagConstraints gcCenter = new GridBagConstraints();


		////HERE
		gcLeft.weightx = 1;
		gcRight.weightx = 1;
		gcLeft.anchor = GridBagConstraints.WEST;
		gcRight.anchor = GridBagConstraints.EAST;

		JLabel messageLabel = new JLabel("Message Panel");
		messageDialog.add(messageLabel, gcCenter);

		JLabel menubarLabel = new JLabel("Menubar");
		menubarLabel.setHorizontalAlignment(JLabel.CENTER);
		menubarDialog.add(menubarLabel, BorderLayout.CENTER);
		menubarDialog.add(menubarPanelTop, BorderLayout.PAGE_START);
		menubarDialog.add(menubarPanelBottom, BorderLayout.PAGE_END);
		menubarPanelTop.add(menubarExpandButton, gcLeft);

		// Helper Panel
		GridBagConstraints gcHelp = new GridBagConstraints();
		gcHelp.gridx = 0;
		gcHelp.gridy = 0;
		JLabel help1 = new JLabel("Layout Manager");
		JLabel help2 = new JLabel("LEFT CLICK : move elements around the screen.");
		JLabel help3 = new JLabel("RIGHT CLICK : toggle monitor lock, preventing items from going off screen.");

		// Options
		GridBagConstraints gcOptions = new GridBagConstraints();
		JPanel optionsPanel = new JPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.setLayout(gridbag);
		gcOptions.gridx = 0;
		gcOptions.gridy = 0;
		
		JLabel menubarButtonLabel = new JLabel("Menubar Expand Button");
		JLabel msgPanelExpandLabel = new JLabel("Message Panel Grow Direction");
		int bufferSize = 10;
		
//		JComboBox<String> menubarCombo = new JComboBox<String>();
//		JComboBox<String> msgPanelCombo = new JComboBox<String>();
		menubarCombo.addItem("Top Left");
		menubarCombo.addItem("Top Right");
		menubarCombo.addItem("Bottom Left");
		menubarCombo.addItem("Bottom Right");
		
		msgPanelCombo.addItem("Upwards");
		msgPanelCombo.addItem("Downwards");
		Dimension panelSize = menubarCombo.getPreferredSize();
		msgPanelCombo.setPreferredSize(panelSize);
				
		optionsPanel.add(menubarButtonLabel, gcOptions);
		gcOptions.gridx = 1;
		optionsPanel.add(new BufferPanel(bufferSize, 0), gcOptions);
		gcOptions.gridx = 2;
		optionsPanel.add(menubarCombo, gcOptions);
		gcOptions.gridx = 0;
		gcOptions.gridy = 1;
		optionsPanel.add(msgPanelExpandLabel, gcOptions);
		gcOptions.gridx = 1;
		optionsPanel.add(new BufferPanel(bufferSize, 0), gcOptions);
		gcOptions.gridx = 2;
		optionsPanel.add(msgPanelCombo, gcOptions);

		helpDialog.add(help1, gcHelp);
		gcHelp.gridy++;
		helpDialog.add(new BufferPanel(0, 15), gcHelp);
		gcHelp.gridy++;
		helpDialog.add(help2, gcHelp);
		gcHelp.gridy++;
		helpDialog.add(help3, gcHelp);
		gcHelp.gridy++;
		helpDialog.add(new BufferPanel(0, 15), gcHelp);
		gcHelp.gridy++;
		helpDialog.add(optionsPanel, gcHelp);

		menubarDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					menubarDialog.toggleScreenLock();
					if (menubarDialog.getScreenLock()) {
						menubarDialog.getRootPane().setBorder(borderScreenLock);
					} else {
						menubarDialog.getRootPane().setBorder(borderDefault);
					}
				}
				updateMenubarButton();
			}
		});

		messageDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					messageDialog.toggleScreenLock();
					if (messageDialog.getScreenLock()) {
						messageDialog.getRootPane().setBorder(borderScreenLock);
					} else {
						messageDialog.getRootPane().setBorder(borderDefault);
					}
				}
			}
		});
		
		menubarCombo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				updateMenubarButton();
			}
		});

		FrameManager.centerFrame(helpDialog);
	}
	
	private void updateMenubarButton(){
		String sel = (String)menubarCombo.getSelectedItem();
		switch(sel){
		case "Top Left":
			if(menubarDialog.getScreenLock()){
				menubarExpandButton.setBorder(borderNWLock);
			}else{
				menubarExpandButton.setBorder(borderNW);
			}
			menubarPanelTop.add(menubarExpandButton, gcLeft);
			break;
		case "Top Right":
			if(menubarDialog.getScreenLock()){
				menubarExpandButton.setBorder(borderNELock);
			}else{
				menubarExpandButton.setBorder(borderNE);
			}
			menubarPanelTop.add(menubarExpandButton, gcRight);
			break;
		case "Bottom Left":
			if(menubarDialog.getScreenLock()){
				menubarExpandButton.setBorder(borderSWLock);
			}else{
				menubarExpandButton.setBorder(borderSW);
			}
			menubarPanelBottom.add(menubarExpandButton, gcLeft);
			break;
		case "Bottom Right":
			if(menubarDialog.getScreenLock()){
				menubarExpandButton.setBorder(borderSELock);
			}else{
				menubarExpandButton.setBorder(borderSE);
			}
			menubarPanelBottom.add(menubarExpandButton, gcRight);
			break;
		}
		menubarDialog.repaint();
	}

	public void show() {
		helpDialog.setVisible(true);
		menubarDialog.setVisible(true);
		messageDialog.setVisible(true);
	}

	public void hide() {
		helpDialog.setVisible(false);
		menubarDialog.setVisible(false);
		messageDialog.setVisible(false);
	}

}
