
package main.java.com.slimtrade.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.observing.ButtonType;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.datatypes.MessageType;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.buttons.BasicIconButton_REMOVE;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class MessagePanelReworked extends JPanel {

	private static final long serialVersionUID = 1L;
	private final PoeInteractionListener poeInteractionListener = Main.eventManager;

	private final static int height = 40;
	private final static int width = height * 10;
	private final static int borderThickness = 2;
	public static final int totalWidth = width + borderThickness * 4;
	public static final int totalHeight = height + borderThickness * 4;
	private final int rowHeight = height / 2;

	public final TradeOffer trade;

	private JPanel borderOuter = new JPanel();
	private JPanel borderInner = new JPanel();
	private JPanel container = new JPanel();
	private JPanel topPanel = new JPanel();
	private JPanel namePanel = new JPanel();
	private JLabel nameLabel = new JLabel();
	private JPanel pricePanel = new JPanel();
	private JLabel priceCountLabel = new JLabel();
	private JLabel priceTypeLabel = new JLabel();
	private JPanel topButtonPanel = new JPanel();
	private JPanel bottomButtonPanel = new JPanel();
	private JPanel bottomPanel = new JPanel();
	private JPanel itemPanel = new JPanel();
	private JLabel itemCountLabel = new JLabel();
	private JLabel itemLabel = new JLabel();
	private JPanel timerPanel = new JPanel();
	private JLabel timerLabel = new JLabel();
	public StashHelper stashHelper;

	// Top Row Buttons
	private IconButton callbackButton = new IconButton("/resources/icons/phone.png", rowHeight);
	private IconButton waitButton = new IconButton("/resources/icons/clock1.png", rowHeight);
	private IconButton refreshButton = new IconButton("/resources/icons/refresh1.png", rowHeight);
//	private IconButton stillInterestedButton = new IconButton("/resources/icons/refresh1.png", rowHeight);
//	private IconButton repeatMessageButton = new IconButton("/resources/icons/refresh1.png", rowHeight);
	private IconButton closeButton = new IconButton("/resources/icons/close.png", rowHeight);
	// Bottom Row Buttons
	private IconButton inviteToPartyButton = new IconButton("/resources/icons/invite.png", rowHeight);
	private IconButton tpToHideoutButton = new IconButton("/resources/icons/warp.png", rowHeight);
	private IconButton tradeButton = new IconButton("/resources/icons/cart2.png", rowHeight);
	private IconButton thankButton = new IconButton("/resources/icons/thumb1.png", rowHeight);
	private IconButton kickButton = new IconButton("/resources/icons/leave.png", rowHeight);
	private IconButton leavePartyButton = new IconButton("/resources/icons/leave.png", rowHeight);
	private IconButton tpHomeButton = new IconButton("/resources/icons/home2.png", rowHeight);

	// Timers
	private int seconds = 0;
	private int minutes = 1;
	private ActionListener secondsTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			seconds++;
			if (seconds == 60) {
				secondsTimer.stop();
				minutesTimer.start();
				timerLabel.setText("1m");
			} else {
				timerLabel.setText(seconds + "s");
			}
		}
	};
	private ActionListener minutesTimerAction = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			minutes++;
			timerLabel.setText(minutes + "m");
		}
	};
	private Timer secondsTimer = new Timer(1000, secondsTimerAction);
	private Timer minutesTimer = new Timer(60000, minutesTimerAction);

	private Color color;

	// TODO : Switch layouts to BoderLayout + setHorizontalAlignment =
	// SwingConstants.CENTER
	// Alternatively use gridbaglayout
	// TODO : Add close function for all cleanup actions
	public MessagePanelReworked(TradeOffer trade) {
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints gcCenter = new GridBagConstraints();

		this.setLayout(gridbag);
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));

		this.trade = trade;

		double nameWidthMult = 0;
		double priceWidthMult = 0;
		double timerWidthMult = 0;
		double itemWidthMult = 0;
		double buttonTopMult = 0;
		double buttonBotMult = 0;

		// Currently buttons are 0.05 mult
		switch (trade.msgType) {
		case INCOMING_TRADE:
			nameWidthMult = 0.5;
			priceWidthMult = 0.3;
			timerWidthMult = 0.1;
			itemWidthMult = 0.7;
			buttonTopMult = 0.2;
			buttonBotMult = 0.2;
			break;
		case OUTGOING_TRADE:
			nameWidthMult = 0.55;
			priceWidthMult = 0.35;
			timerWidthMult = 0.1;
			itemWidthMult = 0.65;
			buttonTopMult = 0.1;
			buttonBotMult = 0.25;
			break;
		default:
			break;
		}

		Random rand = new Random();
		int r = rand.nextInt(150) + 50;
		int g = rand.nextInt(150) + 50;
		int b = rand.nextInt(150) + 50;
		color = new Color(r, g, b);

		// TODO : Could use this instead of borderOuter, as they are same size
		// MESSAGE WINDOW BORDER
		borderOuter.setLayout(gridbag);
		borderOuter.setPreferredSize(new Dimension(totalWidth, totalHeight));

		borderInner.setLayout(gridbag);
		borderInner.setPreferredSize(new Dimension(width + borderThickness * 2, height + borderThickness * 2));
		borderOuter.add(borderInner, gcCenter);

		container.setLayout(new BorderLayout());
		container.setPreferredSize(new Dimension(width, height));
		borderInner.add(container, gcCenter);
		this.add(borderOuter, gcCenter);

		BasicIconButton_REMOVE.width = height / 2;
		BasicIconButton_REMOVE.height = height / 2;

		// TOP PANEL
		LayoutManager flowCenter = new FlowLayout(FlowLayout.CENTER, 0, 0);
		topPanel.setLayout(flowCenter);
		topPanel.setPreferredSize(new Dimension(width, rowHeight));
		container.add(topPanel, BorderLayout.PAGE_START);

		// NAME
		namePanel.setLayout(gridbag);
		nameLabel = new JLabel(trade.playerName);
		namePanel.setPreferredSize(new Dimension((int) (width * nameWidthMult), rowHeight));
		namePanel.add(nameLabel, gcCenter);
		topPanel.add(namePanel);
		// PRICE
		pricePanel.setLayout(gridbag);
		priceCountLabel = new JLabel(trade.priceCount.toString().replaceAll("[.]0", "") + " ");
		pricePanel.add(priceCountLabel, gcCenter);
		BasicIcon_REMOVE.width = rowHeight;
		BasicIcon_REMOVE.height = rowHeight;
		IconPanel priceIcon;
		if (this.getClass().getResource("/resources/currency/" + trade.priceTypeString + ".png") != null) {
			priceIcon = new IconPanel("/resources/currency/" + trade.priceTypeString + ".png", rowHeight);
			pricePanel.add(priceIcon);
		} else {
			priceTypeLabel = new JLabel(" " + trade.priceTypeString);
		}
		pricePanel.setPreferredSize(new Dimension((int) (width * priceWidthMult), rowHeight));
		pricePanel.add(priceTypeLabel, gcCenter);
		topPanel.add(pricePanel);
		// TOP BUTTON PANEL
		topButtonPanel.setLayout(flowCenter);
		;
		topButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		topButtonPanel.setPreferredSize(new Dimension((int) (width * buttonTopMult), rowHeight));
		topPanel.add(topButtonPanel);

		// BOTTOM PANEL
		bottomPanel.setLayout(flowCenter);
		bottomPanel.setPreferredSize(new Dimension(width, rowHeight));
		container.add(bottomPanel, BorderLayout.CENTER);

		// TIMER
		timerLabel.setText("0s");
		timerPanel.setPreferredSize(new Dimension((int) (width * timerWidthMult), rowHeight));
		timerPanel.setLayout(gridbag);
		timerPanel.add(timerLabel, gcCenter);
		bottomPanel.add(timerPanel);

		// ITEM PANEL
		itemPanel.setLayout(gridbag);
		GridBagConstraints gcItem = new GridBagConstraints();
		gcItem.gridx = 0;
		if (trade.itemCount > 0) {
			itemCountLabel = new JLabel(trade.itemCount.toString().replaceAll("[.]0", "") + " ");
			itemPanel.add(itemCountLabel, gcItem);
		}
		gcItem.gridx = 1;
		IconPanel itemIcon;
		if (this.getClass().getResource("/resources/items/" + trade.itemName + ".png") != null) {
			itemIcon = new IconPanel("/resources/items/" + trade.itemName + ".png", rowHeight);
			itemPanel.add(itemIcon, gcItem);
		} else {
			itemLabel = new JLabel(trade.itemName);
			itemPanel.add(itemLabel, gcItem);
		}

		itemPanel.setPreferredSize(new Dimension((int) (width * itemWidthMult), rowHeight));
		bottomPanel.add(itemPanel);

		bottomButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		bottomButtonPanel.setPreferredSize(new Dimension((int) (width * buttonBotMult), rowHeight));
		bottomPanel.add(bottomButtonPanel);

		registerPoeInteractionButton(callbackButton, ButtonType.CALLBACK);
		registerPoeInteractionButton(tpHomeButton, ButtonType.HIDEOUT);
		registerPoeInteractionButton(inviteToPartyButton, ButtonType.INVITE);
		registerPoeInteractionButton(kickButton, ButtonType.KICK);
		registerPoeInteractionButton(leavePartyButton, ButtonType.LEAVE);
		registerPoeInteractionButton(refreshButton, ButtonType.REFRESH);
		registerPoeInteractionButton(thankButton, ButtonType.THANK);
		registerPoeInteractionButton(tradeButton, ButtonType.TRADE);
		registerPoeInteractionButton(waitButton, ButtonType.WAIT);
		registerPoeInteractionButton(tpToHideoutButton, ButtonType.WARP);
		
		switch (trade.msgType) {
		case INCOMING_TRADE:
			// TOP BUTTONS
			topButtonPanel.add(callbackButton);
			topButtonPanel.add(waitButton);
			topButtonPanel.add(refreshButton);
			topButtonPanel.add(closeButton);
			// BUTTOM BUTTONS
			bottomButtonPanel.add(inviteToPartyButton);
			bottomButtonPanel.add(tradeButton);
			bottomButtonPanel.add(thankButton);
			bottomButtonPanel.add(kickButton);

			// STASH HELPER
			stashHelper = new StashHelper(trade, color);
			stashHelper.setVisible(false);
			FrameManager.stashHelperContainer.getContentPane().add(stashHelper);

			inviteToPartyButton.addMouseListener(new AdvancedMouseAdapter() {
				public void click(MouseEvent e) {
					stashHelper.setVisible(true);
				}
			});

			break;
		case OUTGOING_TRADE:
			// TOP BUTTONS
			topButtonPanel.add(refreshButton);
			topButtonPanel.add(closeButton);
			// BOTTOM BUTTONS
			bottomButtonPanel.add(tpToHideoutButton);
			bottomButtonPanel.add(tradeButton);
			bottomButtonPanel.add(thankButton);
			bottomButtonPanel.add(leavePartyButton);
			bottomButtonPanel.add(tpHomeButton);
			break;
		case CHAT_SCANNER:
			break;
		case UNKNOWN:
			break;
		}

		secondsTimer.start();
		updateColor();
	}

	public void updateColor() {
		// Panels
		this.borderOuter.setBackground(ColorManager.MsgWindow.borderOuter);
		this.borderInner.setBackground(ColorManager.MsgWindow.borderInner);
		this.namePanel.setBackground(ColorManager.MsgWindow.nameBG);
		this.nameLabel.setForeground(ColorManager.MsgWindow.text);
		this.priceCountLabel.setForeground(ColorManager.MsgWindow.text);
		this.priceTypeLabel.setForeground(ColorManager.MsgWindow.text);
		// TODO : Give timer panel it's own color?
		this.timerPanel.setBackground(ColorManager.MsgWindow.itemBG);
		this.timerLabel.setForeground(ColorManager.MsgWindow.text);
		this.itemPanel.setBackground(ColorManager.MsgWindow.itemBG);
		this.itemCountLabel.setForeground(ColorManager.MsgWindow.text);
		this.itemLabel.setForeground(ColorManager.MsgWindow.text);
		// Buttons
		// Row 2

		// Message Type Specific
		switch (trade.msgType) {
		case INCOMING_TRADE:
			pricePanel.setBackground(ColorManager.MsgWindow.priceBG_in);
			this.borderInner.setBackground(color);
			break;
		case OUTGOING_TRADE:
			pricePanel.setBackground(ColorManager.MsgWindow.priceBG_out);
			break;
		default:
			break;
		}

	}

	private void registerPoeInteractionButton(JButton button, ButtonType type) {
		button.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), type, trade));
			}
		});
	}

	public JButton getCloseButton() {
		return this.closeButton;
	}

	public MessageType getMessageType() {
		return this.trade.msgType;
	}

}