package main.java.com.slimtrade.gui.history;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.basic.GridBagPanel;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.PricePanel;

public class HistoryRow extends JPanel {

	private static final long serialVersionUID = 1L;
	JPanel datePanel;
	JPanel timePanel;
	JPanel playerPanel;
	JPanel itemPanel;
	JPanel pricePanel;

	private Color color = this.getBackground();
	private Color colorHover = color.LIGHT_GRAY;
	
	final int rowHeight = 20;

	public static final int MIN_WIDTH = 500;

	IconButton refreshButton = new IconButton(ImagePreloader.refresh, rowHeight);
	IconButton closeButton = new IconButton(ImagePreloader.close, rowHeight);
	// public HistoryRow()

	public HistoryRow(TradeOffer trade) {
		
		this.setLayout(new BorderLayout());
		GridBagPanel rowPanel = new GridBagPanel();

		this.setMinimumSize(new Dimension(50, rowHeight));
		this.setMaximumSize(new Dimension(1600, rowHeight));

		this.setBackground(Color.RED);

		if (trade == null) {
			datePanel = new GridBagPanel("Date");
			timePanel = new GridBagPanel("Time");
			playerPanel = new GridBagPanel("Player");
			itemPanel = new GridBagPanel("Item");
			pricePanel = new GridBagPanel("Price");
		} else {
			datePanel = new GridBagPanel(trade.date);
			timePanel = new GridBagPanel(trade.time);
			playerPanel = new GridBagPanel(trade.playerName);
			itemPanel = new GridBagPanel(TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, true));
			pricePanel = new PricePanel(trade.priceTypeString, trade.priceCount, false);
		}
		
		//KEEP
//		rowPanel.setBackground(Color.LIGHT_GRAY);
//		Color color = Color.GRAY;
//		datePanel.setBackground(color);
//		timePanel.setBackground(color);
//		playerPanel.setBackground(color);
//		itemPanel.setBackground(color);
//		pricePanel.setBackground(color);

		datePanel.setPreferredSize(new Dimension(60, rowHeight));
		timePanel.setPreferredSize(new Dimension(60, rowHeight));
		playerPanel.setPreferredSize(new Dimension(160, rowHeight));
		itemPanel.setPreferredSize(new Dimension(200, rowHeight));
		pricePanel.setPreferredSize(new Dimension(100, rowHeight));


		datePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		timePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		playerPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		itemPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		pricePanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		datePanel.setOpaque(false);
		timePanel.setOpaque(false);
		playerPanel.setOpaque(false);
		itemPanel.setOpaque(false);
		pricePanel.setOpaque(false);

		GridBagConstraints gc = rowPanel.gc;
		rowPanel.add(refreshButton, gc);
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
		
//		this.setBackground(Color.GREEN);
		
		
		refreshButton.borderDefault = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED);
		refreshButton.borderHover = BorderFactory.createSoftBevelBorder(BevelBorder.RAISED);
		refreshButton.borderPressed = BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED);
//		refreshButton.setMaximumSize(new Dimension(rowHeight, rowHeight));
//		this.setMaximumSize(2000,);
		
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FrameManager.messageManager.addMessage(trade);
			}
		});
		
		
		//TODO : Switch to paint
		refreshButton.addMouseListener(new MouseAdapter(){

			@Override
			public void mouseEntered(MouseEvent arg0) {
				rowPanel.setBackground(colorHover);
//				rowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				rowPanel.setBackground(color);
			}
		});
		
		this.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				rowPanel.setBackground(colorHover);
//				rowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				rowPanel.setBackground(color);
			}
		});
		
		this.add(refreshButton, BorderLayout.WEST);
		this.add(rowPanel, BorderLayout.CENTER);
	}

}
