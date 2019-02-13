package main.java.com.slimtrade.gui.options.customizer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class PresetMacroRow extends JPanel {

	//TODO : Image
	private static final long serialVersionUID = 1L;
	protected final int BUFFER_WIDTH = 10;
	private JLabel nameLabel = new JLabel("NAME");
	private JLabel tempLabel = new JLabel("TEMP");
	private JTextField tempText = new JTextField(30);
	private IconButton exampleButton = new IconButton("/resources/icons/close.png", 20);
	GridBagConstraints gc = new GridBagConstraints();
	
	PresetMacroRow(String name, String img){
		this.setLayout(new GridBagLayout());
		gc.gridx = 0;
		gc.gridy = 0;
		
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx++;
		this.add(nameLabel, gc);
		gc.gridx++;
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx+=2;
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		gc.gridx+=2;
		this.add(new BufferPanel(BUFFER_WIDTH, 0), gc);
		this.add(exampleButton);
	}
	
	public void addRow(String title, String text){
		gc.gridx = 3;
		this.add(new JLabel(title), gc);
		gc.gridx = 5;
		this.add(new JLabel(text), gc);
		gc.gridy++;
	}
}
