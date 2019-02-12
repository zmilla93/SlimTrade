package main.java.com.slimtrade.gui.messaging;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class AbstractMessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	// TODO Load from drive
	
	private MessageType messageType;
	
	//Heights
	protected int minHeight;
	protected int maxHeight;
	protected int messageWidth;
	protected int messageHeight;
	protected int borderSize;
	protected int rowHeight;
	protected int totalWidth;
	protected int totalHeight;
	
	protected static GridBagLayout gb = new GridBagLayout();
	protected GridBagConstraints gc = new GridBagConstraints();
	
	// Panels
	protected JPanel borderPanel = new JPanel();
	protected JPanel container = new JPanel();

	protected JButton closeButton = new IconButton(ImagePreloader.close, rowHeight);

	public AbstractMessagePanel() {
		this.setLayout(gb);
		borderPanel.setLayout(gb);
		container.setLayout(gb);
		gc.gridx = 0;
		gc.gridy = 0;
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


}
