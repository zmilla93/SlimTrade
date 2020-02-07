package com.slimtrade.gui.options.macros;

import java.awt.*;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.buttons.IconButton;

public class PresetMacroRow extends JPanel implements IColorable{

	// TODO : Image
	private static final long serialVersionUID = 1L;
	protected final int BUFFER_WIDTH = 10;
	private final int COLUMN_SIZE = 35;
	
//	private JPanel titlePanel = new JPanel();
//	private JPanel mousePanel = new JPanel();
	
//	private JLabel titleLabel = new JLabel("NAME");
	
//	private JLabel tempLabel = new JLabel("TEMP");
	private CustomTextField textLMB = new CustomTextField();
	private CustomTextField textRMB = new CustomTextField();
	private IconButton exampleButton;
	GridBagConstraints gc = new GridBagConstraints();
	
	private final int rowHeight = 20;
	private final int nameWidth = 60;
	private final int mouseWidth = 70;
	
//	private Dimension size;

	PresetMacroRow(Image img, boolean... thin) {
		this.setLayout(new GridBagLayout());
//		titlePanel.setLayout(new GridBagLayout());
//		mousePanel.setLayout(new GridBagLayout());
//		titlePanel.setOpaque(false);
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
//		titleLabel.setText(name);
//		titlePanel.add(titleLabel);
//		titlePanel.setPreferredSize(new Dimension(nameWidth, rowHeight));
		
		gc.gridx++;
//		this.add(titlePanel, gc);
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
        App.eventManager.addListener(this);
        this.updateColor();
	}

	// TODO Make row look like editable
	public JTextField getRow(String name, String text, boolean... editable) {
		gc.gridx = 2;
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		namePanel.setOpaque(false);
		JLabel nameLabel = new JLabel(name);
		namePanel.add(nameLabel);
		this.add(namePanel, gc);
//        gc.insets.right = 1;
		gc.gridx = 3;
		JTextField textField = new CustomTextField(COLUMN_SIZE);
		if(editable.length==0 || editable[0]==false){
			textField.setEditable(false);
			textField.setOpaque(false);
			textField.setFocusable(false);
			textField.setBorder(null);
		}
		textField.setText(text);
        gc.insets.right = 1;
		this.add(textField, gc);
        gc.insets.right = 0;
		gc.gridy++;
		return textField;
	}

    @Override
    public void updateColor() {
	    this.setBackground(ColorManager.BACKGROUND);
	    this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
