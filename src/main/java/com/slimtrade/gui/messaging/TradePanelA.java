package main.java.com.slimtrade.gui.messaging;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.observing.ButtonType;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.options.macros.datatypes.ButtonRow;
import main.java.com.slimtrade.gui.options.macros.datatypes.PreloadedImageCustom;
import main.java.com.slimtrade.gui.panels.PricePanel;
import main.java.com.slimtrade.gui.stash.helper.StashHelper;

public class TradePanelA extends AbstractMessagePanel {

	private static final long serialVersionUID = 1L;

//	private JPanel namePanel = new NameClickPanel();
//	private JPanel pricePanel = new JPanel(gb);
//	private PaintedPanel itemPanel = new PaintedPanel();
	private JPanel topPanel = new JPanel(gb);
	private JPanel bottomPanel = new JPanel(gb);

	

	protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

	private StashHelper stashHelper;

	private IconButton callbackButton;
	private IconButton waitButton;
	private IconButton refreshButton;
	private IconButton inviteButton;
	private IconButton warpButton;
	private IconButton tradeButton;
	private IconButton thankButton;
	private IconButton leaveButton;
	private IconButton kickButton;
	private IconButton homeButton;
	
	private IconButton replyButton;

	// private StashHelper stashHelper;

	private ArrayList<IconButton> customButtons = new ArrayList<IconButton>();
	private ArrayList<IconButton> customButtonsTop = new ArrayList<IconButton>();
	private ArrayList<IconButton> customButtonsBottom = new ArrayList<IconButton>();

	// TODO Listeners?
	public TradePanelA(TradeOffer trade, Dimension size) {
		buildPanel(trade, size, true);
	}

	public TradePanelA(TradeOffer trade, Dimension size, boolean makeListeners) {
		buildPanel(trade, size, makeListeners);
	}

	private void buildPanel(TradeOffer trade, Dimension size, boolean makeListeners) {
		// TODO : move size stuff to super
		this.trade = trade;
		this.setMessageType(trade.messageType);
		
		nameLabel.setText(trade.playerName);
		
		
		switch(messageType){
		case CHAT_SCANNER:
			itemLabel.setText(trade.searchMessage);
			//TODO : Search name
			priceLabel.setText("TODO");
			pricePanel.add(priceLabel);
			break;
		case INCOMING_TRADE:
		case OUTGOING_TRADE:
			//TODO : This is janky plz fix
//			itemLabel.setText(trade.itemName);
			itemLabel = new JLabel(TradeUtility.getFixedItemName(trade.itemName, trade.itemCount, true));
			PricePanel p = new PricePanel(trade.priceTypeString, trade.priceCount, true);
			priceLabel = p.getLabel();
			pricePanel.add(p);
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		
		calculateSizes(size);
		refreshButtons(this.getMessageType(), makeListeners);
		resizeFrames();

		namePanel.setLayout(new BorderLayout());
		namePanel.add(nameLabel, BorderLayout.CENTER);
		nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nameLabel.setVerticalAlignment(SwingConstants.CENTER);


		pricePanel.setLayout(new GridBagLayout());
		

		timerPanel.setLayout(new BorderLayout());
		timerPanel.add(timerLabel, BorderLayout.CENTER);
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

		itemPanel.setLayout(new GridBagLayout());
		
		itemPanel.add(itemLabel);
		itemLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Color
		container.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		borderPanel.setBackground(Color.CYAN);

		container.add(topPanel, gc);
		gc.gridy = 1;
		container.add(bottomPanel, gc);
		gc.gridy = 0;
		borderPanel.add(container, gc);
		this.add(borderPanel, gc);
		// TOP PANEL
		topPanel.add(namePanel, gc);
		gc.gridx++;
		topPanel.add(pricePanel, gc);
		gc.gridx++;
		topPanel.add(buttonPanelTop, gc);

		// BOTTOM PANEL
		gc.gridx = 0;
		gc.gridy = 0;
		bottomPanel.add(timerPanel, gc);
		gc.gridx++;
		bottomPanel.add(itemPanel, gc);
		gc.gridx++;
		bottomPanel.add(buttonPanelBottom, gc);
		Color color = null;
		Color colorText = null;

		switch (trade.messageType) {
		case CHAT_SCANNER:
			ToolTipManager.sharedInstance().setInitialDelay(0);
			itemPanel.setToolTipText(trade.searchMessage);
			break;
		case INCOMING_TRADE:
//			Random rand = new Random();
//			color = new Color(rand.nextInt(150) + 50, rand.nextInt(150) + 50, rand.nextInt(150) + 50);
			color = StashTabColor.ONE.getBackground();
			colorText = StashTabColor.ONE.getForeground();
			if (trade.stashtabName != null && !trade.stashtabName.equals("")) {
				int i = 0;
				while (Main.saveManager.hasEntry("stashTabs", "tab" + i)) {
					if (Main.saveManager.getString("stashTabs", "tab" + i, "text").equals(trade.stashtabName)) {
						Main.logger.log(Level.INFO, "STASH FOUND ::: " + trade.stashtabName);
						StashTabColor stashColor = StashTabColor.valueOf(Main.saveManager.getString("stashTabs", "tab" + i, "color"));
						color = stashColor.getBackground();
						colorText = stashColor.getForeground();
						break;
					}
					i++;
				}
			}
			borderPanel.setBackground(color);
			itemPanel.backgroundDefault = color;
			itemLabel.setForeground(colorText);
			stashHelper = new StashHelper(trade, color, colorText);
			stashHelper.setVisible(false);
			FrameManager.stashHelperContainer.add(stashHelper);
			itemPanel.addMouseListener(new AdvancedMouseAdapter() {
				public void click(MouseEvent e) {
					stashHelper.setVisible(true);
				}
			});
			pricePanel.setBackground(ColorManager.greenIncoming);
			priceLabel.setForeground(ColorManager.stashLightText);
			break;
		case OUTGOING_TRADE:
			borderPanel.setBackground(StashTabColor.ONE.getBackground());
			pricePanel.setBackground(ColorManager.redOutgoing);
			priceLabel.setForeground(ColorManager.stashLightText);
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}

		this.startTimer();
		this.revalidate();
		this.repaint();

	}

	// TODO add button count
	//TODO : Finish this
//	public void resizeMessage(Dimension size, boolean listeners) {
//		calculateSizes(size);
//		resizeFrames(3, 5);
//		refreshButtons(this.getMessageType(), listeners);
//		this.revalidate();
//		this.repaint();
//	}

	private void calculateSizes(Dimension size) {
		if (size.width % 2 != 0) {
			size.width++;
		}
		if (size.height % 2 != 0) {
			size.height++;
		}
		messageWidth = size.width;
		messageHeight = size.height;
		rowHeight = messageHeight / 2;
		totalWidth = messageWidth + (borderSize * 4);
		totalHeight = messageHeight + (borderSize * 4);
	}

	// TODO : get max
	protected void refreshButtons(MessageType type, boolean listeners) {
		for (Component c : buttonPanelTop.getComponents()) {
			buttonPanelTop.remove(c);
			c = null;
		}
		for (Component c : buttonPanelBottom.getComponents()) {
			buttonPanelBottom.remove(c);
			c = null;
		}
		
		
		switch (type) {
		case CHAT_SCANNER:
//			respodButton =
			buttonCountTop = 2;
			replyButton = new IconButton(ImagePreloader.warp, rowHeight);
			buttonPanelTop.add(replyButton);
			break;
		case INCOMING_TRADE:
			buttonCountTop = 4;
			buttonCountBottom = 4;
			int i = 0;
			while(Main.saveManager.hasEntry("macros", "in", "custom", "button"+i)){
				PreloadedImageCustom img = PreloadedImageCustom.valueOf(Main.saveManager.getString("macros", "in", "custom", "button"+i, "image"));
				IconButton button = new IconButton(img.getImage(), rowHeight);
				if(Main.saveManager.getString("macros", "in", "custom", "button"+i, "row").equals(ButtonRow.TOP.name())){
					buttonCountTop++;
					String lmb = Main.saveManager.getString("macros", "in", "custom", "button"+i, "left");
					String rmb = Main.saveManager.getString("macros", "in", "custom", "button"+i, "right");
					this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
					customButtonsTop.add(button);
				}else if(Main.saveManager.getString("macros", "in", "custom", "button"+i, "row").equals(ButtonRow.BOTTOM.name())){
					buttonCountBottom++;
					String lmb = Main.saveManager.getString("macros", "in", "custom", "button"+i, "left");
					String rmb = Main.saveManager.getString("macros", "in", "custom", "button"+i, "right");
					this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
					customButtonsBottom.add(button);
				}
				i++;
			}

			callbackButton = new IconButton(ImagePreloader.callback, rowHeight);
			waitButton = new IconButton(ImagePreloader.wait, rowHeight);
			refreshButton = new IconButton(ImagePreloader.refresh, rowHeight);
			inviteButton = new IconButton(ImagePreloader.invite, rowHeight);
			tradeButton = new IconButton(ImagePreloader.trade, rowHeight);
			thankButton = new IconButton(ImagePreloader.thank, rowHeight);
			kickButton = new IconButton(ImagePreloader.leave, rowHeight);

			if (listeners) {
				this.registerPoeInteractionButton(tradeButton, ButtonType.WHISPER, trade.playerName, "trade1", "trade2");
				this.registerPoeInteractionButton(callbackButton, ButtonType.CALLBACK);
			}

//			i = 0;
			for (IconButton b : customButtonsTop) {
				buttonPanelTop.add(b);
			}
			for (IconButton b : customButtonsBottom) {
				buttonPanelBottom.add(b);
			}
			buttonPanelTop.add(callbackButton);
			buttonPanelTop.add(waitButton);
			buttonPanelTop.add(refreshButton);

			buttonPanelBottom.add(inviteButton);
			buttonPanelBottom.add(tradeButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);

			break;
		case OUTGOING_TRADE:
			buttonCountTop = 2;
			buttonCountBottom = 4;
			refreshButton = new IconButton(ImagePreloader.refresh, rowHeight);
			warpButton = new IconButton(ImagePreloader.warp, rowHeight);
			thankButton = new IconButton(ImagePreloader.thank, rowHeight);
			kickButton = new IconButton(ImagePreloader.leave, rowHeight);
			homeButton = new IconButton(ImagePreloader.home, rowHeight);

			buttonPanelTop.add(refreshButton);

			buttonPanelBottom.add(warpButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);
			buttonPanelBottom.add(homeButton);
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}

		// TODO : update force
		this.setCloseButton(rowHeight);
		buttonPanelTop.add(closeButton);
	}

	protected void resizeFrames() {
		this.setPreferredSize(new Dimension(totalWidth, totalHeight));
		borderPanel.setPreferredSize(new Dimension(messageWidth + borderSize * 2, messageHeight + borderSize * 2));
		container.setPreferredSize(new Dimension(messageWidth, messageHeight));
		Dimension s = new Dimension(messageWidth, rowHeight);
		topPanel.setPreferredSize(s);
		bottomPanel.setPreferredSize(s);
//		this.buttonCountTop = buttonsTop;
//		this.buttonCountBottom = buttonsBottom;
		Dimension buttonSizeTop = new Dimension(rowHeight * buttonCountTop, rowHeight);
		Dimension buttonSizeBottom = new Dimension(rowHeight * buttonCountBottom, rowHeight);
		buttonPanelTop.setPreferredSize(buttonSizeTop);
		buttonPanelTop.setMinimumSize(buttonSizeTop);
		buttonPanelBottom.setPreferredSize(buttonSizeBottom);
		buttonPanelBottom.setMaximumSize(buttonSizeBottom);
		int nameWidth = (int) ((messageWidth - buttonSizeTop.width) * 0.7);
		int priceWidth = messageWidth - nameWidth - buttonSizeTop.width;
		int timerWidth = (int) (messageWidth * timerWeight);
		int itemWidth = messageWidth - timerWidth - buttonSizeBottom.width;

		namePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
		pricePanel.setPreferredSize(new Dimension(priceWidth, rowHeight));
		timerPanel.setPreferredSize(new Dimension(timerWidth, rowHeight));
		itemPanel.setPreferredSize(new Dimension(itemWidth, rowHeight));
	}

	public JButton getCloseButton() {
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
