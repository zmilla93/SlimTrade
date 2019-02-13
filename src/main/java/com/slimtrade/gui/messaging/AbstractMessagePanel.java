package main.java.com.slimtrade.gui.messaging;

import java.awt.Font;
import java.awt.Graphics;
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
	protected final double timerWeight = 0.1;
	
	protected static GridBagLayout gb = new GridBagLayout();
	protected GridBagConstraints gc = new GridBagConstraints();
	
	// Panels
	protected JPanel borderPanel = new JPanel();
	protected JPanel container = new JPanel();
	protected JPanel timerPanel = new JPanel();
	protected IconButton closeButton;
	
	protected Font font;

	public AbstractMessagePanel(int height) {
		this.setLayout(gb);
		borderPanel.setLayout(gb);
		container.setLayout(gb);
		gc.gridx = 0;
		gc.gridy = 0;
	}
	
	public void setCloseButton(int size){
		this.closeButton = new IconButton(ImagePreloader.close, size);
	}
	
	public void setCloseButton(int size, boolean forceNew){
		this.closeButton = new IconButton("/resources/icons/close.png", size);
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
	
	//TODO : More fonts
	public void refreshFont(int size){
//		Font f = this.getFont();
		int i = size;
		if(i%2!=0){
			i--;
		}
//		System.out.println("FONT SIZE : " + i);
		font = new Font("Serif", Font.PLAIN, i);
	}


}
