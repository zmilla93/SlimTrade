package main.java.com.slimtrade.gui.options;

import java.awt.GridBagConstraints;

public class MacroPanel extends ContentPanel {

//	private final int bufferSize = 10;

	public MacroPanel(int width, int  height) {
		super(width, height);
		int bufferSize = 5;
		
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		this.addBufferY(bufferSize, gc);
		this.add(new MacroCell("Wait Button", "/resources/icons/clock1.png", "", this), gc);
		this.addBufferY(bufferSize, gc);
		// TODO : Refresh Button
		this.add(new MacroCell("Close Button", "/resources/icons/close.png", "", this), gc);
		this.addBufferY(bufferSize, gc);
		this.add(new MacroCell("Invite Button", "/resources/icons/invite.png", "", this), gc);
		this.addBufferY(bufferSize, gc);
		this.add(new MacroCell("Trade Button", "/resources/icons/cart.png", "", this), gc);
		this.addBufferY(bufferSize, gc);
		this.add(new MacroCell("Thank Button", "/resources/icons/thumb1.png", "", this), gc);
		this.addBufferY(bufferSize, gc);
		
		this.autoResize();
	}

}
