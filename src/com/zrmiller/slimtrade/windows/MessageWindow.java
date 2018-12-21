package com.zrmiller.slimtrade.windows;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.TradeOffer;
import com.zrmiller.slimtrade.buttons.BasicButton;
import com.zrmiller.slimtrade.panels.StashHelper;

public class MessageWindow extends JPanel{
	
	//GLOBAL
	private static final long serialVersionUID = 1L;
	private static int width = 400;
	private static int height= 40;
	private static int borderThickness = 2;
	private int rowHeight = height/2;
	public static int totalWidth = width+borderThickness*4;
	public static int totalHeight = height+borderThickness*4;
	public JButton closeButton;
	private TradeOffer trade;
	
	//ITEM HIGHLIGHTER + TIMER
	private StashHelper stashHelper = new StashHelper();
	private JPanel itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	private Timer highlighterTimer = new Timer(2500, hideHighlighter);
	
	
	public MessageWindow(TradeOffer trade){
		this.trade = trade;
		 // TODO : Optimize
		 // Size Variables
		 
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		
		//BORDER
		JPanel bgOuter = new JPanel();
		bgOuter.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		bgOuter.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		bgOuter.setBackground(Color.RED);
		
		JPanel bgInner = new JPanel();
		bgInner.setLayout(new FlowLayout(FlowLayout.CENTER, borderThickness, borderThickness));
		bgInner.setPreferredSize(new Dimension(width+borderThickness*2, height+borderThickness*2));
		bgInner.setBackground(Color.GREEN);
		bgOuter.add(bgInner, BorderLayout.CENTER);
		
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(width, height));
		container.setBackground(Color.YELLOW);
		bgInner.add(container, BorderLayout.CENTER);
		
		this.add(bgOuter);
		
		//TOP PANEL
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		topPanel.setPreferredSize(new Dimension(width, rowHeight));
		topPanel.setBackground(Color.CYAN);
		container.add(topPanel, BorderLayout.PAGE_START);
		
		JPanel namePanel = new JPanel();
		//TODO : MODIFY TO CENTER TEXT VERITCALLY
		namePanel.setLayout(new FlowLayout(FlowLayout.LEFT, borderThickness, borderThickness));
		JLabel nameLabel = new JLabel(trade.playerName);
		namePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		nameLabel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		namePanel.setBackground(Color.YELLOW);
		namePanel.add(nameLabel);
		topPanel.add(namePanel);
		
		JPanel pricePanel = new JPanel();
		JLabel priceLabel = new JLabel(trade.priceType.toString());
		pricePanel.setPreferredSize(new Dimension((int)(width*0.4), rowHeight));
		pricePanel.setBackground(Color.ORANGE);
		pricePanel.add(priceLabel);
		topPanel.add(pricePanel);
		
		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		topButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		topButtonPanel.setBackground(Color.BLUE);
		topPanel.add(topButtonPanel);
		
		JButton button2 = new JButton();
		button2.setPreferredSize(new Dimension(rowHeight, rowHeight));
		topButtonPanel.add(button2);
		
		closeButton = new BasicButton(rowHeight, rowHeight);
		closeButton.setPreferredSize(new Dimension(rowHeight, rowHeight));
		topButtonPanel.add(closeButton);
		
		/*
		 * CENTER PANEL
		 */
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		centerPanel.setPreferredSize(new Dimension(width,rowHeight));
		centerPanel.setBackground(Color.RED);
		container.add(centerPanel, BorderLayout.CENTER);
		
		JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel itemLabel = new JLabel(trade.itemName);
		itemPanel.setPreferredSize(new Dimension((int)(width*0.8), rowHeight));
		itemPanel.setBackground(Color.GRAY);
		itemPanel.add(itemLabel, BorderLayout.CENTER);
		centerPanel.add(itemPanel);
		
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		centerButtonPanel.setPreferredSize(new Dimension((int)(width*0.2), rowHeight));
		centerButtonPanel.setBackground(Color.MAGENTA);
		centerPanel.add(centerButtonPanel);
		
		JButton button3 = new JButton();
		button3.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button3);
		
		JButton button4 = new JButton();
		button4.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button4);
		
		JButton button5 = new JButton();
		button5.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button5);
		
		JButton button6 = new JButton();
		button6.setPreferredSize(new Dimension(rowHeight, rowHeight));
		centerButtonPanel.add(button6);
		
		Border blankBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		Border hoverBorder = BorderFactory.createLineBorder(Color.RED);
		
//		itemHighlighter.setSize(Overlay.stashWindow.grid.getWidth()/12, Overlay.stashWindow.grid.getWidth()/12);
		itemHighlighter = new JPanel();
		itemHighlighter.setVisible(false);
		double cellWidth = (double)StashWindow.grid.getWidth()/12;
		double cellHeight = (double)StashWindow.grid.getHeight()/12;
		itemHighlighter.setPreferredSize(new Dimension((int)cellWidth, (int)cellHeight));
		itemHighlighter.setBounds((int)(Overlay.stashWindow.grid.getLocationOnScreen().x+((trade.stashtabX-1)*cellWidth)), (int)(Overlay.stashWindow.grid.getLocationOnScreen().y+((trade.stashtabY-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
		itemHighlighter.setBackground(Color.blue);
		Overlay.screenContainer.add(itemHighlighter);
//		Overlay.screenContainer.revalidate();
//		Overlay.screenContainer.repaint();
		
//		itemHighlighter.setBounds(Overlay.stashWindow.grid.getX()*(trade.stashtabX-1), Overlay.stashWindow.grid.getX()*(trade.stashtabY-1), Overlay.stashWindow.grid.getWidth()/12, Overlay.stashWindow.grid.getWidth()/12);
//		itemHighlighter.setBounds(0, 0, Overlay.stashWindow.grid.getWidth()/12, Overlay.stashWindow.grid.getWidth()/12);

//		BasicPanel stashHelper = new BasicPanel(80, 20, Color.green);
//		stashHelper.setBounds(0, 0, 80, 20);
//		stashHelper.add(new JLabel("NAME"));
//		Overlay.screenContainer.add(stashHelper);
//		stashHelper.setSize(new Dimension(80, 20));
//		stashHelper.setLocation(0, 0);
//		
		//Item Panel Actions
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	itemPanel.setBorder(hoverBorder);
		    }
		});
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent evt) {
				itemPanel.setBorder(blankBorder);
			}
		});
		itemPanel.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	Overlay.stashHelperContainer.add(stashHelper);
		    	Overlay.stashHelperContainer.refresh();
		    }
		});
		
		//Stash Helper
		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		    	highlighterTimer.stop();
		    	itemHighlighter.setVisible(true);
		    }
		});
		
		stashHelper.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				highlighterTimer.restart();
			}
		});
		
	}

}