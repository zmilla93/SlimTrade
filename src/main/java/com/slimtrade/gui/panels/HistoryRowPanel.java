package main.java.com.slimtrade.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.buttons.BasicIconButton_REMOVE;
import main.java.com.slimtrade.gui.dialogs.HistoryWindow;

public class HistoryRowPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int width = (int)(HistoryWindow.width*0.9);
	public static final int height = 25;
	
	private double timeWidthPercent = 0.15;
	private double nameWidthPercent = 0.35;
	private double itemWidthPercent = 0.35;
	private double priceWidthPercent = 0.15;
	public BasicIconButton_REMOVE refreshButton = new BasicIconButton_REMOVE("/resources/icons/refresh1.png", height, height);

	private TradeOffer localTrade;
	
	Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
	Border borderHover = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
	
	public HistoryRowPanel(TradeOffer trade){
		
		//TODO : Could change to grid layout for better spacing
//		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setPreferredSize(new Dimension(width, height));
		this.setMaximumSize(new Dimension(width, height));
		this.setBackground(Color.green);
		
		JPanel timePanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JPanel pricePanel = new JPanel();
		
		//TODO : Change flow layout to better center
		FlowLayout flowCenter = new FlowLayout(FlowLayout.CENTER, 0, 2);
		timePanel.setLayout(flowCenter);
		namePanel.setLayout(flowCenter);
		itemPanel.setLayout(flowCenter);
		pricePanel.setLayout(flowCenter);
		
		int remainingWidth = width-height;
		timePanel.setPreferredSize(new Dimension((int)(remainingWidth*timeWidthPercent), height));
		namePanel.setPreferredSize(new Dimension((int)(remainingWidth*nameWidthPercent), height));
		itemPanel.setPreferredSize(new Dimension((int)(remainingWidth*itemWidthPercent), height));
		pricePanel.setPreferredSize(new Dimension((int)(remainingWidth*priceWidthPercent), height));
		
		timePanel.setBorder(border);
		namePanel.setBorder(border);
		itemPanel.setBorder(border);
		pricePanel.setBorder(border);
		
//		String fixedItemName = trade.itemCount == 0 ? trade.itemName : trade.itemCount.toString().replaceAll("\\.0", "") + " " + trade.itemName;
		
		JLabel timeLabel = new JLabel(trade.date);
		JLabel nameLabel = new JLabel(trade.playerName);
		JLabel itemLabel = new JLabel(TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, true));
		JLabel priceLabel = new JLabel(trade.priceCount + " " + trade.priceTypeString);
		
//		timeLabel.setVerticalAlignment(Alignment.CENTER);
		
		timePanel.add(timeLabel);
		namePanel.add(nameLabel);
		itemPanel.add(itemLabel);
		pricePanel.add(priceLabel);
		
		this.add(refreshButton);
		this.add(timePanel);
		this.add(namePanel);
		this.add(itemPanel);
		this.add(pricePanel);
		
		this.localTrade = trade;
		
		timePanel.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseEntered(java.awt.event.MouseEvent evt) {
			LocalTime fixedTime = LocalTime.parse(trade.time);
			DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:mm a");
			String s = fixedTime.format(f);
			timeLabel.setText(s.replaceAll("\\A0", ""));
		}});
		
		timePanel.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseExited(java.awt.event.MouseEvent evt) {
			timeLabel.setText(trade.date);
		}});
		
		refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {public void mouseClicked(java.awt.event.MouseEvent evt) {
			FrameManager.messageManager.addMessage(localTrade);
		}});
		
		
		
		updateColor();

	}
	
	public void updateColor(){
		refreshButton.setColorPresets(ColorManager.GenericWindow.buttonBG, ColorManager.GenericWindow.buttonBG_hover);
		refreshButton.setBorderPresets(border, border);
		refreshButton.updateColorPresets();
	}
}
