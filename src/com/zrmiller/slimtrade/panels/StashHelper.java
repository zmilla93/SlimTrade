package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.PoeInterface;
import com.zrmiller.slimtrade.windows.StashWindow;

public class StashHelper extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	public static int width = 150;
	public static int height = 40;
	
	private int x;
	private int y;
	private int borderThickness = 2;
	private String stashtab;
	private String itemName;
	
	public JPanel itemHighlighter;
	private ActionListener hideHighlighter = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e){
			itemHighlighter.setVisible(false);
			highlighterTimer.stop();
		}
	};
	private Timer highlighterTimer = new Timer(2500, hideHighlighter);
	
	public StashHelper(int x, int y, String stashtab, String itemName){
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, borderThickness));
		this.setPreferredSize(new Dimension(width, height));
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, borderThickness, true));
		BasicPanel stashPanel = new BasicPanel(width, height/3, Overlay.flowCenter);
		JLabel stashLabel = new JLabel(stashtab);
		stashLabel.setForeground(Color.WHITE);
		System.out.println(stashtab);
		stashPanel.add(stashLabel);
		BasicPanel itemPanel = new BasicPanel(width, height/3, Overlay.flowCenter);
		JLabel itemLabel = new JLabel(itemName);
		itemLabel.setForeground(Color.WHITE);
//		itemLabel.setForeground(Color.WHITE);
		itemPanel.add(itemLabel);
		this.add(stashPanel);
		this.add(itemPanel);
		this.add(new BasicPanel(width, borderThickness));
//		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		
		//ITEM HIGHLIGHTER
		this.x = x;
		this.y = y;
		this.stashtab = stashtab;
		this.itemName = itemName;
		Random rand = new Random();
		int r = rand.nextInt(255)+1;
		int g = rand.nextInt(255)+1;
		int b = rand.nextInt(255)+1;
		double cellWidth = (double)StashWindow.getGridSize().width/12;
		double cellHeight = (double)StashWindow.getGridSize().height/12;
		this.setBackground(new Color(r,g,b));
		itemHighlighter = new JPanel();
		itemHighlighter.setVisible(false);
		itemHighlighter.setBackground(new Color(1F, 1F, 1F, 0F));
		itemHighlighter.setBorder(BorderFactory.createLineBorder(new Color(r, g, b), 4, false));
		itemHighlighter.setBounds((int)(StashWindow.getGridPos().x+((x-1)*cellWidth)), (int)(StashWindow.getGridPos().y+((y-1)*cellHeight)), (int)cellWidth, (int)cellHeight);
		itemHighlighter.setPreferredSize(new Dimension((int)cellWidth, (int)cellHeight));
		Overlay.messageContainer.add(itemHighlighter);
		
//		this.addMouseListener(new java.awt.event.MouseAdapter() {
//			public void mouseExited(java.awt.event.MouseEvent e) {
//				StringSelection pasteString = new StringSelection(s);
//				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//				clipboard.setContents(pasteString, null);
//				this.focusPoe();
//				robot.keyPress(KeyEvent.VK_ENTER);
//			    robot.keyRelease(KeyEvent.VK_ENTER);
//			    robot.keyPress(KeyEvent.VK_CONTROL);
//			    robot.keyPress(KeyEvent.VK_V);
//			    robot.keyRelease(KeyEvent.VK_V);
//			    robot.keyRelease(KeyEvent.VK_CONTROL);
//			    robot.keyPress(KeyEvent.VK_ENTER);
//			    robot.keyRelease(KeyEvent.VK_ENTER);
//			}
//		});
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent e) {
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
				PoeInterface.findInStash(itemName);
			}
		});
	}
	
}
