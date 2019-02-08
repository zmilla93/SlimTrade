package main.java.com.slimtrade.gui.stashtab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.gui.buttons.IconButton;

public class StashTabRow extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int ROW_HEIGHT = 25;
	
	private Dimension comboSize;
//	JButton deleteButton;
	
	public StashTabRow(){
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.YELLOW);
		this.setPreferredSize(new Dimension(400, ROW_HEIGHT));
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		JButton deleteButton = new IconButton("/resources/icons/close.png", ROW_HEIGHT);
		
		int stashTextWidth = 250;
		JPanel stashTabTextPanel = new JPanel(new GridBagLayout());
		stashTabTextPanel.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT+1));
		stashTabTextPanel.setBackground(Color.green);
		JTextField stashTabText = new LimitTextField(31);
		stashTabText.setHorizontalAlignment(JTextField.CENTER);
		stashTabText.setPreferredSize(new Dimension(stashTextWidth, ROW_HEIGHT+1));
		stashTabTextPanel.add(stashTabText, new GridBagConstraints());
		
		JComboBox<String> typeCombo = new JComboBox<String>();
		System.out.println(typeCombo.getPreferredSize());
//		typeCombo.setPreferredSize(new Dimension(30, ROW_HEIGHT));
		typeCombo.addItem("Normal");
		typeCombo.addItem("Quad");
		
		JComboBox<Color> colorCombo = new JComboBox<Color>();
		colorCombo.setPreferredSize(new Dimension(50, ROW_HEIGHT));
		colorCombo.setRenderer(new StashTabCellRenderer());
		for(StashTabColor c : StashTabColor.values()){
			colorCombo.addItem(c.getBackground());
		}
		
		
		
//		stashTabText.setPreferredSize(new Dimension(200, ROW_HEIGHT));
		
		this.add(deleteButton, gc);
		gc.gridx++;
		this.add(stashTabTextPanel, gc);
		gc.gridx++;
		this.add(typeCombo, gc);
		gc.gridx++;
		this.add(colorCombo, gc);
		
		
		
	}
	
}
