package com.slimtrade.gui.history;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.DateStyle;
import com.slimtrade.enums.TimeStyle;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.PricePanel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class HistoryRow extends JPanel implements IColorable{

	private static final long serialVersionUID = 1L;
	HistoryCellPanel datePanel;
	HistoryCellPanel timePanel;
	JPanel playerPanel;
	JPanel itemPanel;
	PricePanel pricePanel;

	private Color color = this.getBackground();
	private Color colorHover = color.LIGHT_GRAY;

	public static final int ROW_HEIGHT = 20;

	public static final int MIN_WIDTH = 500;

	IconButton refreshButton = new HistoryRefreshButton();
	IconButton closeButton = new IconButton(DefaultIcons.CLOSE, ROW_HEIGHT);
	// public HistoryRow()

	TradeOffer trade;

	HistoryCellPanel rowPanel;
	
	private Border cellBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
	
	public HistoryRow(TradeOffer trade){
		buildRow(trade, false);
	}
	
	public HistoryRow(TradeOffer trade, boolean close){
		buildRow(trade, close);
	}

	private void buildRow(TradeOffer trade, boolean close) {
		this.trade = trade;
		this.setLayout(new BorderLayout());
		rowPanel = new HistoryCellPanel();

		this.setMinimumSize(new Dimension(50, ROW_HEIGHT));
		this.setMaximumSize(new Dimension(1600, ROW_HEIGHT));

		datePanel = new HistoryCellPanel(trade.date);

		// datePanel = new HistoryCellPanel(newDate);
		timePanel = new HistoryCellPanel(trade.time);
		playerPanel = new HistoryCellPanel(trade.playerName);
		itemPanel = new HistoryCellPanel(TradeUtility.getFixedItemName(trade.itemName, trade.itemQuantity, true));
		pricePanel = new PricePanel(trade.priceTypeString, trade.priceCount, false);

		datePanel.setPreferredSize(new Dimension(60, ROW_HEIGHT));
		timePanel.setPreferredSize(new Dimension(60, ROW_HEIGHT));
		playerPanel.setPreferredSize(new Dimension(160, ROW_HEIGHT));
		itemPanel.setPreferredSize(new Dimension(200, ROW_HEIGHT));
		pricePanel.setPreferredSize(new Dimension(100, ROW_HEIGHT));

		datePanel.setBorder(cellBorder);
		timePanel.setBorder(cellBorder);
		playerPanel.setBorder(cellBorder);
		itemPanel.setBorder(cellBorder);
		pricePanel.setBorder(cellBorder);



		GridBagConstraints gc = rowPanel.gc;
//		rowPanel.add(refreshButton, gc);
		gc.weightx = 1;
		gc.anchor = GridBagConstraints.CENTER;
		gc.fill = GridBagConstraints.BOTH;
		gc.gridx++;
		gc.weightx = 0.2;
		rowPanel.add(datePanel, gc);
		gc.gridx++;
		gc.weightx = 0.2;
		rowPanel.add(timePanel, gc);
		gc.gridx++;
		gc.weightx = 0.4;
		rowPanel.add(playerPanel, gc);
		gc.gridx++;
		gc.weightx = 0.4;
		rowPanel.add(itemPanel, gc);
		gc.gridx++;
		gc.weightx = 0.2;
		rowPanel.add(pricePanel, gc);



//		refreshButton.borderDefault = ColorManager.BORDER_LOW_CONTRAST_1;
//		refreshButton.borderHover = ColorManager.BORDER_TEXT;
//		refreshButton.borderPressed = ColorManager.BORDER_TEXT;

		refreshButton.addActionListener(e -> FrameManager.messageManager.addMessage(trade, false));
		this.updateDate();
		this.updateTime();

		if(close){
			JPanel buttonPanel = new JPanel(new GridBagLayout());
			gc = new GridBagConstraints();
			gc.gridx = 0;
			gc.gridy = 0;
			gc.insets.right = 20;
			buttonPanel.add(closeButton, gc);
			gc.gridx++;
			gc.insets.right = 0;
			buttonPanel.add(refreshButton, gc);
//			buttonPanel.setBorder(cellBorder);
			this.add(buttonPanel, BorderLayout.WEST);
		}else{
			this.add(refreshButton, BorderLayout.WEST);
		}
		
		this.add(rowPanel, BorderLayout.CENTER);

	}

	public void updateDate() {
		try {
			LocalDate date = LocalDate.parse(trade.date, DateTimeFormatter.ISO_DATE);
			DateStyle style;
			if(HistoryWindow.dateStyle == null) {
				style = DateStyle.values()[0];
			} else {
				style = HistoryWindow.dateStyle;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style.getFormat());
			String newDate = date.format(formatter);
			datePanel.setLabel(newDate);
		} catch (DateTimeParseException e) {
			// e.printStackTrace();
		}

	}

	public void updateTime() {
		try {
			LocalTime time = LocalTime.parse(trade.time, DateTimeFormatter.ISO_TIME);
			TimeStyle style;
			if(HistoryWindow.timeStyle == null) {
				style = TimeStyle.values()[0];
			} else {
				style = HistoryWindow.timeStyle;
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(style.getFormat());
			String newTime = time.format(formatter);
			timePanel.setLabel(newTime);
		} catch (DateTimeParseException e) {

		}
	}

	@Override
	public void updateColor() {

		rowPanel.setBackground(ColorManager.BACKGROUND);

		refreshButton.setBackground(ColorManager.LOW_CONTRAST_1);
		refreshButton.borderDefault = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED);
		refreshButton.borderHover = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED);
		refreshButton.borderPressed = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED);

		datePanel.setBackground(ColorManager.BACKGROUND);
		timePanel.setBackground(ColorManager.BACKGROUND);
		playerPanel.setBackground(ColorManager.BACKGROUND);
		itemPanel.setBackground(ColorManager.BACKGROUND);
		pricePanel.setBackground(ColorManager.BACKGROUND);
		pricePanel.getLabel().setForeground(ColorManager.TEXT);
	}
}
