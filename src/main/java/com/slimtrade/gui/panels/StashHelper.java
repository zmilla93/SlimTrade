package main.java.com.slimtrade.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.java.com.slimtrade.core.managers.PoeInterface;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.gui.basic.BasicPanel;
import main.java.com.slimtrade.gui.frames.ItemHighlighter;

public class StashHelper extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 150;
	public static int height = 40;
	
//	private int x;
//	private int y;
	private int borderThickness = 2;
//	private String itemName;
	
	public ItemHighlighter itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	public Timer highlighterTimer = new Timer(2000, hideHighlighter);
	
	public StashHelper(TradeOffer trade, Color color){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, borderThickness));
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, borderThickness, true));
		BasicPanel stashPanel = new BasicPanel(width, height/3, new FlowLayout(FlowLayout.CENTER, 0, 0));
		String fixedStashtabName = trade.stashtabName == null ? "(~price " + Double.toString(trade.priceCount).replaceAll("[.]0", "") + " " + trade.priceTypeString + ")" : trade.stashtabName;
		JLabel stashLabel = new JLabel(fixedStashtabName);
		stashLabel.setForeground(Color.WHITE);
		stashPanel.add(stashLabel);
		
		BasicPanel itemPanel = new BasicPanel(width, height/3, new FlowLayout(FlowLayout.CENTER, 0, 0));
		JLabel itemLabel = new JLabel(trade.itemName);
		itemLabel.setForeground(Color.WHITE);
		itemPanel.add(itemLabel);
		this.add(stashPanel);
		this.add(itemPanel);
		this.add(new BasicPanel(width, borderThickness));
		
		//ITEM HIGHLIGHTER
//		this.x = trade.stashtabX;
//		this.y = trade.stashtabY;
//		this.stashtab = trade.stashtabName;
//		this.itemName = trade.itemName;
		this.setBackground(color);
		itemHighlighter = new ItemHighlighter(trade.stashtabX, trade.stashtabY, color);
		
		this.repaint();
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		    	highlighterTimer.stop();
		    	itemHighlighter.refresh();
		    	itemHighlighter.setVisible(true);
		    	itemHighlighter.repaint();
//		    	PoeInterface.focus();
		    }
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				highlighterTimer.restart();
			}
		});
		
//		this.addMouseListener(new java.awt.event.MouseAdapter() {
//			public void mousePressed(java.awt.event.MouseEvent e) {
//				PoeInterface.focus();
//			}
//		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					PoeInterface.findInStash(trade.itemName.replaceAll("(?i)superior( )?", "").replaceAll("( )?\\(.+\\)", ""));
				}else if(e.getButton() == MouseEvent.BUTTON3){
					hideStashHelper();
				}
				
			}
		});
	}
	
	private void hideStashHelper(){
		this.setVisible(false);
		itemHighlighter.setVisible(false);
	}
	
}
