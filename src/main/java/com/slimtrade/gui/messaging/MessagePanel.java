package com.slimtrade.gui.messaging;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.SaveSystem.StashTab;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.ButtonType;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.enums.StashTabType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.enums.PreloadedImageCustom;
import com.slimtrade.gui.panels.PricePanel;
import com.slimtrade.gui.stash.helper.StashHelper;

public class MessagePanel extends AbstractMessagePanel implements IColorable {

	private static final long serialVersionUID = 1L;

	// private JPanel namePanel = new NameClickPanel();
	// private JPanel pricePanel = new JPanel(gb);
	// private PaintedPanel itemPanel = new PaintedPanel();
	private JPanel topPanel = new JPanel(gb);
	private JPanel bottomPanel = new JPanel(gb);

	protected JPanel buttonPanelTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	protected JPanel buttonPanelBottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));

	private StashHelper stashHelper;

	// private IconButton saveToHistoryButton;
	// private IconButton waitButton;
	private IconButton refreshButton;
	private IconButton inviteButton;
	private IconButton warpButton;
	private IconButton tradeButton;
	private IconButton thankButton;

	private IconButton homeButton;
	private IconButton replyButton;

	// private StashHelper stashHelper;
	private ArrayList<IconButton> customButtonsTop = new ArrayList<IconButton>();
	private ArrayList<IconButton> customButtonsBottom = new ArrayList<IconButton>();

	// TODO Listeners?
	public MessagePanel(TradeOffer trade, Dimension size) {
		super(trade);
		buildPanel(trade, size, true);
	}

	public MessagePanel(TradeOffer trade, Dimension size, boolean makeListeners) {
		super(trade);
		buildPanel(trade, size, makeListeners);
	}

	private void buildPanel(TradeOffer trade, Dimension size, boolean makeListeners) {
		// TODO : move size stuff to super
		this.trade = trade;
		this.setMessageType(trade.messageType);

		if (trade.guildName != null && App.saveManager.saveFile.showGuildName) {
			nameLabel.setText(trade.guildName + " " + trade.playerName);
		} else {
			nameLabel.setText(trade.playerName);
		}

		switch (messageType) {
		case CHAT_SCANNER:
			itemLabel.setText(trade.searchMessage);
			// TODO : Search name
			priceLabel.setText(trade.searchName);
			pricePanel.add(priceLabel);
			break;
		case INCOMING_TRADE:
		case OUTGOING_TRADE:
			// TODO : This is janky plz fix
			// itemLabel.setText(trade.itemName);
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

		this.startTimer();
		
		
		App.eventManager.addColorListener(this);
		this.updateColor();
		
		// this.revalidate();
		// this.repaint();

	}

	// TODO add button count
	// TODO : Finish this
	// public void resizeMessage(Dimension size, boolean listeners) {
	// calculateSizes(size);
	// resizeFrames(3, 5);
	// refreshButtons(this.getMessageType(), listeners);
	// this.revalidate();
	// this.repaint();
	// }

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
			// respodButton =
			buttonCountTop = 2;
			buttonCountBottom = 4;

			replyButton = new IconButton(PreloadedImage.REPLY.getImage(), rowHeight);
			buttonPanelTop.add(replyButton);

			inviteButton = new IconButton(PreloadedImage.INVITE.getImage(), rowHeight);
			tradeButton = new IconButton(PreloadedImage.CART.getImage(), rowHeight);
			thankButton = new IconButton(PreloadedImage.THUMB.getImage(), rowHeight);
			kickButton = new IconButton(PreloadedImage.LEAVE.getImage(), rowHeight);

			if (listeners) {
				this.registerPoeInteractionButton(replyButton, ButtonType.WHISPER, trade.playerName, trade.searchResponseLeft, trade.searchResponseRight);
				this.registerPoeInteractionButton(inviteButton, ButtonType.INVITE);
				this.registerPoeInteractionButton(tradeButton, ButtonType.TRADE);
				this.registerPoeInteractionButton(thankButton, ButtonType.THANK);
				this.registerPoeInteractionButton(kickButton, ButtonType.KICK);
			}

			buttonPanelBottom.add(inviteButton);
			buttonPanelBottom.add(tradeButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);
			break;
		case INCOMING_TRADE:
			buttonCountTop = 2;
			buttonCountBottom = 4;
			for(MacroButton macro : App.saveManager.saveFile.incomingMacroButtons) {
                PreloadedImageCustom img = macro.image;
                IconButton button = new IconButton(img.getImage(), rowHeight);
                if (macro.row == ButtonRow.TOP) {
                    buttonCountTop++;
                    String lmb = macro.leftMouseResponse;
                    String rmb = macro.rightMouseResponse;
                    if (listeners) {
                        this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
                    }
                    customButtonsTop.add(button);
                } else if (macro.row == ButtonRow.BOTTOM) {
                    buttonCountBottom++;
                    String lmb = macro.leftMouseResponse;
                    String rmb = macro.rightMouseResponse;
                    if (listeners) {
                        this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
                    }
                    customButtonsBottom.add(button);
                }
            }
			refreshButton = new IconButton(PreloadedImage.REFRESH.getImage(), rowHeight);
			inviteButton = new IconButton(PreloadedImage.INVITE.getImage(), rowHeight);
			tradeButton = new IconButton(PreloadedImage.CART.getImage(), rowHeight);
			thankButton = new IconButton(PreloadedImage.THUMB.getImage(), rowHeight);
			kickButton = new IconButton(PreloadedImage.LEAVE.getImage(), rowHeight);
			if (listeners) {
				this.registerPoeInteractionButton(refreshButton, ButtonType.REFRESH);
				this.registerPoeInteractionButton(inviteButton, ButtonType.INVITE);
				this.registerPoeInteractionButton(tradeButton, ButtonType.TRADE);
				this.registerPoeInteractionButton(thankButton, ButtonType.THANK);
				this.registerPoeInteractionButton(kickButton, ButtonType.KICK);
				itemPanel.addMouseListener(new AdvancedMouseAdapter() {
					public void click(MouseEvent e) {
						if(e.getButton() == MouseEvent.BUTTON1){
							stashHelper.setVisible(true);
							FrameManager.stashHelperContainer.pack();
						}else if (e.getButton() == MouseEvent.BUTTON3){
							FrameManager.ignoreItemWindow.setItem(trade.itemName);
							FrameManager.ignoreItemWindow.pack();
							FrameManager.centerFrame(FrameManager.ignoreItemWindow);
							FrameManager.ignoreItemWindow.setVisible(true);
						}

					}
				});
				inviteButton.addMouseListener(new AdvancedMouseAdapter() {
					public void click(MouseEvent e) {
						stashHelper.setVisible(true);
						FrameManager.stashHelperContainer.pack();
					}
				});
			}

			for (IconButton b : customButtonsTop) {
				buttonPanelTop.add(b);
			}
			for (IconButton b : customButtonsBottom) {
				buttonPanelBottom.add(b);
			}
			// buttonPanelTop.add(saveToHistoryButton);
			buttonPanelTop.add(refreshButton);

			buttonPanelBottom.add(inviteButton);
			buttonPanelBottom.add(tradeButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(kickButton);

			break;
		case OUTGOING_TRADE:
			buttonCountTop = 2;
			buttonCountBottom = 4;
            for(MacroButton macro : App.saveManager.saveFile.outgoingMacroButtons) {
                PreloadedImageCustom img = macro.image;
                IconButton button = new IconButton(img.getImage(), rowHeight);
                if (macro.row == ButtonRow.TOP) {
                    buttonCountTop++;
                    String lmb = macro.leftMouseResponse;
                    String rmb = macro.rightMouseResponse;
                    if (listeners) {
                        this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
                    }
                    customButtonsTop.add(button);
                } else if (macro.row == ButtonRow.BOTTOM) {
                    buttonCountBottom++;
                    String lmb = macro.leftMouseResponse;
                    String rmb = macro.rightMouseResponse;
                    if (listeners) {
                        this.registerPoeInteractionButton(button, ButtonType.WHISPER, trade.playerName, lmb, rmb);
                    }
                    customButtonsBottom.add(button);
                }
            }
			refreshButton = new IconButton(PreloadedImage.REFRESH.getImage(), rowHeight);
			warpButton = new IconButton(PreloadedImage.WARP.getImage(), rowHeight);
			thankButton = new IconButton(PreloadedImage.THUMB.getImage(), rowHeight);
			leaveButton = new IconButton(PreloadedImage.LEAVE.getImage(), rowHeight);
			homeButton = new IconButton(PreloadedImage.HOME.getImage(), rowHeight);

            for (IconButton b : customButtonsTop) {
                buttonPanelTop.add(b);
            }
            for (IconButton b : customButtonsBottom) {
                buttonPanelBottom.add(b);
            }

			buttonPanelTop.add(refreshButton);
			buttonPanelBottom.add(warpButton);
			buttonPanelBottom.add(thankButton);
			buttonPanelBottom.add(leaveButton);
			buttonPanelBottom.add(homeButton);
			if (listeners) {
				this.registerPoeInteractionButton(refreshButton, ButtonType.REFRESH);
				this.registerPoeInteractionButton(warpButton, ButtonType.WARP);
				this.registerPoeInteractionButton(thankButton, ButtonType.THANK);
				this.registerPoeInteractionButton(leaveButton, ButtonType.LEAVE);
				this.registerPoeInteractionButton(homeButton, ButtonType.HIDEOUT);
			}
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		if (listeners) {
			this.registerPoeInteractionButton(namePanel, ButtonType.NAME_PANEL);
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

	// public JButton getCloseKickButton(){
	// if(this.messageType == MessageType.INCOMING_TRADE){
	// return this.kickButton;
	// }else if(this.messageType == MessageType.OUTGOING_TRADE){
	// return this.leaveButton;
	// }else return null;
	// }

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

	@Override
	public void updateColor() {
		Color color = null;
		Color colorText = null;
        nameLabel.setForeground(ColorManager.TEXT);
        itemLabel.setForeground(ColorManager.TEXT);
		namePanel.setBackground(ColorManager.LOW_CONSTRAST_1);
        itemPanel.setBackground(ColorManager.HIGH_CONTRAST_2);
        itemPanel.backgroudDefault = ColorManager.HIGH_CONTRAST_2;
		//MUTUAL COLORS
		this.setBackground(ColorManager.PRIMARY);
		switch (trade.messageType) {
		case CHAT_SCANNER:
			itemPanel.setToolTipText(trade.searchMessage);
//			itemPanel.setToolTipText(trade.searchMessage);
			borderPanel.setBackground(ColorManager.SCANNER_BACKGROUND);
			pricePanel.setBackground(ColorManager.SCANNER_BACKGROUND);
//            itemPanel.backgroudDefault = ColorManager.HIGH_CONTRAST_2;
            itemLabel.setForeground(ColorManager.TEXT);
//			priceLabel.setForeground(ColorManager.POE_TEXT_LIGHT);
			break;
		case INCOMING_TRADE:
			Random rand = new Random();
//			color = new Color(rand.nextInt(150) + 50, rand.nextInt(150) + 50, rand.nextInt(150) + 50);
//            colorText = ColorManager.POE_TEXT_DARK;
			color = StashTabColor.TWENTYSIX.getBackground();
            colorText = StashTabColor.TWENTYSIX.getForeground();
			boolean stashFound = false;
			if (trade.stashtabName != null && !trade.stashtabName.equals("")) {
				int i = 0;
				for(StashTab tab : App.saveManager.saveFile.stashTabs) {
                    if (tab.name.equals(trade.stashtabName)) {
                        App.logger.log(Level.INFO, "STASH FOUND ::: " + trade.stashtabName);
                        stashFound = true;
                        StashTabColor stashColor = tab.color;
                        StashTabType type = tab.type;
                        trade.stashType = type;
                        if (stashColor != StashTabColor.ZERO) {
                            color = stashColor.getBackground();
                            colorText = stashColor.getForeground();
                        }
                        break;
                    }
                }
			}
			itemPanel.backgroudDefault = color;
			itemLabel.setForeground(colorText);
			stashHelper = new StashHelper(trade, color, colorText);
			stashHelper.setVisible(false);
			FrameManager.stashHelperContainer.add(stashHelper);
			borderPanel.setBackground(ColorManager.GREEN_SALE);
			pricePanel.setBackground(ColorManager.GREEN_SALE);
			priceLabel.setForeground(ColorManager.POE_TEXT_LIGHT);
			break;
		case OUTGOING_TRADE:
//			itemPanel.backgroudDefault = Color.GRAY;
			borderPanel.setBackground(ColorManager.RED_SALE);
			pricePanel.setBackground(ColorManager.RED_SALE);
			priceLabel.setForeground(ColorManager.POE_TEXT_LIGHT);
			break;
		case UNKNOWN:
			break;
		default:
			break;
		}
		itemPanel.refresh();
	}

}
