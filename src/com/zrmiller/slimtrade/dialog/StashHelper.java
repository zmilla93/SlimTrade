package com.zrmiller.slimtrade.dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.PoeInterface;
import com.zrmiller.slimtrade.panels.BasicPanel;
import com.zrmiller.slimtrade.windows.StashGridOverlay;

public class StashHelper extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 150;
	public static int height = 40;
	
	private int x;
	private int y;
	private int borderThickness = 2;
	private String stashtab;
	private String itemName;
	
	private static double cellWidth;
	private static double cellHeight;
	
	public JPanel itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	private Timer highlighterTimer = new Timer(2500, hideHighlighter);
	
	public StashHelper(int x, int y, String stashtab, String itemName, String priceType, double priceCount){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, borderThickness));
		this.setPreferredSize(new Dimension(width, height));
//		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, borderThickness, true));
		BasicPanel stashPanel = new BasicPanel(width, height/3, Overlay.flowCenter);
		String fixedStashtabName = stashtab == null ? "(~price " + Double.toString(priceCount).replaceAll("[.]0", "") + " " + priceType + ")" : stashtab;
		JLabel stashLabel = new JLabel(fixedStashtabName);
		stashLabel.setForeground(Color.WHITE);
		stashPanel.add(stashLabel);
		BasicPanel itemPanel = new BasicPanel(width, height/3, Overlay.flowCenter);
		JLabel itemLabel = new JLabel(itemName);
		itemLabel.setForeground(Color.WHITE);
		itemPanel.add(itemLabel);
		this.add(stashPanel);
		this.add(itemPanel);
		this.add(new BasicPanel(width, borderThickness));
		
		//ITEM HIGHLIGHTER
		this.x = x;
		this.y = y;
		this.stashtab = stashtab;
		this.itemName = itemName;
		Random rand = new Random();
		int r = rand.nextInt(255)+1;
		int g = rand.nextInt(255)+1;
		int b = rand.nextInt(255)+1;
//		double cellWidth = (double)StashGridOverlay.getGridSize().width/12;
//		double cellHeight = (double)StashGridOverlay.getGridSize().height/12;
		this.setBackground(new Color(r,g,b));
		itemHighlighter = new JPanel();
		itemHighlighter.setVisible(false);
		itemHighlighter.setBackground(new Color(1F, 1F, 1F, 0F));
		itemHighlighter.setBorder(BorderFactory.createLineBorder(new Color(r, g, b), 4, false));
		itemHighlighter.setBounds(0-(int)cellWidth*2, 0, (int)cellWidth, (int)cellHeight);
		updateItemHighlighterPos();
		itemHighlighter.setPreferredSize(new Dimension((int)cellWidth, (int)cellHeight));
//		itemHighlighter.setVisible(true);
//		FrameManager.messageContainer.add(itemHighlighter);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
		    	updateItemHighlighterPos();
		    	highlighterTimer.stop();
		    	itemHighlighter.setVisible(true);
		    	itemHighlighter.repaint();
		    }
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseExited(java.awt.event.MouseEvent e) {
				highlighterTimer.restart();
			}
		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1){
					PoeInterface.findInStash(itemName.replaceAll("(?i)superior( )?", "").replaceAll("( )?\\(.+\\)", ""));
				}else if(e.getButton() == MouseEvent.BUTTON3){
					hideStashHelper();
				}
				
			}
		});
	}
	
	//TODO : Avoid using statics here?
	public static void updateCellSize(double cellWidth, double cellHeight){
		StashHelper.cellWidth = cellWidth;
		StashHelper.cellHeight = cellHeight;
	}
	
	private void hideStashHelper(){
		this.setVisible(false);
		itemHighlighter.setVisible(false);
	}
	
	public void updateItemHighlighterPos(){
		if(x==0 && y==0){
			return;
		}
//		double cellWidth = (double)StashGridOverlay.getGridSize().width/12;
//		double cellHeight = (double)StashGridOverlay.getGridSize().height/12;
		itemHighlighter.setLocation((int)(StashGridOverlay.getGridPos().x+((x-1)*cellWidth)), (int)(StashGridOverlay.getGridPos().y+((y-1)*cellHeight)));
	}
	
}
