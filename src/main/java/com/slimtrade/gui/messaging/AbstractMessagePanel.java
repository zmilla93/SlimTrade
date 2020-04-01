package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.ButtonType;
import com.slimtrade.core.observing.poe.PoeInteractionEvent;
import com.slimtrade.core.observing.poe.PoeInteractionListener;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.basic.PaintedPanel;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.PricePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class AbstractMessagePanel extends ColorPanel {

	private static final long serialVersionUID = 1L;
	// TODO Load from drive
	// TODO : Move?
	// TODO : switch totalwidth/windowHeight to use get/set?
	private final PoeInteractionListener poeInteractionListener = App.macroEventManager;
	protected MessageType messageType;
	// Heights
	// protected int minHeight;
	// protected int maxHeight;
	protected int messageWidth;
	protected int messageHeight;
	protected int borderSize = 2;
	protected int rowHeight;
	public static int totalWidth;
	public static int totalHeight;
	protected final double timerWeight = 0.1;

	protected static GridBagLayout gb = new GridBagLayout();
	protected GridBagConstraints gc = new GridBagConstraints();

	// Panels
	protected PaintedPanel namePanel = new PaintedPanel();
	protected PricePanel pricePanel = new PricePanel();
	protected PaintedPanel itemPanel = new PaintedPanel();

	protected JPanel borderPanel = new ColorPanel();
	protected JPanel container = new ColorPanel();
	protected PaintedPanel timerPanel = new PaintedPanel();
	protected JLabel timerLabel;
	protected IconButton closeButton;

	// Buttons
//	protected IconButton kickButton;
//	protected IconButton leaveButton;

	// Labels
//	protected JLabel nameLabel = new JLabel();
//	protected JLabel priceLabel = new JLabel();
//	protected JLabel itemLabel = new JLabel();

	protected int buttonCountTop;
	protected int buttonCountBottom;
	// TODO : Change to generic offer
	protected TradeOffer trade;

	protected Font font;
	private int second = 0;
	private int minute = 1;
	private Timer secondTimer = new Timer(1000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			second++;
			if(second>59){
				secondTimer.stop();
				minuteTimer.start();
				timerLabel.setText("1m");
			}else{
				timerLabel.setText(second + "s");
			}
		}
	});
	private Timer minuteTimer = new Timer(60000, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			minute++;
			timerLabel.setText(minute + "m");
		}
	});

	public AbstractMessagePanel(TradeOffer trade) {
		timerPanel.setLabel(new CustomLabel("0s"));
		timerLabel = timerPanel.getLabel();
		this.trade = trade;
		this.setLayout(gb);
		borderPanel.setLayout(gb);
		container.setLayout(gb);
		gc.gridx = 0;
		gc.gridy = 0;
//		App.eventManager.addColorListener(this);
	}

	public void resizeMessage(int i) {
		System.out.println("Abstract Resize");
	}

	public void setCloseButton(int size, boolean... forceNew) {
		if (forceNew.length > 0 && forceNew[0]) {
			this.closeButton = new IconButton(DefaultIcons.CLOSE, size);
		} else {
			this.closeButton = new IconButton(DefaultIcons.CLOSE, size);
		}
		closeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 || messageType == MessageType.INCOMING_TRADE) {
					stopTimer();
				}
			}
		});
	}

	protected void registerPoeInteractionButton(JButton button, ButtonType type, String playerName, String clickLeft, String clickRight) {
		if (type == ButtonType.WHISPER) {
			button.addMouseListener(new AdvancedMouseAdapter() {
				public void click(MouseEvent e) {
					poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), type, trade.playerName, clickLeft, clickRight));
				}
			});
		}
	}

	protected void registerPoeInteractionButton(Component c, ButtonType type) {
		c.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), type, trade));
			}
		});
	}

	public JButton getCloseButton() {
		return this.closeButton;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	protected void resizeFrames() {

	}

	public void startTimer() {
		secondTimer.start();
	}

	public void stopTimer() {
		secondTimer.stop();
	}

	public void restartTimer() {
		secondTimer.restart();
	}

	@Override
	public void updateColor() {
		super.updateColor();
		timerPanel.setBackgroundColor(ColorManager.BACKGROUND);
		timerPanel.setBorderColor(ColorManager.BACKGROUND);
	}
}
