package main.java.com.slimtrade.gui.messaging;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.audio.AudioManager;
import main.java.com.slimtrade.core.audio.SoundComponent;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.core.observing.ButtonType;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionEvent;
import main.java.com.slimtrade.core.observing.poe.PoeInteractionListener;
import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class AbstractMessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// TODO Load from drive
	// TODO : Move?
	private final PoeInteractionListener poeInteractionListener = Main.eventManager;
	private MessageType messageType;
	// Heights
	// protected int minHeight;
	// protected int maxHeight;
	protected int messageWidth;
	protected int messageHeight;
	protected int borderSize;
	protected int rowHeight;
	protected int totalWidth;
	protected int totalHeight;
	protected final double timerWeight = 0.1;

	protected static GridBagLayout gb = new GridBagLayout();
	protected GridBagConstraints gc = new GridBagConstraints();

	// Panels
	protected JPanel borderPanel = new JPanel();
	protected JPanel container = new JPanel();
	protected JPanel timerPanel = new JPanel();
	protected JLabel timerLabel = new JLabel("0s");
	protected IconButton closeButton;

	// TODO : Change to generic offer
	protected TradeOffer trade;

	protected Font font;
	private int second = 0;
	private int minute = 1;
	// TODO minute timer
	private Timer secondTimer=new Timer(1000,new ActionListener(){public void actionPerformed(ActionEvent arg0){second++;timerLabel.setText(second+"s");}});

	public AbstractMessagePanel() {
		this.setLayout(gb);
		borderPanel.setLayout(gb);
		container.setLayout(gb);
		gc.gridx = 0;
		gc.gridy = 0;
	}

	public void resizeMessage(int i) {
		System.out.println("Abstract Resize");
	}

	public void setCloseButton(int size, boolean... forceNew) {
		if (forceNew.length > 0 && forceNew[0]) {
			this.closeButton = new IconButton("/resources/icons/close.png", size);
		} else {
			this.closeButton = new IconButton(ImagePreloader.close, size);
		}
		closeButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 || messageType == MessageType.INCOMING_TRADE) {
					stopTimer();
				}
			}
		});
	}

	protected void registerPoeInteractionButton(JButton button, ButtonType type, String playerName, String whisper) {
		if (type == ButtonType.WHISPER) {
			button.addMouseListener(new AdvancedMouseAdapter() {
				public void click(MouseEvent e) {
					poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), type, trade.playerName, whisper));
				}
			});
		}
	}

	protected void registerPoeInteractionButton(JButton button, ButtonType type) {

		button.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				poeInteractionListener.poeInteractionPerformed(new PoeInteractionEvent(e.getButton(), type, trade));
			}
		});
	}
	// public void setCloseButton(int size, boolean forceNew) {
	//
	// }

	public JButton getCloseButton() {
		return this.closeButton;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	//TODO : Reconsider where to put audio
	public void setMessageType(MessageType messageType) {
		switch(messageType){
		case CHAT_SCANNER:
			break;
		case INCOMING_TRADE:
			AudioManager.play(SoundComponent.INCOMING_MESSAGE);
			break;
		case OUTGOING_TRADE:
			break;
		case UNKNOWN:
			break;
		default:
			break;
		
		}
		this.messageType = messageType;
	}

	// TODO : More fonts
	// TODO : Properly center font
	public void refreshFont(int size) {
		// Font f = this.getFont();
		int[] fontSizes = { 12, 16, 18, 20 };
		int i = size;
		if (i % 2 != 0) {
			i--;
		}
		// System.out.println("FONT SIZE : " + i);
		font = new Font("Serif", Font.PLAIN, i);
	}

	protected void resizeFrames() {
		System.out.println("abstract frames");

	}

	protected void resizeButtons() {

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
}
