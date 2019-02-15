package main.java.com.slimtrade.gui.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import main.java.com.slimtrade.gui.panels.BufferPanel;

public class AudioPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private final int BUFFER_HEIGHT = 10;
	
	public AudioPanel(){
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx=0;
		gc.gridy=0;
		
		AudioRow row1 = new AudioRow("Test");
		
		this.addRow(new AudioRow("Test"), gc);
		this.addRow(new BufferPanel(0, BUFFER_HEIGHT), gc);
		this.addRow(new AudioRow("Test"), gc);
		this.addRow(new BufferPanel(0, BUFFER_HEIGHT), gc);
		this.addRow(new AudioRow("Test"), gc);
		this.addRow(new BufferPanel(0, BUFFER_HEIGHT), gc);
		this.addRow(new AudioRow("Test"), gc);
		
		this.autoResize();
	}
	
}
