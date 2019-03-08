package main.java.com.slimtrade.gui.options.macros;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class PresetMacroRow extends JPanel {

	// TODO : Image
	private static final long serialVersionUID = 1L;
	protected final int BUFFER_WIDTH = 10;
	private final int COLUMN_SIZE = 35;
	
	private JPanel titlePanel = new JPanel();
//	private JPanel mousePanel = new JPanel();
	
	private JLabel titleLabel = new JLabel("NAME");
	
//	private JLabel tempLabel = new JLabel("TEMP");
	private JTextField textLMB = new JTextField(COLUMN_SIZE);
	private JTextField textRMB = new JTextField(COLUMN_SIZE);
	private IconButton exampleButton;
	GridBagConstraints gc = new GridBagConstraints();
	
	private final int rowHeight = 20;
	private final int nameWidth = 60;
	private final int mouseWidth = 70;
	
//	private Dimension size;

	PresetMacroRow(String name, String img, boolean... thin) {
		this.setLayout(new GridBagLayout());
		titlePanel.setLayout(new GridBagLayout());
//		mousePanel.setLayout(new GridBagLayout());
		this.setBackground(Color.GRAY);
		titlePanel.setOpaque(false);
//		titlePanel.setOpaque(false);
		
		if (thin.length > 0 && thin[0]) {
			this.setPreferredSize(new Dimension(500, 20));
		}else{
			this.setPreferredSize(new Dimension(500, 40));
		}
//		size = textLMB.getPreferredSize();
		gc.weightx = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		titleLabel.setText(name);
		titlePanel.add(titleLabel);
		titlePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
		
		gc.gridx++;
		this.add(titlePanel, gc);
		gc.gridx += 2;// Label
		gc.gridx += 2;// Action

		if (thin.length > 0 && thin[0]) {
			gc.gridx = 2;
			gc.gridy = 0;
		} else {
			gc.gridx = 1;
			gc.gridy = 1;
			
		}
		gc.gridx = 0;
		gc.gridy = 0;
		exampleButton = new IconButton(img, 20);
		this.add(exampleButton, gc);
		gc.gridy = 0;
		this.setPreferredSize(null);
		this.revalidate();
	}
	
	private JPanel getSimplePanel(){
		return null;
	}

	// TODO Make row look like editable
	public JTextField getRow(String name, String text, boolean... editable) {
		gc.gridx = 2;
		JPanel namePanel = new JPanel();
		namePanel.setBackground(Color.yellow);
		namePanel.setLayout(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		namePanel.setOpaque(false);
		JLabel nameLabel = new JLabel(name);
		namePanel.add(nameLabel);
		this.add(namePanel, gc);
		gc.gridx = 3;
		JTextField textLabel = new JTextField(COLUMN_SIZE);
		if(editable.length==0 || editable[0]==false){
			textLabel.setEditable(false);
			textLabel.setOpaque(false);
			textLabel.setFocusable(false);
			//TODO : Border
//			textLabel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
//			textLabel.setDragEnabled(false);
		}
		textLabel.setText(text);
		this.add(textLabel, gc);
		gc.gridy++;
		return textLabel;
	}

	public void addEditLMB(String title, String preset) {
		gc.gridx = 3;
		JPanel titlePanel = new JPanel();
//		titlePanel.setBackground(Color.yellow);
		titlePanel.setLayout(new GridBagLayout());
		JLabel titleLabel = new JLabel(title);
		titlePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		titlePanel.add(titleLabel);
		this.add(titlePanel, gc);
		gc.gridx = 5;
//		textLMB.setPreferredSize(size);
		textLMB.setText(preset);
		this.add(textLMB, gc);
		gc.gridy++;
	}

	public void addEditRMB(String title, String preset) {
		gc.gridx = 3;
		JPanel titlePanel = new JPanel();
//		titlePanel.setBackground(Color.yellow);
		titlePanel.setLayout(new GridBagLayout());
		JLabel titleLabel = new JLabel(title);
		titlePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		titlePanel.add(titleLabel);
		this.add(titlePanel, gc);
		gc.gridx = 5;
		// JTextField text = new JTextField(50);
//		textRMB.setPreferredSize(size);
		textRMB.setText(preset);
		// textRMB.setEditable(false);
		this.add(textRMB, gc);
		gc.gridy++;
	}

	public void addEditRow(String title, String preset) {
		gc.gridx = 3;
		JPanel titlePanel = new JPanel();
//		titlePanel.setBackground(Color.yellow);
		titlePanel.setLayout(new GridBagLayout());
		JLabel titleLabel = new JLabel(title);
		titlePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		titlePanel.add(titleLabel);
		this.add(titlePanel, gc);
		gc.gridx = 5;
		JTextField text = new JTextField(COLUMN_SIZE);
//		text.setPreferredSize(size);
		
		text.setText(preset);
		this.add(text, gc);
		gc.gridy++;
	}
}
