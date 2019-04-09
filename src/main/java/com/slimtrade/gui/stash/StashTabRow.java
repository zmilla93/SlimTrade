package main.java.com.slimtrade.gui.stash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.enums.StashTabType;
import main.java.com.slimtrade.gui.buttons.IconButton;
import main.java.com.slimtrade.gui.components.AddRemovePanel;
import main.java.com.slimtrade.gui.components.RemovablePanel;

public class StashTabRow extends RemovablePanel {

	private static final long serialVersionUID = 1L;

	public static final int ROW_HEIGHT = 25;

	private JButton removeButton;
	
	JTextField stashTabText;
	JComboBox<StashTabType> typeCombo;
	JComboBox<Color> colorCombo;
	private boolean delete;
	private boolean newRow = true;
	
	public StashTabRow(AddRemovePanel parent){
		super(parent);
		this.setLayout(new GridBagLayout());
//		this.setBackground(Color.YELLOW);
		this.setPreferredSize(new Dimension(400, ROW_HEIGHT));
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		removeButton = new IconButton("/resources/icons/close.png", ROW_HEIGHT);
		this.setRemoveButton(removeButton);
		
		int stashTextWidth = 250;
		JPanel stashTabTextPanel = new JPanel(new GridBagLayout());
		stashTabTextPanel.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
		stashTabTextPanel.setBackground(Color.green);
		stashTabText = new LimitTextField(31);
		stashTabText.setHorizontalAlignment(JTextField.CENTER);
		stashTabText.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
		stashTabTextPanel.add(stashTabText, new GridBagConstraints());
		
		typeCombo = new JComboBox<StashTabType>();
		for(StashTabType type : StashTabType.values()){
			typeCombo.addItem(type);
		}
		typeCombo.setFocusable(false);
		Dimension typeSize = typeCombo.getPreferredSize();
		typeSize.height = ROW_HEIGHT;
		typeCombo.setPreferredSize(typeSize);
		
		
		colorCombo = new JComboBox<Color>();
		colorCombo.setFocusable(false);
		colorCombo.setPreferredSize(new Dimension(50, ROW_HEIGHT));
		colorCombo.setRenderer(new StashTabCellRenderer());
		for(StashTabColor c : StashTabColor.values()){
			colorCombo.addItem(c.getBackground());
		}
				
		this.add(removeButton, gc);
		gc.gridx++;
		this.add(stashTabTextPanel, gc);
		gc.gridx++;
		this.add(typeCombo, gc);
		gc.gridx++;
		this.add(colorCombo, gc);
	}
	
//	public JButton getDeleteButton(){
//		return this.deleteButton;
//	}
	
	public String getText(){
		return stashTabText.getText();
	}
	
	public void setText(String text){
		stashTabText.setText(text);
	}

	public StashTabType getType(){
		return (StashTabType) typeCombo.getSelectedItem();
	}
	
	public void setType(StashTabType type){
		typeCombo.setSelectedItem(type);
	}
	
	public StashTabColor getColor(){
		return StashTabColor.getValueFromColor((Color)colorCombo.getSelectedItem());
	}
	
	public void setColor(StashTabColor color){
		colorCombo.setSelectedItem(color.getBackground());
	}
	
}
