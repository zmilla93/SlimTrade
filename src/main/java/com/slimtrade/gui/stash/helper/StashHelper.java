package com.slimtrade.gui.stash.helper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.slimtrade.Main;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.ButtonType;
import com.slimtrade.core.observing.poe.PoeInteractionEvent;
import com.slimtrade.core.observing.poe.PoeInteractionListener;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicPanel;

public class StashHelper extends JPanel {

	private static final long serialVersionUID = 1L;

	public static int width = 150;
	public static int height = 40;

	// private int x;
	// private int y;
	private int borderThickness = 2;
	// private String itemName;

	public ItemHighlighter itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	public Timer highlighterTimer = new Timer(2000, hideHighlighter);

	private PoeInteractionListener poeInteractionListener = Main.macroEventManager;

	public StashHelper(TradeOffer trade, Color colorBackground, Color colorForeground) {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, borderThickness));
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createLineBorder(colorForeground, borderThickness, true));

		BasicPanel stashPanel = new BasicPanel(width, height / 3, new FlowLayout(FlowLayout.CENTER, 0, 0));
		String fixedStashtabName = trade.stashtabName == null ? "(~price " + Double.toString(trade.priceCount).replaceAll("[.]0", "") + " " + trade.priceTypeString + ")" : trade.stashtabName;
		JLabel stashLabel = new JLabel(fixedStashtabName);
		stashLabel.setForeground(colorForeground);
		stashPanel.add(stashLabel);

		BasicPanel itemPanel = new BasicPanel(width, height / 3, new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel itemLabel = new JLabel(trade.itemName);
		itemLabel.setForeground(colorForeground);
		itemPanel.add(itemLabel);
		this.add(stashPanel);
		this.add(itemPanel);
		this.add(new BasicPanel(width, borderThickness));

		// ITEM HIGHLIGHTER
		this.setBackground(colorBackground);
		if (trade.stashtabName != null) {
			itemHighlighter = new ItemHighlighter(trade.stashType, trade.stashtabX, trade.stashtabY, colorBackground);

			this.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent e) {
					highlighterTimer.stop();
					itemHighlighter.updatePos(12);
					itemHighlighter.setVisible(true);
					itemHighlighter.repaint();
				}
			});

			this.addMouseListener(new MouseAdapter() {
				public void mouseExited(MouseEvent e) {
					highlighterTimer.restart();
				}
			});
		}
		
		this.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), ButtonType.SEARCH, trade));
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					hideStashHelper();
					FrameManager.stashHelperContainer.pack();
				}
			}
		});
	}

	private void hideStashHelper() {
		this.setVisible(false);
		if(itemHighlighter != null){
			itemHighlighter.setVisible(false);
		}
	}

}
