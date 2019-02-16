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

public class StashTabRow extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int ROW_HEIGHT = 25;

	private JButton deleteButton;
	
	JTextField stashTabText;
	JComboBox<String> typeCombo;
	JComboBox<Color> colorCombo;
	private boolean delete;
	private boolean newRow = true;
	
	public StashTabRow(){
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.YELLOW);
		this.setPreferredSize(new Dimension(400, ROW_HEIGHT));
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		deleteButton = new IconButton("/resources/icons/close.png", ROW_HEIGHT);
		
		int stashTextWidth = 250;
		JPanel stashTabTextPanel = new JPanel(new GridBagLayout());
		stashTabTextPanel.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
		stashTabTextPanel.setBackground(Color.green);
		stashTabText = new LimitTextField(31);
		stashTabText.setHorizontalAlignment(JTextField.CENTER);
		stashTabText.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT));
		stashTabTextPanel.add(stashTabText, new GridBagConstraints());
		
		typeCombo = new JComboBox<String>();
		typeCombo.setFocusable(false);
		typeCombo.addItem("Normal");
		typeCombo.addItem("Quad");
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
				
		this.add(deleteButton, gc);
		gc.gridx++;
		this.add(stashTabTextPanel, gc);
		gc.gridx++;
		this.add(typeCombo, gc);
		gc.gridx++;
		this.add(colorCombo, gc);
	}

	public boolean isNewRow() {
		return newRow;
	}
	
	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}
	
	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	
	public JButton getDeleteButton(){
		return this.deleteButton;
	}
	
	public String getStashTabName(){
		return stashTabText.getText();
	}

	//TODO : Stashtab type
	public StashTabType getStashTabType(){
		return StashTabType.valueOf((String)typeCombo.getSelectedItem());
	}
	
	public Color getStashTabColor(){
		return (Color)colorCombo.getSelectedItem();
	}
	
}
