package com.zrmiller.slimtrade;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;

@SuppressWarnings("serial")
public class TradeWindowOLD extends JFrame{
	//Panels
	JPanel panelContainer = new JPanel();
	JPanel panelBorder = new JPanel();
	JPanel panelBorder2 = new JPanel();
	JPanel panelPlayerName = new JPanel();
	JPanel panelItem = new JPanel();
	JPanel panelPrice = new JPanel();
	//Labels
	JLabel labelPlayerName = new JLabel();
	JLabel labelItem = new JLabel();
	JLabel labelPrice = new JLabel();
	//Buttons
	JButton buttonClose = new JButton();
	JButton buttonExpand = new JButton();
	JButton buttonInviteToParty = new JButton();
	JButton buttonInviteToTrade = new JButton();
	JButton buttonThank = new JButton();
	JButton buttonKick = new JButton();
	
	public TradeWindowOLD(TradeOffer tradeOffer) {
		this.tradeWindowMutual();
		//this.createMessageBox(tradeOffer.offerType, tradeOffer.playerName, tradeOffer.purchase, tradeOffer.purchaseQuant, tradeOffer.price, tradeOffer.priceQuant);
	}
	
	public void tradeWindowMutual(){
		//Remove from task bar
		//this.setType(javax.swing.JFrame.Type.UTILITY);
		/*
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		*/
		//int i = 0;
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
 		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
	}
	
	public void createMessageBox(String type, String player, String purchase, int purchaseQuantity, String price, int priceQuantity){
		//this.messageCount++;
		System.out.println("Adding trade window...");
		/*
		 * COLORS
		 */
		//Color textColor = null;
		//String textColorDefault = "";
		String textColorHex = "";
		Color borderColor = null;
		Color colorNameBg = null;
		Color colorPriceBg = null;
		Color colorItemBg = null;
		Color colorButtonDefault = null;
		Color colorButtonSuccess = new Color(0, 153, 0);
		Color colorButtonHover = Color.WHITE;
		if(type=="in"){
			textColorHex = "#d9d9d9";
			borderColor = new Color(179, 107, 0);
			colorNameBg = new Color(26, 26, 26);
			colorItemBg = new Color(51, 51, 51);
			//backgroundColor3 = new Color(0, 0, 0);
			colorButtonDefault = new Color(255, 179, 102);
			colorButtonHover = new Color(215, 139, 62);
		}else if(type=="out"){
			textColorHex = "#FFD700";
			borderColor = new Color(93, 64, 55);
			colorNameBg = new Color(140, 140, 140,50);
			colorItemBg = new Color(120, 120, 120);
			//backgroundColor3 = new Color(0, 0, 0);
			colorButtonDefault = new Color(102, 102, 102);
			colorButtonHover = new Color(82, 82, 82);
		}
		//colorButtonHover = Color.white;
		final Color colorButtonDefaultF = colorButtonDefault;
		final Color colorButtonSuccessF = colorButtonSuccess;
		final Color colorButtonHoverF = colorButtonHover;
		
		/*
		 *	STUFF
		 */
		
		
		//Tooltip
		//UIManager.put("ToolTip.background", new ColorUIResource(255, 247, 200)); //#fff7c8
		//UIManager.put("ToolTip.border", BorderFactory.createLineBorder(new Color(76,79,83)));
		/*
		//NAME - label
		playerLabel.setFont(new Font("Didot", Font.BOLD, 13));
		playerLabel.setBounds(offsetX, offsetY, playerWidth, msgHeight/2);
		playerLabel.setText("<html>&nbsp;&nbsp;<font style=\"color:" + textColorHex +";\">" + player + "</font></html>");
		//NAME - panel (bottom  border)
		playerPanel.setBounds(offsetX, offsetY, playerWidth, msgHeight/2);
		playerPanel.setBackground(colorNameBg);
		
		//PRICE - label (NO BORDER)
		priceLabel.setFont(new Font("Didot", Font.BOLD, 13));
		priceLabel.setText("<html>&nbsp;<font style=\"color:" + textColorHex +";\">" + priceQuantity + "</font></html>");
		priceLabel.setBounds(offsetX+playerWidth, offsetY, priceWidth, msgHeight/2);
		//PRICE - panel
		pricePanel.setBounds(offsetX+playerWidth, offsetY, priceWidth, msgHeight/2);
		pricePanel.setBackground(borderColor);
		
		//ITEM - label
		//JLabel itemLabel = new JLabel();
		itemLabel.setFont(new Font("Didot", Font.BOLD, 13));
		itemLabel.setText("<html>&nbsp;&nbsp;<font style=\"color:" + textColorHex +";\">" + purchase + "</font></html>");
		itemLabel.setBounds(offsetX, offsetY+msgHeight/2, itemWidth, msgHeight/2);
		//ITEM - panel (top border)
		itemPanel.setBounds(offsetX, offsetY+msgHeight/2, itemWidth, msgHeight/2);
		itemPanel.setBackground(colorItemBg);
		*/
		/*
		 * BUTTONS
		 */
		
		//Button Background-REMOVE>?
		/*
		JPanel buttonBackgroundPanel = new JPanel();
		int buttonbgWidth = buttonCountRow1;
		if (buttonCountRow2>buttonCountRow1){buttonbgWidth = buttonCountRow2;}
		buttonBackgroundPanel.setBounds(screenWidth-buttonbgWidth*buttonWidth-offsetX, offsetY, buttonbgWidth*buttonWidth, msgHeight);
		buttonBackgroundPanel.setBackground(borderColor);
		//buttonBackgroundPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
		//Images
		Image imgInvite = new ImageIcon(this.getClass().getResource("/invite.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgLeave = new ImageIcon(this.getClass().getResource("/leave.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgAlch = new ImageIcon(this.getClass().getResource("/alch.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgChaos = new ImageIcon(this.getClass().getResource("/chaos.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgClose = new ImageIcon(this.getClass().getResource("/no.png")).getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
		Image imgExpand = new ImageIcon(this.getClass().getResource("/refresh2.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgCart = new ImageIcon(this.getClass().getResource("/cart.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		Image imgHappy = new ImageIcon(this.getClass().getResource("/happy.png")).getImage().getScaledInstance(buttonWidth, buttonWidth, Image.SCALE_SMOOTH);
		
		//UPPER ROW OF BUTTONS, RIGHT TO LEFT
		//Close	Button
		//JButton closeButton = new JButton();
		closeButton.setBackground(colorButtonDefault);
		closeButton.setBounds(playerWidth+priceWidth+buttonWidth+offsetX, offsetY, buttonWidth, buttonHeight);
		closeButton.setIcon(new ImageIcon(imgClose));
		closeButton.setToolTipText("Close this trade");
		//closeButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		closeButton.setBorderPainted(false);
		//Expand Button
		expandButton.setBounds(playerWidth+priceWidth+offsetX, offsetY, buttonWidth, buttonHeight);
		expandButton.setIcon(new ImageIcon(imgExpand));
		expandButton.setBackground(colorButtonDefault);
		expandButton.setToolTipText("Expand");
		expandButton.setBorderPainted(false);
		//expandButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		
		
		//LOWER ROW OF BUTTONS, RIGHT TO LEFT
		//Kick Button
		//JButton kickButton = new JButton();
		kickButton.setBounds(itemWidth+offsetX+buttonWidth*3, offsetY+buttonHeight, buttonWidth, buttonHeight);
		kickButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		kickButton.setBackground(colorButtonDefault);
		kickButton.setIcon(new ImageIcon(imgLeave));
		kickButton.setBorderPainted(false);
		//Thank Button
		//JButton thankButton = new JButton();
		thankButton.setBounds(itemWidth+offsetX+buttonWidth*2, offsetY+buttonHeight, buttonWidth, buttonHeight);
		thankButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		thankButton.setBackground(colorButtonDefault);
		thankButton.setIcon(new ImageIcon(imgHappy));
		thankButton.setBorderPainted(false);
		//Trade Button
		//JButton tradeButton = new JButton();
		tradeButton.setBounds(itemWidth+offsetX+buttonWidth, offsetY+buttonHeight, buttonWidth, buttonHeight);
		tradeButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		tradeButton.setBackground(colorButtonDefault);
		tradeButton.setIcon(new ImageIcon(imgCart));
		tradeButton.setBorderPainted(false);
		//Invite Button
		//JButton inviteButton = new JButton();
		inviteButton.setBounds(itemWidth+offsetX, offsetY+buttonHeight, buttonWidth, buttonHeight);
		inviteButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		inviteButton.setBackground(colorButtonDefault);
		inviteButton.setIcon(new ImageIcon(imgInvite));
		inviteButton.setBorderPainted(false);
		*/
		/*
		 *  BUTTON HIGHLIGHTING + CLICK EVENTS

		closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt){closeButton.setBackground(colorButtonHoverF);}
		    public void mouseExited(java.awt.event.MouseEvent evt) {closeButton.setBackground(colorButtonDefaultF);}
		    public void mouseClicked(java.awt.event.MouseEvent evt) {close();}
		});
		expandButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt){expandButton.setBackground(colorButtonHoverF);}
		    public void mouseExited(java.awt.event.MouseEvent evt) {expandButton.setBackground(colorButtonDefaultF);}
		});
		kickButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt){kickButton.setBackground(colorButtonHoverF);}
		    public void mouseExited(java.awt.event.MouseEvent evt) {kickButton.setBackground(colorButtonDefaultF);}
		    public void mouseClicked(java.awt.event.MouseEvent evt) {pasteIntoPOE("/kick ShootyMcArrowShooter");}
		});
		tradeButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt){tradeButton.setBackground(colorButtonHoverF);}
		    public void mouseExited(java.awt.event.MouseEvent evt) {tradeButton.setBackground(colorButtonDefaultF);}
		});
		inviteButton.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt){inviteButton.setBackground(colorButtonHoverF);}
		    public void mouseExited(java.awt.event.MouseEvent evt) {inviteButton.setBackground(colorButtonDefaultF);}
		});
		
		/*
		 * ADD UI ELEMENTS, ADDED SOONER = HIGHER PRIORITY

		
		//Buttons
		this.add(closeButton);
		this.add(expandButton);
		this.add(kickButton);
		this.add(thankButton);
		this.add(tradeButton);
		this.add(inviteButton);
		//Labels
		this.add(playerLabel);
		this.add(priceLabel);
		this.add(itemLabel);
		//Background Panels
		this.add(playerPanel);
		this.add(pricePanel);
		this.add(itemPanel);
		this.add(borderPanel2);
		this.add(borderPanel);
		*/
	}
	
	private void pasteIntoPOE(String s){
		//Move object creatoin?
		StringSelection pasteString = new StringSelection(s);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(pasteString, null);
		this.focusPOE();
		/*
		robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        */
        //robot.keyPress(KeyEvent.VK_ENTER);
        //robot.keyRelease(KeyEvent.VK_ENTER);
	}

	 private void focusPOE() {
	        User32.INSTANCE.EnumWindows((hWnd, arg1) -> {
	            char[] className = new char[512];
	            User32.INSTANCE.GetClassName(hWnd, className, 512);
	            String wText = Native.toString(className);
	            if (wText.isEmpty()) {
	                return true;
	            }
	            if (wText.equals("POEWindowClass")) {
	                User32.INSTANCE.SetForegroundWindow(hWnd);
	                return false;
	            }
	            return true;
	        }, null);
	    }
	
	private void close(){
		System.out.println("CLOSED TRADE WINDOW");
		this.dispose();
	}
	
	
	
}
