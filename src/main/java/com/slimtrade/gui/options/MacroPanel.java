package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class MacroPanel extends ContentPanel {

	// private final int bufferSize = 10;
	private static final long serialVersionUID = 1L;

	private CustomMacroCell waitCell;
	private CustomMacroCell thankCell;

	private InbuiltMacroCell inviteCell;
	private InbuiltMacroCell warpCell;
	private InbuiltMacroCell tradeCell;
	private InbuiltMacroCell kickLeaveCell;
	private InbuiltMacroCell homeCell;
	private InbuiltMacroCell closeCell;
	
	//TODO : Move inbuilt to own class
	public MacroPanel(int width, int height) {
		super(width, height);
		GridBagConstraints gc = new GridBagConstraints();
		int bufferSize = 5;

		JPanel customMacroPanel = new JPanel();
		JLabel customMacroLabel = new JLabel("Custom Macros");
		customMacroPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		customMacroPanel.add(customMacroLabel);

		JPanel inbuiltMacroPanel = new JPanel();
		JLabel inbuiltMacroLabel = new JLabel("Inbuilt Macros");
		inbuiltMacroPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		inbuiltMacroPanel.add(inbuiltMacroLabel);

		// CUSTOM MACROS
		waitCell = new CustomMacroCell("Wait Button", "/resources/icons/clock1.png", "waitButton", this);
		thankCell = new CustomMacroCell("Thank Button", "/resources/icons/thumb1.png", "thankButton", this);

		// INBUILT MACROS
		inviteCell = new InbuiltMacroCell("Invite Button", "/resources/icons/invite.png", this);
		inviteCell.addText("Invites a player to your party");
		
		warpCell = new InbuiltMacroCell("Warp Button", "/resources/icons/warp.png", this);
		warpCell.addText("Warps you to another player's hideout");
		
		tradeCell = new InbuiltMacroCell("Trade Button", "/resources/icons/cart.png", this);
		tradeCell.addText("Trades with a player");
		
		kickLeaveCell = new InbuiltMacroCell("Kick/Leave Button", "/resources/icons/leave.png", this);
		kickLeaveCell.addText("Incoming Trade : Kicks a player from your party");
		kickLeaveCell.addText("Outgoing Trade : Leaves the current party");
		
		homeCell = new InbuiltMacroCell("Home Button", "/resources/icons/home2.png", this);
		homeCell.addText("Warps you to your hideout");
		
		closeCell = new InbuiltMacroCell("Close Button", "/resources/icons/close.png", this);
		closeCell.addText("Closes the respective trade message");
		closeCell.addText("Right clicking close on an outgoing trade message will close ALL OTHER outgoing trade messages");

		gc.gridx = 0;
		gc.gridy = 0;

		this.addBufferY(bufferSize, gc);
		this.add(customMacroPanel, gc);
		this.addBufferY(bufferSize, gc);

		// CUSTOM MACROS
		this.add(waitCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(closeCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(thankCell, gc);
		this.addBufferY(bufferSize, gc);

		this.add(inbuiltMacroPanel, gc);
		this.addBufferY(bufferSize, gc);

		// INBUILT MACROS
		this.add(inviteCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(warpCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(tradeCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(kickLeaveCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(homeCell, gc);
		this.addBufferY(bufferSize, gc);
		this.add(closeCell, gc);
		this.addBufferY(bufferSize, gc);
	}

	public void saveAll() {
		waitCell.saveSettings();
		thankCell.saveSettings();
	}

	public void resetAll() {
		waitCell.applySavedSettings();
		thankCell.applySavedSettings();
	}

}
