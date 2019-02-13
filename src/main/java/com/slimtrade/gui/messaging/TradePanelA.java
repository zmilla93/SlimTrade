package main.java.com.slimtrade.gui.messaging;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.StashHelper;

public class TradePanelA extends AbstractMessagePanel {
	 
	private static final long serialVersionUID = 1L;

	private JPanel namePanel = new NameClickPanel();
	private JPanel pricePanel = new JPanel(gb);
	private JPanel itemPanel = new ItemClickPanel();
	protected JPanel topPanel = new JPanel(gb);
	protected JPanel bottomPanel = new JPanel(gb);
	
	private JLabel nameLabel = new JLabel("NAME");
	private JLabel priceLabel = new JLabel("PRICE");
	private JLabel itemLabel = new JLabel("ITEM");

	protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	
	private TradeOffer trade;
	private StashHelper stashHelper;
	
	private int buttonCountTop;
	private int buttonCountBottom;
	
	public TradePanelA(int size){
		super(size);
		buildPanel(size, true);
	}
	
	public TradePanelA(int size, boolean makeListeners){
		super(size);
		buildPanel(size, makeListeners);
	}
	
	private void buildPanel(int size, boolean makeListeners) {
		//TODO : move size stuff to super
		if(size%2!=0){
			size++;
		}
		nameLabel.setLayout(gb);
		
//		nameLabel
		
		
		//Sizing
//		topPanel.setLayout(gb);
//		bottomPanel.setLayout(gb);
		minHeight = size;
		maxHeight = 100;
		messageHeight = size;
		messageWidth = messageHeight * 10;
		borderSize = 2;
		rowHeight = messageHeight/2;
		totalWidth = messageWidth + (borderSize * 4);
		totalHeight = messageHeight + (borderSize * 4);
		
		
//		nameLabel.setVerticalAlignment(JLabel.CENTER);
//		nameLabel.setAlignmentY(JLabel.CENTER_ALIGNMENT);
		
		
		refreshFont(rowHeight);
		
		nameLabel.setFont(font);
		priceLabel.setFont(font);
		itemLabel.setFont(font);
		
		
		///NAME TESTING
		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.CENTER);
		
		pricePanel.setLayout(new BorderLayout());
		itemPanel.setLayout(new BorderLayout());
		
		nameLabel.setPreferredSize(new Dimension(messageWidth, rowHeight/2));
		nameLabel.setMaximumSize(new Dimension(messageWidth, rowHeight/2));
		
		
		pricePanel.add(priceLabel, BorderLayout.CENTER);
		priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		itemPanel.add(itemLabel, BorderLayout.CENTER);
		itemLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		nameLabel.revalidate();
		nameLabel.repaint();
		
		double bottomButtonWeight = 1*0.005;
		double itemWeight = 1-bottomButtonWeight-timerWeight;
		
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		borderPanel.setPreferredSize(new Dimension(messageWidth + borderSize * 2, messageHeight + borderSize * 2));
		container.setPreferredSize(new Dimension(messageWidth, messageHeight));
		Dimension s = new Dimension(messageWidth, rowHeight);
		topPanel.setPreferredSize(s);
		bottomPanel.setPreferredSize(s);
		
		//Color
		namePanel.setBackground(Color.LIGHT_GRAY);
		nameLabel.setOpaque(true);;
		nameLabel.setBackground(Color.green);
		pricePanel.setBackground(Color.GRAY);
		itemPanel.setBackground(Color.DARK_GRAY);
		buttonPanelTop.setBackground(Color.ORANGE);
		buttonPanelBottom.setBackground(Color.YELLOW);
		
		this.setButtonCount(3, 5);
		
		this.setBackground(Color.BLACK);
		borderPanel.setBackground(Color.CYAN);
		container.setBackground(Color.BLACK);
		topPanel.setBackground(Color.RED);
		bottomPanel.setBackground(Color.RED);
		
		container.add(topPanel, gc);
		gc.gridy = 1;
		container.add(bottomPanel, gc);
		gc.gridy = 0;
		borderPanel.add(container, gc);
		this.add(borderPanel, gc);
		
		
		
//		gc.fill = GridBagConstraints.BOTH;
//		gc.weightx = 0.7;

		// TOP PANEL
		topPanel.add(namePanel, gc);
		gc.gridx++;
		topPanel.add(pricePanel, gc);
		gc.gridx++;
		topPanel.add(buttonPanelTop, gc);

		// BOTTOM PANEL
		gc.gridx = 0;
		gc.gridy = 0;
		bottomPanel.add(timerPanel, gc);
		gc.gridx++;
		bottomPanel.add(itemPanel, gc);
		gc.gridx++;
		bottomPanel.add(buttonPanelBottom, gc);
		
		buttonPanelTop.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
		buttonPanelTop.add(new IconButton("/resources/icons/thumb1.png", rowHeight));
		this.setCloseButton(rowHeight, true);
		buttonPanelTop.add(closeButton);
		
		buttonPanelBottom.add(new IconButton("/resources/icons/thumb1.png", rowHeight));
		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", rowHeight));
//		buttonPanelBottom.add(new IconButton(ImagePreloader.rad, 20));

		
		
		this.revalidate();
		this.repaint();
	}
	
	public void setButtonCount(int top, int bottom){
		this.buttonCountTop = top;
		this.buttonCountBottom = bottom;
		Dimension sizeTop = new Dimension(rowHeight*top, rowHeight);
		Dimension sizeBottom= new Dimension(rowHeight*bottom, rowHeight);
		buttonPanelTop.setPreferredSize(sizeTop);
		buttonPanelTop.setMinimumSize(sizeTop);
		buttonPanelBottom.setPreferredSize(sizeBottom);
		buttonPanelBottom.setMaximumSize(sizeBottom);
		int nameWidth = (int)((messageWidth-sizeTop.width)*0.7);
		int priceWidth = messageWidth-nameWidth-sizeTop.width;
		int timerWidth = (int)(messageWidth*timerWeight);
//		System.out.println("TIMER WIDTH : " + timerWidth);
		int itemWidth = messageWidth-timerWidth-sizeBottom.width;
		
		namePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
//		namePanel.setMinimumSize(new Dimension(nameWidth, rowHeight));
		pricePanel.setPreferredSize(new Dimension(priceWidth, rowHeight));
//		pricePanel.setMinimumSize(new Dimension(priceWidth, rowHeight));
		timerPanel.setPreferredSize(new Dimension(timerWidth, rowHeight));
		itemPanel.setPreferredSize(new Dimension(itemWidth, rowHeight));
		
//		DEBUG
//		System.out.println("\nNAME\t\tPRICE\t\tBUTTONS");
//		System.out.println(messageWidth);
//		System.out.println(nameWidth + "\t\t"  + priceWidth + "\t\t" + sizeTop.width);
//		System.out.println(nameWidth + priceWidth+ sizeTop.width + "\n");
	}
	
	public JButton getCloseButton(){
		return this.closeButton;
	}

	public TradeOffer getTrade() {
		return trade;
	}

	public void setTrade(TradeOffer trade) {
		this.trade = trade;
	}

	public StashHelper getStashHelper() {
		return stashHelper;
	}

	public void setStashHelper(StashHelper stashHelper) {
		this.stashHelper = stashHelper;
	}
	
	

}
