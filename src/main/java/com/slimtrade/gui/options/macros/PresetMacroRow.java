package com.slimtrade.gui.options.macros;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomTextField;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.ICacheImage;

import javax.swing.*;
import java.awt.*;

public class PresetMacroRow extends JPanel implements IColorable{

	// TODO : Image
	private static final long serialVersionUID = 1L;
	protected final int BUFFER_WIDTH = 10;
	private final int COLUMN_SIZE = 35;
	
//	private JPanel titlePanel = new JPanel();
//	private JPanel mousePanel = new JPanel();
	
//	private JLabel titleLabel = new JLabel("NAME");
	
//	private JLabel tempLabel = new JLabel("TEMP");
//	private CustomTextField textLMB = new CustomTextField();
//	private CustomTextField textRMB = new CustomTextField();
	private IconButton exampleButton;
	GridBagConstraints gc = new GridBagConstraints();
	
	private final int rowHeight = 20;
	private final int nameWidth = 60;
	private final int mouseWidth = 70;

	boolean hasImage = true;
	private String title;
	
//	private Dimension size;
	public PresetMacroRow(String title) {
		this(title, false);
	}

	public PresetMacroRow(String title, boolean thin) {
		this(title, null, thin);
	}

	public PresetMacroRow(ICacheImage img) {
		this(null, img, false);
	}

	public PresetMacroRow(ICacheImage img, boolean thin) {
		this(null, img, thin);
	}

	public PresetMacroRow(String title, ICacheImage img, boolean thin) {
		this.title = title;
		this.setLayout(new GridBagLayout());
		if (thin) {
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

		if (thin) {
			gc.gridx = 2;
			gc.gridy = 0;
		} else {
			gc.gridx = 1;
			gc.gridy = 1;
			
		}
		gc.gridx = 0;
		gc.gridy = 0;
		if(img == null) {
			JLabel label = new CustomLabel(title);
			this.add(label, gc);
		} else {
			exampleButton = new IconButton(img, 20);
			this.add(exampleButton, gc);
		}

		gc.gridy = 0;
		this.setPreferredSize(null);
		this.revalidate();
	}


	public JTextField getRow(String name, String text, boolean... editable) {
		gc.gridx = 2;
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridBagLayout());
		namePanel.setPreferredSize(new Dimension(mouseWidth, rowHeight));
		namePanel.setOpaque(false);
		JLabel nameLabel = new CustomLabel(name);
		namePanel.add(nameLabel);
		this.add(namePanel, gc);
//        gc.insets.right = 1;
		gc.gridx = 3;
//		gc.fill = GridBagConstraints.HORIZONTAL;
		CustomTextField textField;
		if(title == null) {
			textField = new CustomTextField(30);
		}else {
			gc.insets.left = 5;
			textField = new CustomTextField(20);
		}
		if(editable.length==0 || editable[0]==false){
			textField.setEditable(false);
			textField.setOpaque(false);
			textField.setFocusable(false);
			textField.setBorder(null);
			textField.enableBorder = false;
		}
		textField.setText(text);
        gc.insets.right = 1;
		this.add(textField, gc);
		gc.insets.left = 0;
        gc.insets.right = 0;
		gc.gridy++;
		return textField;
	}

    @Override
    public void updateColor() {
	    this.setBackground(ColorManager.BACKGROUND);
	    this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2));
    }
}
