package main.java.com.slimtrade.gui.messaging;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.StashHelper;

public class TradePanelA extends AbstractMessagePanel {

	private static final long serialVersionUID = 1L;

	private JPanel namePanel = new JPanel(new GridBagLayout());
	private JPanel pricePanel = new JPanel(new GridBagLayout());
	private JPanel itemPanel = new JPanel(new GridBagLayout());
	protected JPanel topPanel = new JPanel();
	protected JPanel bottomPanel = new JPanel();
	
	private JLabel nameLabel = new JLabel("NAME");
	private JLabel priceLabel = new JLabel("PRICE");
	private JLabel itemLabel = new JLabel("ITEM");

	protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	
	private TradeOffer trade;
	private StashHelper stashHelper;
	
	private int buttonCountTop;
	private int buttonCountBottom;
	
	public TradePanelA() {
		topPanel.setLayout(gb);
		bottomPanel.setLayout(gb);
		
		minHeight = 40;
		maxHeight = 100;
		messageHeight = 40;
		messageWidth = messageHeight * 10;
		borderSize = 2;
		rowHeight = messageHeight/2;
		totalWidth = messageWidth + (borderSize * 4);
		totalHeight = messageHeight + (borderSize * 4);
		
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		borderPanel.setPreferredSize(new Dimension(messageWidth + borderSize * 2, messageHeight + borderSize * 2));
		container.setPreferredSize(new Dimension(messageWidth, messageHeight));
		Dimension size = new Dimension(messageWidth, rowHeight);
		topPanel.setPreferredSize(size);
		bottomPanel.setPreferredSize(size);
		
		namePanel.setBackground(Color.LIGHT_GRAY);
		pricePanel.setBackground(Color.GRAY);
		itemPanel.setBackground(Color.DARK_GRAY);
		buttonPanelTop.setBackground(Color.ORANGE);
		buttonPanelBottom.setBackground(Color.YELLOW);

		namePanel.setPreferredSize(new Dimension(40, rowHeight));
		pricePanel.setPreferredSize(new Dimension(40, rowHeight));
		itemPanel.setPreferredSize(new Dimension(40, rowHeight));
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
		
		
		
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 0.7;

		// TOP PANEL
		topPanel.add(namePanel, gc);
		gc.gridx++;
		gc.weightx = 0.3;
		topPanel.add(pricePanel, gc);
		gc.gridx++;
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0.0;
		topPanel.add(buttonPanelTop, gc);

		// BOTTOM PANEL
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 0;
//		gc.gridwidth = 1;
		bottomPanel.add(itemPanel, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0.0;
		gc.gridx++;
		bottomPanel.add(buttonPanelBottom, gc);
		
		buttonPanelTop.add(new IconButton(ImagePreloader.rad, 20));
		buttonPanelTop.add(new IconButton(ImagePreloader.rad, 20));
		buttonPanelTop.add(new IconButton(ImagePreloader.rad, 20));
		
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb1.png", 20));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", 20));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", 20));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", 20));
//		buttonPanelBottom.add(new IconButton("/resources/icons/thumb2.png", 20));
		buttonPanelBottom.add(new IconButton(ImagePreloader.rad, 20));

		this.revalidate();
		this.repaint();
	}
	
	public void setButtonCount(int top, int bottom){
		this.buttonCountTop = top;
		this.buttonCountBottom = bottom;
		buttonPanelTop.setPreferredSize(new Dimension(rowHeight*top, rowHeight));
		buttonPanelTop.setMaximumSize(new Dimension(rowHeight*top, rowHeight));
		buttonPanelBottom.setPreferredSize(new Dimension(rowHeight*bottom, rowHeight));
		buttonPanelBottom.setMaximumSize(new Dimension(rowHeight*bottom, rowHeight));
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
